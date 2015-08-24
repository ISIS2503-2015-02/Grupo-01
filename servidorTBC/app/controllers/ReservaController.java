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

	@BodyParser.Of(BodyParser.Json.class)
	public Result crearReserva(){
		JsonNode j = Controller.request().body().asJson();
        Reserva reserva = Reserva.bind(j);
        reserva.save();

        return ok(Json.toJson(reserva));
	}

	public Result darReservas(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).all();
		return ok(Json.toJson(reservas));
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result asignarRuta(Long id){
		JsonNode j = Controller.request().body().asJson();
		Ruta ruta = Ruta.bind(j);

		Reserva reserva = (Reserva) new Model.Finder(Long.class, Reserva.class).byId(id);
		reserva.setRuta(ruta);

		reserva.update();

		return ok(Json.toJson(reserva));
	}

	public Result darReservasSinRuta(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).
		where().isNull("ruta").findList();
		return ok(Json.toJson(reservas));
	}

	public Result darReservasVencen(){
		List<Reserva> reservas = new Model.Finder(Long.class, Reserva.class).where().isNull("ruta").eq("fecha", Reserva.maniana()).findList();
		return ok(Json.toJson(reservas));
	}
}