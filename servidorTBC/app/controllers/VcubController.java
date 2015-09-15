package controllers;

import com.avaje.ebean.Model;
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
        vcub.save();

        return ok(Json.toJson(vcub));
    }

    public Result darVcubs() {
        List<Vcub> vcubs = new Model.Finder(Long.class, Vcub.class).all();
        return ok(Json.toJson(vcubs));
    }

    public Result darVcub(Long id){
      Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(id);
      return ok(Json.toJson(vcub));  
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result actualizarUbicacion(){
        JsonNode j = Controller.request().body().asJson();
        Posicion posicion = Posicion.bind(j);
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(j.findPath("vcubId").asText());
        posicion.setVcub(vcub);
        posicion.save();

        return ok(Json.toJson(posicion));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result adquirir(){
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(j.findPath("vcubId").asText());
        Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(j.findPath("usuarioId").asInt());
        vcub.setEstacion(null);
        vcub.setEstado("Prestada");
        vcub.setUsuario(usuario);
        vcub.update();

        return ok(Json.toJson(vcub));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result restituir(){
        JsonNode j = Controller.request().body().asJson();
        Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).byId(j.findPath("vcubId").asText());
        Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).byId(j.findPath("estacionId").asText());
        Usuario usuario = (Usuario) new Model.Finder(Long.class,  Usuario.class).byId(j.findPath("usuarioID").asText());
        if(vcub.getUsuario().getNumeroIdentificacion().equals(usuario.getNumeroIdentificacion())){
            vcub.setEstacion(estacion);
            vcub.setEstado("Disponible");
            vcub.setUsuario(null);
            vcub.update();
            return ok(Json.toJson(vcub));
        }
        else{
            return ok(Json.toJson(j));
        }
    }
}