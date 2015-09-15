
package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class ConductorController extends Controller{
	
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearConductor() {
        JsonNode j = Controller.request().body().asJson();
        Conductor conductor = Conductor.bind(j);
        conductor.save();

        return ok(Json.toJson(conductor));
    }

    public Result darConductores() {
        List<Conductor> conductores = new Model.Finder(Long.class, Conductor.class).all();
        return ok(Json.toJson(conductores));
    }

    public Result darConductor(Long cedula){
      Conductor conductor = (Conductor) new Model.Finder(Long.class, Conductor.class).byId(cedula);
      return ok(Json.toJson(conductor));  
    }
}



