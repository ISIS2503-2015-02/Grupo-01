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

public class TranviaController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode j = Controller.request().body().asJson();
        Tranvia tranvia = Tranvia.bind(j);
        tranvia.save();

        return ok(Json.toJson(tranvia));
    }

    public Result read() {
        List<Tranvia> tranvias = new Model.Finder(String.class, Tranvia.class).all();
        return ok(Json.toJson(tranvias));
    }


    @BodyParser.Of(BodyParser.Json.class)
    public Result createRevision(Long id) {
        JsonNode j = Controller.request().body().asJson();
        Revision revision = Revision.bind(j);
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
        tranvia.agregarRevision(revision);
        tranvia.update();
        return ok(Json.toJson(tranvia));
    }

    public Result readRevisiones(Long id) {
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
        List<Revision> revisiones = tranvia.getRevisiones();
        return ok(Json.toJson(revisiones));
    }
}
