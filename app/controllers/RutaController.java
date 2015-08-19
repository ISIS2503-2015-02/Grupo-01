package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import models.Ruta;
import models.Tranvia;
import models.Mobibus;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class RutaController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRuta() {
        JsonNode j = Controller.request().body().asJson();
        Ruta ruta = Ruta.bind(j);
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


    public Result find(Long id){
    	Ruta ruta = new Model.Finder(Long.class, Ruta.class).byId(id);
    	return ok(Json.toJson(ruta));
    }

    public Result alertMobibusAccident(Long id, String accidente, double longitud, double latitud) {
    	Ruta ruta = new Model.Finder(Long.class, Ruta.class).byId(id);
    	ruta.setTerminado("accidentado");	
    	ruta.setTipoAccidente(accidente);
    	Mobibus bus = ruta.getBus();
    	bus.setUbicacionX(longitud);
    	bus.setUbicacionY(latitud);
    	bus.setEstado("accidentado");
        return ok(Json.toJson(ruta));
    }

    public Result alertarAccidenteTranvia(Long id, String accidente, double longitud, double latitud){
        Ruta ruta = new Model.Finder(Long.class, Ruta.class).byId(id);
        ruta.setTerminado("accidentado");   
        ruta.setTipoAccidente(accidente);
        Tranvia tranvi = ruta.getTranvia();
        tranvia.setUbicacionX(longitud);
        tranvia.setUbicacionY(latitud);
        tranvia.setEstado("accidentado");
        tranvia.update();

        Tranvia nuevoTranvia = new Tranvia(longitud, latitud, "activo", 0, 
            tranvia.getTemperatura(), false, new ArrayList<Revision>());
        ruta.setUbicacionOrigen(longitud+","+latitud);
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
}

