package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Usuario;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.avaje.ebean.Model;
import security.*;

import static play.libs.Json.toJson;
import static play.mvc.Controller.request;
import static play.mvc.Controller.response;

public class SecureUsuarioController extends Controller {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";


    public static Usuario getUser() {
        return (Usuario)Http.Context.current().args.get("user");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result crearUsuario() {
        JsonNode j = Controller.request().body().asJson();
        Usuario usuario = Usuario.bind(j);
        usuario.save();

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(usuario));
    }

    // returns an authToken
    @BodyParser.Of(BodyParser.Json.class)
    public Result login() {

        JsonNode j = Controller.request().body().asJson();
        String user = j.findPath("user").asText();
        String pass = j.findPath("pass").asText();
        Usuario usuario = (Usuario) new Model.Finder(String.class, Usuario.class).where().eq("usuario",user).eq("password",pass).findUnique();
        if (usuario == null) {
            return unauthorized();
        }
        else {
            String authToken = usuario.createToken();
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(AUTH_TOKEN, authToken);
            response().setCookie(AUTH_TOKEN, authToken);
            return ok(authTokenJson);
        }
    }

    @With(SecuredActionUsuario.class)
    public Result logout() {
        response().discardCookie(AUTH_TOKEN);
        getUser().deleteAuthToken();
        return redirect("/");
    }

    public static class Login {

        @Constraints.Required
        @Constraints.Email
        public String emailAddress;

        @Constraints.Required
        public String password;

    }
}