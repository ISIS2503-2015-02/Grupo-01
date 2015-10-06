package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
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
        Posicion pos = Posicion.bind(j);
        bus.agregarPosicion(pos);
        bus.save();
        return ok(Json.toJson(bus));
    }

    public Result read() {
        List<Mobibus> buses = new Model.Finder(Long.class, Mobibus.class).all();
        return ok(Json.toJson(buses));
    }

    public Result darMobibusesDisponibles() {
        List<Mobibus> buses = new Model.Finder(Long.class, Mobibus.class).
        where().eq("estado", Cons.V_DISPONIBLE).findList();;
        return ok(Json.toJson(buses));
    }

    public Result darMobibusesOcupados() {
        List<Mobibus> buses = new Model.Finder(Long.class, Mobibus.class).
        where().eq("estado", Cons.V_OCUPADO).findList();;
        return ok(Json.toJson(buses));
    }

    public Result darBus(Long id){
      Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
      return ok(Json.toJson(mobibus));  
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRevision() {
        JsonNode j = Controller.request().body().asJson();
        Revision revision = Revision.bind(j);
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(new Long(j.findPath("mobibusId").asInt()));
        mobibus.agregarRevision(revision);
        mobibus.update();
        return ok(Json.toJson(mobibus));
    }

    public Result darRevisiones(Long id) {
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
        List<Revision> revisiones = mobibus.getRevisiones();
        return ok(Json.toJson(revisiones));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result actualizarUbicacion(){
        JsonNode j = Controller.request().body().asJson();
        Posicion posicion = Posicion.bind(j);
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(new Long(j.findPath("mobibusId").asInt()));
        posicion.setMobibus(mobibus);
        posicion.save();

        return ok(Json.toJson(mobibus));
    }

    public Result eliminarMobibuses(){
        List<Mobibus> mobibuses = new Model.Finder(Long.class, Mobibus.class).all();
        for(int i = 0; i<mobibuses.size();i++){
            mobibuses.get(i).delete();
        }

        return ok(Json.toJson(""));
    }
}
