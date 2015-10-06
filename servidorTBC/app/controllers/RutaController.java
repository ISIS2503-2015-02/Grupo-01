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
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).where().eq("estado", Cons.V_DISPONIBLE).findUnique();
        ruta.setTranvia(tranvia);
        ruta.save();
        return ok(Json.toJson(ruta));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result asignarTranvia(){
        JsonNode j = Controller.request().body().asJson();
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(new Long(j.findPath("tranviaId").asInt()));
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(new Long(j.findPath("rutaId").asInt()));
        Conductor conductor = (Conductor) new Model.Finder(Long.class, Conductor.class).byId(new Long(j.findPath("conductorId").asInt()));
        ruta.setTranvia(tranvia);
        ruta.setConductor(conductor);
        ruta.update();
        return ok(Json.toJson(ruta));

    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result asignarMobibus(){
        JsonNode j = Controller.request().body().asJson();
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(new Long(j.findPath("mobibusId").asInt()));
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(new Long(j.findPath("rutaId").asInt()));
        Conductor conductor = (Conductor) new Model.Finder(Long.class, Conductor.class).byId(new Long(j.findPath("conductorId").asInt()));
        ruta.setBus(mobibus);
        ruta.setConductor(conductor);
        ruta.update();
        return ok(Json.toJson(ruta));

    }

    public Result darRutas() {
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).all();
        return ok(Json.toJson(rutas));
    }

    public Result darRutasAccidenteTranvia(){
      List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).
      where().eq("tipo_accidente", "choque").eq("tipo", "tranvia").findList();
      return ok(Json.toJson(rutas));  
    }


    public Result darRuta(Long id){
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(id);
        return ok(Json.toJson(ruta));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result alertMobibusAccident() {
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(new Long(j.findPath("rutaId").asInt()));
        ruta.setTerminado(Cons.ET_ANORMAL);   
        ruta.setTipoAccidente(j.findPath("accidente").asText());
        Mobibus bus = ruta.getBus();
        bus.setEstado(Cons.V_ACCIDENTADO);
        bus.update();
        ruta.update();
        return ok(Json.toJson(ruta));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result alertarAccidenteTranvia(){
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(new Long(j.findPath("rutaId").asInt()));
        ruta.setTerminado(Cons.ET_ANORMAL);   
        ruta.setTipoAccidente(j.findPath("accidente").asText());
        Tranvia tranvia = ruta.getTranvia();
        tranvia.setEstado(Cons.V_ACCIDENTADO);
        tranvia.update();
        ruta.update();
        return ok(Json.toJson(ruta));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result darBusesCercanosAccidente(){
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(new Long(j.findPath("rutaId").asInt()));
        ruta.setTerminado(Cons.ET_ANORMAL);   
        Mobibus bus = ruta.getBus();
        Tranvia tranvia = ruta.getTranvia();
        Posicion pos;

        if(bus!=null)
            pos=bus.getPosiciones().get(bus.getPosiciones().size() - 1);
        else
            pos=tranvia.getPosiciones().get(bus.getPosiciones().size() - 1);

        double posX = pos.getLongitud();
        double posY = pos.getLatitud();

        
        
        return ok(Json.toJson(ruta));
    }

    public Result darRutasAccidentes(){
        List<Ruta> rutas = new Model.Finder(Long.class, Mobibus.class).
        where().eq("terminado", Cons.ET_ANORMAL).findList();
        return ok(Json.toJson(rutas));
    }

    public Result darRutasTerminadas(){
        List<Ruta> rutas = new Model.Finder(Long.class, Mobibus.class).
        where().eq("terminado", Cons.ET_TERMINADO).findList();
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
        return coord;
    }

    public Result eliminarRutas(){
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).all();
        for(int i = 0; i<rutas.size();i++){
            rutas.get(i).delete();
        }

        return ok(Json.toJson(""));
    }
}

