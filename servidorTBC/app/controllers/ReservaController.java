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
public class ReservaController extends Controller{

    @With(SecuredActionAdmin.class)    
	public Result darReservas(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).all();
		
		response().setHeader(Cons.CORS, "*");
		return ok(Json.toJson(reservas));
	}

    @With(SecuredActionAdmin.class)
	public Result darReservasSinAsignar(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).
		where().eq("estado",  Cons.R_ESPERA).findList();
		
		response().setHeader(Cons.CORS, "*");
		return ok(Json.toJson(reservas));
	}

    @With(SecuredActionAdmin.class)
	public Result darReservasVencen(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).where().isNull("ruta").eq("fecha", null).findList();
		
		response().setHeader(Cons.CORS, "*");
		return ok(Json.toJson(reservas));
	}


    @With(SecuredActionAdmin.class)
	@BodyParser.Of(BodyParser.Json.class)
    public Result asignarReserva() {
        JsonNode j = Controller.request().body().asJson();
        Reserva reserva = (Reserva) new Model.Finder(Long.class, Reserva.class).byId(new Long(j.findPath("reservaId").asInt()));
        reserva.setEstado(Cons.R_ASIGNADA);   
        reserva.update();
        
        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(reserva));
    }

    @With(SecuredActionAdmin.class)
    public Result eliminarReservas(){
        List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).all();
        for(int i = 0; i<reservas.size();i++){
            reservas.get(i).delete();
        }

        response().setHeader(Cons.CORS, "*");
        return ok(Json.toJson(""));
    }

    @With(SecuredActionUsuario.class)
    public Result eliminarReserva(Long id){
      Reserva reserva = (Reserva) new Model.Finder(Long.class, Reserva.class).byId(id);
      reserva.delete();
      response().setHeader(Cons.CORS, "*");
      return ok(Json.toJson(""));  
    }
}