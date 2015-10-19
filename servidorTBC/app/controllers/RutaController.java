package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import actions.CorsComposition;
import actions.ForceHttps;

import java.util.List;
import java.util.ArrayList;

@CorsComposition.Cors
//@ForceHttps.Https
public class RutaController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRuta() {
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = Ruta.bind(j);
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).where().eq("estado", Cons.V_DISPONIBLE).findList().get(0);
        tranvia.setEstado(Cons.V_OCUPADO);
        tranvia.update();
        ruta.setTranvia(tranvia);
        ruta.save();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(ruta));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result asignarTranvia(){
        JsonNode j = Controller.request().body().asJson();
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(new Long(j.findPath("tranviaId").asInt()));
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(new Long(j.findPath("rutaId").asInt()));
        Conductor conductor = (Conductor) new Model.Finder(Long.class, Conductor.class).where().eq("estado", Cons.V_DISPONIBLE).findList().get(0);
        tranvia.setEstado(Cons.V_OCUPADO);
        tranvia.update();
        conductor.setEstado(Cons.V_OCUPADO);
        conductor.update();
        ruta.setBus(null);
        ruta.setTipoAccidente(Cons.EA_NORMAL);
        ruta.setTerminado(Cons.ET_CURSO);
        ruta.setTranvia(tranvia);
        ruta.setConductor(conductor);
        ruta.update();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(ruta));

    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result asignarMobibus(){
        JsonNode j = Controller.request().body().asJson();
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(new Long(j.findPath("mobibusId").asInt()));
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(new Long(j.findPath("rutaId").asInt()));
        Conductor conductor = (Conductor) new Model.Finder(Long.class, Conductor.class).where().eq("estado", Cons.V_DISPONIBLE).findList().get(0);
        mobibus.setEstado(Cons.V_OCUPADO);
        mobibus.update();
        conductor.setEstado(Cons.V_OCUPADO);
        conductor.update();
        ruta.setTranvia(null);
        ruta.setTipoAccidente(Cons.EA_NORMAL);
        ruta.setTerminado(Cons.ET_CURSO);
        ruta.setBus(mobibus);
        ruta.setConductor(conductor);
        ruta.update();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(ruta));

    }

    public Result darRutas() {
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).all();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(rutas));
    }

    public Result darRutasAccidenteTranvia(){
      List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).
      where().eq("tipo_accidente", "choque").eq("tipo", "Ruta tranvia").findList();

      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(rutas));  
    }


    public Result darRuta(Long id){
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(id);

        response().setHeader("Access-Control-Allow-Origin", "*");
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

        response().setHeader("Access-Control-Allow-Origin", "*");
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

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(ruta));
    }

    public Result darBusesCercanosAccidente(Long id){
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(id);
        ruta.setTerminado(Cons.ET_ANORMAL);   
        Mobibus bus = ruta.getBus();
        Tranvia tranvia = ruta.getTranvia();
        Posicion pos;

        if(bus!=null)
            pos=bus.getPosiciones().get(bus.getPosiciones().size() - 1);
        else
            pos=tranvia.getPosiciones().get(tranvia.getPosiciones().size() - 1);

        double posY = pos.getLongitud();
        double posX = pos.getLatitud();

        List<Mobibus> buses = new Model.Finder(Long.class, Mobibus.class).where().eq("estado", Cons.V_DISPONIBLE).findList();

        for(int i = 0; i<buses.size();i++){

            Mobibus buss = buses.get(i);
            Posicion tempPos = buss.getPosiciones().get(buss.getPosiciones().size() - 1);
            double tempposY = tempPos.getLongitud();
            double tempposX = tempPos.getLatitud();

            if(tempposX > (posX + 0.2) || tempposX < (posX - 0.2) || 
             tempposY > (posY + 0.2) || tempposY < (posY - 0.2)){

                buses.remove(i);
                i--;
            }
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(buses));
    }

    public Result darRutasAccidentes(){
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).
        where().eq("terminado", Cons.ET_ANORMAL).findList();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(rutas));
    }

    public Result darRutasTerminadas(){
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).
        where().eq("terminado", Cons.ET_TERMINADO).findList();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(rutas));
    }

    /**
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
    */

    public Result eliminarRutas(){
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).all();
        for(int i = 0; i<rutas.size();i++){
            rutas.get(i).delete();
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }

    public Result eliminarRuta(Long id){
      Ruta ruta = (Ruta) new Model.Finder(Long.class, Ruta.class).byId(id);
      ruta.delete();
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(""));  
    }
}

