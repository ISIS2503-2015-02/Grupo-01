package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class ReservaController extends Controller{

	public Result darReservas(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).all();
		return ok(Json.toJson(reservas));
	}

	public Result darReservasSinAsignar(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).
		where().eq("estado",  Cons.R_ESPERA).findList();
		return ok(Json.toJson(reservas));
	}

	public Result darReservasVencen(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).where().isNull("ruta").eq("fecha", Reserva.maniana()).findList();
		return ok(Json.toJson(reservas));
	}


	@BodyParser.Of(BodyParser.Json.class)
    public Result asignarReserva() {
        JsonNode j = Controller.request().body().asJson();
        Reserva reserva = (Reserva) new Model.Finder(Long.class, Reserva.class).byId(new Long(j.findPath("reservaId").asInt()));
        reserva.setEstado(Cons.R_ASIGNADA);   
        reserva.update();
        return ok(Json.toJson(reserva));
    }

    public Result eliminarReservas(){
        List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).all();
        for(int i = 0; i<reservas.size();i++){
            reservas.get(i).delete();
        }

        return ok(Json.toJson(""));
    }
}