package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import actions.CorsComposition;
import actions.ForceHttps;

import java.util.List;

@CorsComposition.Cors
//@ForceHttps.Https
public class UsuarioController extends Controller {

	@BodyParser.Of(BodyParser.Json.class)
    public Result crearUsuario() {
        JsonNode j = Controller.request().body().asJson();
        Usuario usuario = Usuario.bind(j);
        usuario.save();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(usuario));
    }

    public Result darUsuarios() {
        List<Usuario> usuarios = new Model.Finder(Long.class, Usuario.class).all();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(usuarios));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearReserva(){
    	JsonNode j = Controller.request().body().asJson();
    	Reserva reserva = Reserva.bind(j);
    	Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(j.findPath("usuarioId").asLong());
        JsonNode rutaJson = j.get("ruta");
        Ruta rout = Ruta.bind(rutaJson);
        reserva.setRuta(rout);
    	usuario.addReserva(reserva);
        rout.save();
        reserva.save();
    	usuario.update();
    	
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(usuario));
   	}   

    public Result eliminarUsuarios(){
        List<Usuario> usuarios = new Model.Finder(Long.class, Usuario.class).all();
        for(int i = 0; i<usuarios.size();i++){
            usuarios.get(i).delete();
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }

    public Result darUsuario(Long id){
      Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(id);

      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(usuario));  
    }

    public Result eliminarUsuario(Long id){
      Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(id);
      usuario.delete();
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(usuario));  
    }

}