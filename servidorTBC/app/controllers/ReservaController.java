package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import actions.CorsComposition;
import actions.ForceHttps;

import java.util.List;

@With(SecuredAction.class)
@CorsComposition.Cors
//@ForceHttps.Https
public class ReservaController extends Controller{

	public Result darReservas(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).all();
		
		response().setHeader("Access-Control-Allow-Origin", "*");
		return ok(Json.toJson(reservas));
	}

	public Result darReservasSinAsignar(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).
		where().eq("estado",  Cons.R_ESPERA).findList();
		
		response().setHeader("Access-Control-Allow-Origin", "*");
		return ok(Json.toJson(reservas));
	}

	public Result darReservasVencen(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).where().isNull("ruta").eq("fecha", Reserva.maniana()).findList();
		
		response().setHeader("Access-Control-Allow-Origin", "*");
		return ok(Json.toJson(reservas));
	}


	@BodyParser.Of(BodyParser.Json.class)
    public Result asignarReserva() {
        JsonNode j = Controller.request().body().asJson();
        Reserva reserva = (Reserva) new Model.Finder(Long.class, Reserva.class).byId(new Long(j.findPath("reservaId").asInt()));
        reserva.setEstado(Cons.R_ASIGNADA);   
        reserva.update();
        
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(reserva));
    }

    public Result eliminarReservas(){
        List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).all();
        for(int i = 0; i<reservas.size();i++){
            reservas.get(i).delete();
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }

    public Result eliminarReserva(Long id){
      Reserva reserva = (Reserva) new Model.Finder(Long.class, Reserva.class).byId(id);
      reserva.delete();
      response().setHeader("Access-Control-Allow-Origin", "*");
      return ok(Json.toJson(""));  
    }
}