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

public class UsuarioController extends Controller {

	@BodyParser.Of(BodyParser.Json.class)
    public Result crearUsuario() {
        JsonNode j = Controller.request().body().asJson();
        Usuario usuario = Usuario.bind(j);
        usuario.save();

        return ok(Json.toJson(usuario));
    }

    public Result darUsuarios() {
        List<Usuario> usuarios = new Model.Finder(Long.class, Usuario.class).all();
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
    	return ok(Json.toJson(usuario));
   	}

    public Result eliminarUsuarios(){
        List<Usuario> usuarios = new Model.Finder(Long.class, Usuario.class).all();
        for(int i = 0; i<usuarios.size();i++){
            usuarios.get(i).delete();
        }

        return ok(Json.toJson(""));
    }

}