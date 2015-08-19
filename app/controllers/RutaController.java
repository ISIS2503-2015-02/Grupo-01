package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Vehiculo;
import models.Tranvia;
import models.Revision;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class RutaController extends Controller{

	@BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = Ruta.bind(j);
        ruta.save();
        return ok(Json.toJson(ruta));
    }

    public Result read() {
        List<Ruta> rutas = new Model.Finder(String.class, Ruta.class).all();
        return ok(Json.toJson(rutas));
    }

    public Result find(Long id){
    	Ruta ruta = new Model.Finder(Long.class, Ruta.class).byId(id);
    	return ok(Json.toJson(ruta));
    }

    public Result alertMobibusAccident(Long id, String accidente, double longitud, double latitud){
    	Ruta ruta = new Model.Finder(Long.class, Ruta.class).byId(id);
    	ruta.setTerminado("Accidentado");	
    	ruta.setTipoAccidente(accidente);
    	Mobibus bus = ruta.getBus();
    	bus.setUbicacionX(longitud);
    	bus.setUbicacionY(latitud);
    	bus.setEstado("Accidentado");
    }
