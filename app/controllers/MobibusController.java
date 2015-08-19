package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Tranvia;
import models.Mobibus;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class MobibusController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        JsonNode j = Controller.request().body().asJson();
        Mobibus bus = Mobibus.bind(j);
        bus.save();
        return ok(Json.toJson(bus));
    }

    public Result read() {
        List<Mobibus> buses = new Model.Finder(String.class, Mobibus.class).all();
        return ok(Json.toJson(buses));
    }
}
