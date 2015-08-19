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

public class ReservaController extends Controller{
	
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearTranvia() {
        JsonNode j = Controller.request().body().asJson();
        Tranvia tranvia = Tranvia.bind(j);
        tranvia.save();

        return ok(Json.toJson(tranvia));
    }

    public Result darTranvias() {
        List<Tranvia> tranvias = new Model.Finder(String.class, Tranvia.class).all();
        return ok(Json.toJson(tranvias));
    }

    public Result darTranvia(Long id){
      Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
      return ok(Json.toJson(tranvia));  
    }
}