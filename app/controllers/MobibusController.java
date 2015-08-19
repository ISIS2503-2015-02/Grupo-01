package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Tranvia;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by scvalencia606 on 8/3/15.
 */

public class MobibusController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode j = Controller.request().body().asJson();
        Tranvia tranvia = Tranvia.bind(j);
        tranvia.save();
        return ok(Json.toJson(tranvia));
    }

    public Result read() {
        List<Tranvia> tranvias = new Model.Finder(String.class, Tranvia.class).all();
        return ok(Json.toJson(tranvias));
    }
}
