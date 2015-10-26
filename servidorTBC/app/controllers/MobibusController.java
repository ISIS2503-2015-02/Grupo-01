package controllers;

import java.util.Date;
import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import actions.CorsComposition;
import actions.ForceHttps;
import security.*;
import utils.*;
import java.util.List;

@CorsComposition.Cors
//@ForceHttps.Https
public class MobibusController extends Controller {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode j = Controller.request().body().asJson();
        Mobibus bus = Mobibus.bind(j);
        
        double[] coords = Utilidad.coordenadasNuevas();
        Posicion pos = new Posicion(coords[0], coords[1], new Date());
        
        //Posicion posicion = Posicion.bind(j);

        bus.agregarPosicion(pos);
        String authToken = bus.createToken();
        ObjectNode authTokenJson = Json.newObject();
        authTokenJson.put(AUTH_TOKEN, authToken);
        response().setCookie(AUTH_TOKEN, authToken);

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(authTokenJson);
    }

    @With(SecuredActionAdmin.class)
    public Result read() {
        List<Mobibus> buses = new Model.Finder(Long.class, Mobibus.class).all();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(buses));
    }

    @With(SecuredActionAdmin.class)
    public Result darMobibusesDisponibles() {
        List<Mobibus> buses = new Model.Finder(Long.class, Mobibus.class).
        where().eq("estado", Cons.V_DISPONIBLE).findList();;
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(buses));
    }

    @With(SecuredActionAdmin.class)
    public Result darMobibusesOcupados() {
        List<Mobibus> buses = new Model.Finder(Long.class, Mobibus.class).
        where().eq("estado", Cons.V_OCUPADO).findList();;
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(buses));
    }

    @With(SecuredActionAdmin.class)
    public Result darBus(Long id){
      Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
      
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(mobibus));  
    }

    @With(SecuredActionAdmin.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result crearRevision() {
        JsonNode j = Controller.request().body().asJson();
        Revision revision = Revision.bind(j);
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(new Long(j.findPath("mobibusId").asInt()));
        mobibus.agregarRevision(revision);
        mobibus.update();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(mobibus));
    }

    @With(SecuredActionAdmin.class)
    public Result darRevisiones(Long id) {
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
        List<Revision> revisiones = mobibus.getRevisiones();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(revisiones));
    }

    @With(SecuredActionMobibus.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result actualizarUbicacion(){
        JsonNode j = Controller.request().body().asJson();
        Posicion posicion = Posicion.bind(j);
        Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(new Long(j.findPath("mobibusId").asInt()));
        posicion.setMobibus(mobibus);
        posicion.save();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(mobibus));
    }

    @With(SecuredActionAdmin.class)
    public Result eliminarMobibuses(){
        List<Mobibus> mobibuses = new Model.Finder(Long.class, Mobibus.class).all();
        for(int i = 0; i<mobibuses.size();i++){
            mobibuses.get(i).delete();
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }

    @With(SecuredActionAdmin.class)
    public Result eliminarBus(Long id){
      Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).byId(id);
      mobibus.delete();
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(""));  
    }

}
