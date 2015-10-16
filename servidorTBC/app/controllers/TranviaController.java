package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import java.io.Serializable;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import actions.CorsComposition;
import actions.ForceHttps;

import java.util.List;

CorsComposition.Cors
//@ForceHttps.Https
public class TranviaController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearTranvia() {
        JsonNode j = Controller.request().body().asJson();
        Tranvia tranvia = Tranvia.bind(j);
        Posicion pos = Posicion.bind(j);
        tranvia.agregarPosicion(pos);
        tranvia.save();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(tranvia));
    }

    public Result darTranvias() {
        List<Tranvia> tranvias = new Model.Finder(Long.class, Tranvia.class).all();
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(tranvias));
    }

    public Result darTranviasDisponibles() {
        List<Tranvia> tranvias = new Model.Finder(Long.class, Tranvia.class).
        where().eq("estado", Cons.V_DISPONIBLE).findList();;
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(tranvias));
    }

    public Result darTranviasOcupados() {
        List<Tranvia> tranvias = new Model.Finder(Long.class, Tranvia.class).
        where().eq("estado", Cons.V_OCUPADO).findList();;

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(tranvias));
    }

    public Result darTranvia(Long id){
      Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);

      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(tranvia));  
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRevision(Long id) {
        JsonNode j = Controller.request().body().asJson();
        Revision revision = Revision.bind(j);
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
        tranvia.agregarRevision(revision);
        tranvia.update();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(tranvia));
    }

    public Result darRevisiones(Long id) {
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(id);
        List<Revision> revisiones = tranvia.getRevisiones();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(revisiones));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result actualizarUbicacion(){
        JsonNode j = Controller.request().body().asJson();
        Posicion posicion = Posicion.bind(j);
        Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).byId(new Long(j.findPath("tranviaId").asInt()));
        posicion.setTranvia(tranvia);
        posicion.save();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(tranvia));
    }

    public Result eliminarTranvias(){
        List<Tranvia> tranvias = new Model.Finder(Long.class, Tranvia.class).all();
        for(int i = 0; i<tranvias.size();i++){
            tranvias.get(i).delete();
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }
}
