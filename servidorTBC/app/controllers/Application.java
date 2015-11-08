package controllers;

import play.*;
import play.mvc.*;

import models.*;

import views.html.*;

public class Application extends Controller {

	public Result index() {
		return ok(index.render("Your new application is ready now."));
	}

	public Result preflight(String path){
		return ok();
	}

	public Result preflight(){
		return ok();
	}
}