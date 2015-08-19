package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import models.Ruta;
import models.Tranvia;
import models.Mobibus;
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

    public Result darUsuario() {
        List<Usuario> usuario = new Model.Finder(String.class, Usuario.class).all();
        return ok(Json.toJson(usuario));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearReserva(String id){
    	JsonNode j = Controller.request().body().asJson();
    	Reserva reserva = Reserva.bind(j);
    	Usuario usuario = new Model.Finder(String.class, Usuario.class).byId(id);
    	usuario.setReserva(reserva);
    	usuario.update();
    	reserva.save();
    	return ok(Json.toJson(usuario));
   	}

}