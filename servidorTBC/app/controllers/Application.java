package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready now."));
    }

    public Result tranvia(){
    	return ok(tranvia.render());
    }

    public Result emergencias(){
    	return ok(emergencias.render());
    }

    public Result reportes(){
    	return ok(reportes.render());
    }

    public Result mobibus(){
    	return ok(mobibus.render());
    }

    public Result vcub(){
    	return ok(vcub.render());
    }
}
