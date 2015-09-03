package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.ArrayList;

public class RutaController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRuta() {
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = Ruta.bind(j);
        Long idVehic = new Long(j.findPath("idVehiculo").asInt());
        if(ruta.getTipo().equals("tranvia")){
            Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(idVehic);
            ruta.setTranvia(tranvia);
        }
        else{
            Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(idVehic);
            ruta.setBus(mobibus);
        }

        ruta.save();

        return ok(Json.toJson(ruta));
    }

    public Result darRutas() {
        List<Ruta> rutas = new Model.Finder(String.class, Ruta.class).all();
        return ok(Json.toJson(rutas));
    }

    public Result darRutasAccidenteTranvia(){
      List<Ruta> rutas = new Model.Finder(Long.class, Tranvia.class).
      where().eq("accidente", "choque").eq("tipo", "tranvia").findList();
      return ok(Json.toJson(rutas));  
    }


    public Result darRuta(Long id){
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(id);
        return ok(Json.toJson(ruta));
    }

    public Result alertMobibusAccident(Long id, String accidente, double longitud, double latitud) {
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(id);
        ruta.setTerminado("accidentado");   
        ruta.setTipoAccidente(accidente);
        Mobibus bus = ruta.getBus();
        bus.setUbicacionX(longitud);
        bus.setUbicacionY(latitud);
        bus.setEstado("accidentado");
        return ok(Json.toJson(ruta));
    }

    public Result alertarAccidenteTranvia(Long id, String accidente, double longitud, double latitud){
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(id);
        ruta.setTerminado("accidentado");   
        ruta.setTipoAccidente(accidente);
        Tranvia tranvia = ruta.getTranvia();
        tranvia.setUbicacionX(longitud);
        tranvia.setUbicacionY(latitud);
        tranvia.setEstado("accidentado");
        tranvia.update();

        double[][] coord = darCoordenadas(latitud, longitud);

        Tranvia nuevoTren = new Model.finder(Long.class, Tranvia.class).
        where().between("latitud", coord[1][0], coord[0][0]).between("longitud",coord[3][1],coord[2][1]);

        Tranvia nuevoTranvia = new Tranvia(longitud, latitud, "activo", 0, 
            tranvia.getTemperatura(), false, new ArrayList<Revision>());
        ruta.setUbicaiconOrigen(longitud+","+latitud);
        ruta.setTranvia(nuevoTranvia);
        ruta.update();
        return ok(Json.toJson(ruta));    }

    public Result darRutasAccidentesBus(){
        List<Ruta> rutas = new Model.Finder(Long.class, Mobibus.class).
        where().eq("accidente", "choque").eq("tipo","mobibus").findList();
        return ok(Json.toJson(rutas));
    }

    public Result darRutasTerminadasBus(){
        List<Ruta> rutas = new Model.Finder(Long.class, Mobibus.class).
        where().eq("terminado", "terminado").eq("tipo","mobibus").findList();
        return ok(Json.toJson(rutas));
    }

    public Result darRutasTerminadasTranvia(){
        List<Ruta> rutas = new Model.Finder(Long.class, Tranvia.class).
        where().eq("terminado", "terminado").eq("tipo","tranvia").findList();
        return ok(Json.toJson(rutas));
    }

    private double[][] darCoordenadas(double latitud, double longitud){
        double[][] coord = new double[4][2];
        double radLat = Math.toRadians(latitud);
        double radLon = Math.toRadians(longitud);
        double distanciaRadial = 1/6371;
        int[] angulos = {0,180,90,270};
        for (int j = 0; j < angulos.length; j++) {  
            double angulo = Math.toRadians(angulos[j]);
            double latitudRadial = Math.asin(Math.sin(radLat)*Math.cos(distanciaRadial) 
                    + Math.cos(radLat)*Math.sin(distanciaRadial)*Math.cos(angulo));
            double longitudRadial = radLon + Math.atan2(Math.sin(angulo)*Math.sin(distanciaRadial)*Math.cos(latitudRadial), Math.cos(distanciaRadial) - Math.sin(radLat)*Math.sin(latitudRadial));
            coord[j][0] = latitudRadial;
            coord[j][1] = longitudRadial;
        }
    }
}

