package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Vehiculo;
import models.Mobibus;
import models.Revision;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class MobibusController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode j = Controller.request().body().asJson();
        Mobibus bus = Mobibus.bind(j);
        bus.save();
        return ok(Json.toJson(bus));
    }

    public Result read() {
        List<Mobibus> buses = new Model.Finder(String.class, Mobibus.class).all();
        return ok(Json.toJson(buses));
    }

    public Result darBus(Long id){
      Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
      return ok(Json.toJson(mobibus));  
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRevision(Long id) {
        JsonNode j = Controller.request().body().asJson();
        Revision revision = Revision.bind(j);
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
        mobibus.agregarRevision(revision);
        mobibus.update();
        return ok(Json.toJson(mobibus));
    }

    public Result darRevisiones(Long id) {
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
        List<Revision> revisiones = mobibus.getRevisiones();
        return ok(Json.toJson(revisiones));
    }

    public Result actualizarUbicacion(Long id, Double posX, Double posY){
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
        mobibus.setUbicacionY(posY);
        mobibus.setUbicacionX(posX);
        mobibus.update();
        return ok(Json.toJson(mobibus));
    }
}
