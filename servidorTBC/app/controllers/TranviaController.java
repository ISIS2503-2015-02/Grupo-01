package controllers;

import com.avaje.ebean.Model;
import java.io.Serializable;
import com.fasterxml.jackson.databind.JsonNode;
import models.Vehiculo;
import models.Tranvia;
import models.Revision;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class TranviaController extends Controller {

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

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRevision(Long id) {
        JsonNode j = Controller.request().body().asJson();
        Revision revision = Revision.bind(j);
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
        tranvia.agregarRevision(revision);
        tranvia.update();
        return ok(Json.toJson(tranvia));
    }

    public Result darRevisiones(Long id) {
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
        List<Revision> revisiones = tranvia.getRevisiones();
        return ok(Json.toJson(revisiones));
    }

    public Result actualizarUbicacion(Long id, Double posX, Double posY){
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
        tranvia.setUbicacionY(posY);
        tranvia.setUbicacionX(posX);
        tranvia.update();
        return ok(Json.toJson(tranvia));
    }
}
