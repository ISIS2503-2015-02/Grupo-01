package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Vcub;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class VcubController extends Controller{
	
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearVcub() {
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = Vcub.bind(j);
        vcub.save();

        return ok(Json.toJson(vcub));
    }

    public Result darVcubs() {
        List<Vcub> vcubs = new Model.Finder(String.class, Vcub.class).all();
        return ok(Json.toJson(vcubs));
    }

    public Result darVcub(Long id){
      Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(id);
      return ok(Json.toJson(vcub));  
    }

    public Result actualizarUbicacion(Long id, Double posX, Double posY){
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(id);
        vcub.setUbicacionY(posY);
        vcub.setUbicacionX(posX);
        vcub.update();
        return ok(Json.toJson(vcub));
    }
}