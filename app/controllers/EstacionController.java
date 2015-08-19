package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Estacion;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Estacion  extends Controller{
	
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearEstacion() {
        JsonNode j = Controller.request().body().asJson();
        Estacion estacion = Estacion.bind(j);
        estacion.save();

        return ok(Json.toJson(estacion));
    }

    public Result darEstaciones() {
        List<Tranvia> estaciones = new Model.Finder(String.class, Estacion.class).all();
        return ok(Json.toJson(tranvias));
    }

    public Result darEstacion(Long id){
      Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).byId(id);
      return ok(Json.toJson(estacion));  
    }
}