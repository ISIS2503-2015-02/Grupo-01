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

    public Result tranvia(){
    	ArrayList<Tranvia> tranvias = new ArrayList<Tranvia>();
    	Tranvia test = new Tranvia();
    	test.setEstado("normal");
    	tranvias.add(test);
    	TranviaController tCtrl = new TranviaController();
    	Result sonTranvias = tCtrl.darTranvias();
    	System.out.println(sonTranvias);
    	return ok(tranvia.render(tranvias));
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

    public Result login(){
    	return ok(logAdmin.render());
    }

    public Result user(){
    	return ok(user.render("test"));
    }

    public Result admin(){
    	return ok(admin.render());
    }

    public Result form(){
    	Form<Usuario> formu = Form.form(Usuario.class);
    	return ok(form.render(formu));
    }

    public Result registro(){
    	return ok();
    }
}
