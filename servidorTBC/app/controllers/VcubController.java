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

public class VcubController extends Controller{
	
	@BodyParser.Of(BodyParser.Json.class)
    public Result crearVcub() {
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = Vcub.bind(j);
        Posicion pos = Posicion.bind(j);
        vcub.agregarPosicion(pos);
        vcub.save();

        return ok(Json.toJson(vcub));
    }

    public Result darVcubs() {
        List<Vcub> vcubs = new Model.Finder(Long.class, Vcub.class).all();
        return ok(Json.toJson(vcubs));
    }

    public Result darVcubsDisponibles() {
        List<Vcub> vcubs = new Model.Finder(Long.class, Vcub.class).
        where().eq("estado", Cons.V_DISPONIBLE).findList();;
        return ok(Json.toJson(vcubs));
    }

    public Result darVcubsOcupados() {
        List<Tranvia> tranvias = new Model.Finder(Long.class, Tranvia.class).
        where().eq("estado", Cons.V_OCUPADO).findList();;
        return ok(Json.toJson(tranvias));
    }

    public Result darVcub(Long id){
      Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(id);
      return ok(Json.toJson(vcub));  
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result actualizarUbicacion(){
        JsonNode j = Controller.request().body().asJson();
        Posicion posicion = Posicion.bind(j);
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(new Long(j.findPath("vcubId").asInt()));
        posicion.setVcub(vcub);
        posicion.save();

        return ok(Json.toJson(posicion));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result adquirir(){
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(new Long(j.findPath("vcubId").asInt()));
        Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(new Long(j.findPath("usuarioId").asInt()));
        vcub.setEstacion(null);
        vcub.setEstado(Cons.V_OCUPADO);
        vcub.setUsuario(usuario);
        vcub.update();

        return ok(Json.toJson(vcub));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result restituir(){
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(new Long(j.findPath("vcubId").asInt()));
        Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).byId(new Long(j.findPath("estacionId").asInt()));
        Usuario usuario = (Usuario) new Model.Finder(Long.class,  Usuario.class).byId(new Long(j.findPath("usuarioId").asInt()));
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

    public Result eliminarVcubs(){
        List<Vcub> vcubs = new Model.Finder(Long.class, Vcub.class).all();
        for(int i = 0; i<vcubs.size();i++){
            vcubs.get(i).delete();
        }

        return ok(Json.toJson(""));
    }
}