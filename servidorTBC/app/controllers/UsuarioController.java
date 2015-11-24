package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import actions.CorsComposition;
import actions.ForceHttps;
import security.*;

import java.util.List;

@CorsComposition.Cors
@ForceHttps.Https
public class UsuarioController extends Controller {

    @With(SecuredActionAdmin.class)
    public Result darUsuarios() {
        List<Usuario> usuarios = new Model.Finder(Long.class, Usuario.class).all();
        
        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(usuarios));
    }

    @With(SecuredActionUsuario.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result crearReserva(){
    	JsonNode j = Controller.request().body().asJson();
    	Reserva reserva = Reserva.bind(j);
    	Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(j.findPath("usuarioId").asLong());
        JsonNode rutaJson = j.get("ruta");
        Ruta rout = Ruta.bind(rutaJson);

        reserva.save();
        rout.save();

        reserva.setRuta(rout);
        reserva.setUsuario(usuario);
        reserva.update();

    	
        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(reserva));
   	}   

    @With(SecuredActionUsuario.class)
    public Result darReservasUsuario(Long id){
      List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).where().eq("usuario_numero_identificacion", id ).findList();
      response().setHeader(Cons.CORS, "*");
      return ok(Json.toJson(reservas));  
    }

    @With(SecuredActionAdmin.class)
    public Result eliminarUsuarios(){
        List<Usuario> usuarios = new Model.Finder(Long.class, Usuario.class).all();
        for(int i = 0; i<usuarios.size();i++){
            usuarios.get(i).delete();
        }

        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(""));
    }

    @With(SecuredActionAdmin.class)
    public Result darUsuario(Long id){
      Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(id);

      response().setHeader(Cons.CORS, "*");
      return ok(Json.toJson(usuario));  
    }

    @With(SecuredActionAdmin.class)
    public Result eliminarUsuario(Long id){
      Usuario usuario = (Usuario) new Model.Finder(Long.class, Usuario.class).byId(id);
      usuario.delete();
      response().setHeader(Cons.CORS, "*");
      return ok(Json.toJson(usuario));  
    }

    /**
    public Result login(){
        JsonNode j = Controller.request().body().asJson();
        String user = j.findPath("user").asText();
        String pass = j.findPath("pass").asText();
        Usuario usuario = (Usuario) new Model.Finder(String.class, Usuario.class).where().eq("usuario",user).eq("password",pass).findUnique();
        return ok(Json.toJson(usuario));
    }

    public Result logout(){
        return ok();
    }
*/
}