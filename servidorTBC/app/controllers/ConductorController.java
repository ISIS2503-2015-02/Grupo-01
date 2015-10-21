
package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Result;
import actions.CorsComposition;
import actions.ForceHttps;

import java.util.List;

@With(SecuredAction.class)
@CorsComposition.Cors
//@ForceHttps.Https
public class ConductorController extends Controller{
	
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearConductor() {
        JsonNode j = Controller.request().body().asJson();
        Conductor conductor = Conductor.bind(j);
        conductor.save();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(conductor));
    }

    public Result darConductores() {
        List<Conductor> conductores = new Model.Finder(Long.class, Conductor.class).all();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(conductores));
    }

    public Result darConductor(Long cedula){
      Conductor conductor = (Conductor) new Model.Finder(Long.class, Conductor.class).byId(cedula);
      
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(conductor));  
    }

    public Result eliminarConductor(Long cedula){
      Conductor conductor = (Conductor) new Model.Finder(Long.class, Conductor.class).byId(cedula);
      conductor.delete();
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(""));  
    }

    public Result eliminarConductores(){
        List<Conductor> conductores = new Model.Finder(Long.class, Conductor.class).all();
        for(int i = 0; i<conductores.size();i++){
            conductores.get(i).delete();
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }
}



