package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.*;
import play.libs.Json;
import play.mvc.*;

import play.data.Form;

import java.util.ArrayList;
import models.*;

import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready now."));
    }

}
