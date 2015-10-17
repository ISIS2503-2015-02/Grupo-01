package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import actions.CorsComposition;
import actions.ForceHttps;

import java.util.List;

@CorsComposition.Cors
//@ForceHttps.Https
public class EstacionController  extends Controller{
	
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearEstacion() {
        JsonNode j = Controller.request().body().asJson();
        Estacion estacion = Estacion.bind(j);
        estacion.save();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(estacion));
    }

    public Result darEstaciones() {
        List<Estacion> estaciones = new Model.Finder(Long.class, Estacion.class).all();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(estaciones));
    }

    public Result darEstacion(Long id){
      Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).byId(id);
      
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(estacion));  
    }

    public Result darEstacionesDesocupadas(){
        List<Estacion> estaciones = new Model.Finder(Long.class, Estacion.class).where().lt("ocupacion", 0.10).findList();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(estaciones));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result llenarEstacion(){
        JsonNode j = Controller.request().body().asJson();
        Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).byId(new Long(j.findPath("estacionId").asInt()));
        int cap = estacion.getCapacidad();
        while(estacion.getVcubs().size() < cap){
            Vcub vcub= new Vcub(Cons.V_DISPONIBLE);
            estacion.agregarVcub(vcub);
        }
        estacion.update();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(estacion));  
    }

    public Result eliminarEstaciones(){
        List<Estacion> estaciones = new Model.Finder(Long.class, Estacion.class).all();
        for(int i = 0; i<estaciones.size();i++){
            estaciones.get(i).delete();
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson("")); 
    }

}