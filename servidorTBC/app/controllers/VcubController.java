package controllers;

import java.util.Date;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import actions.CorsComposition;
import actions.ForceHttps;
import security.*;

import java.util.List;

@CorsComposition.Cors
@ForceHttps.Https
public class VcubController extends Controller{
	
    public static final String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";
    
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearVcub() {
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = Vcub.bind(j);

        double[] coords = Utilidad.coordenadasNuevas();
        Posicion pos = new Posicion(coords[0], coords[1], new Date());
        
        vcub.agregarPosicion(pos);
        String authToken = vcub.createToken();
        ObjectNode authTokenJson = Json.newObject();
        authTokenJson.put(AUTH_TOKEN, authToken);
        response().setCookie(AUTH_TOKEN, authToken);

        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(vcub));
    }

    @With(SecuredActionAdmin.class)
    public Result darVcubs() {
        List<Vcub> vcubs = new Model.Finder(Long.class, Vcub.class).all();
        
        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(vcubs));
    }

    @With(SecuredActionAdmin.class)
    public Result darVcubsDisponibles() {
        List<Vcub> vcubs = new Model.Finder(Long.class, Vcub.class).
        where().eq("estado", Cons.V_DISPONIBLE).findList();
        
        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(vcubs));
    }

    @With(SecuredActionAdmin.class)
    public Result darVcubsOcupados() {
        List<Tranvia> tranvias = new Model.Finder(Long.class, Tranvia.class).
        where().eq("estado", Cons.V_OCUPADO).findList();
        
        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(tranvias));
    }

    @With(SecuredActionAdmin.class)
    public Result darVcub(Long id){
      Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(id);
      
      response().setHeader(Cons.CORS, "*");
      return ok(Json.toJson(vcub));  
    }

    @With(SecuredActionVcub.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result actualizarUbicacion(){
        JsonNode j = Controller.request().body().asJson();
        Posicion posicion = Posicion.bind(j);
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(new Long(j.findPath("vcubId").asInt()));
        posicion.setVcub(vcub);
        posicion.save();

        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(posicion));
    }

    @With(SecuredActionVcub.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result adquirir(){
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(new Long(j.findPath("vcubId").asInt()));
        Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(new Long(j.findPath("usuarioId").asInt()));
        vcub.setEstacion(null);
        vcub.setEstado(Cons.V_OCUPADO);
        vcub.setUsuario(usuario);
        vcub.update();

        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(vcub));
    }

    @With(SecuredActionVcub.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result restituir(){
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(new Long(j.findPath("vcubId").asInt()));
        Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).byId(new Long(j.findPath("estacionId").asInt()));
        Usuario usuario = (Usuario) new Model.Finder(Long.class,  Usuario.class).byId(new Long(j.findPath("usuarioId").asInt()));
        
        response().setHeader(Cons.CORS, "*");
        if(vcub.getUsuario().getNumeroIdentificacion().equals(usuario.getNumeroIdentificacion())){
            vcub.setEstacion(estacion);
            vcub.setEstado(Cons.V_DISPONIBLE);
            vcub.setUsuario(null);
            vcub.update();
            return ok(Json.toJson(vcub));
        }
        else{
            return ok(Json.toJson(j));
        }
    }

    @With(SecuredActionAdmin.class)
    public Result eliminarVcubs(){
        List<Vcub> vcubs = new Model.Finder(Long.class, Vcub.class).all();
        for(int i = 0; i<vcubs.size();i++){
            vcubs.get(i).delete();
        }

        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(""));
    }

    @With(SecuredActionAdmin.class)
    public Result eliminarVcub(Long id){
      Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(id);
      vcub.delete();
      response().setHeader(Cons.CORS, "*");
      return ok(Json.toJson(""));  
    }

}