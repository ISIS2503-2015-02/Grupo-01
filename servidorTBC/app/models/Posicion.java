package models;

import java.util.Date;
import com.fasterxml.jackson.databind.JsonNode;
import com.avaje.ebean.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
public class Posicion extends Model {

	//-----------------------------------
	// Atributos
	//-----------------------------------

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private double latitud;
	
	private double longitud;

	private Date fecha;

	private Long vehiculoIdent;

	@ManyToOne
	@JoinColumn(name="vehiculo_id")
	private Vehiculo vehiculo;
	
	
	//-----------------------------------
	// Constructores
	//-----------------------------------
	
	public Posicion(){}

	public Posicion(double latitud, double longitud, Date fecha, Long vehiculoIdent) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.fecha = fecha;
		this.vehiculoIdent = vehiculoIdent;
	}
	
	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Long getVehiculoId() {
		return vehiculoIdent;
	}

	public void setvehiculoId(Long vehiculoIdent) {
		this.vehiculoIdent = vehiculoIdent;
	}

	// Crea un objeto a partir de un nodo JSon
    public static Posicion bind(JsonNode j) {
    	String fechaStr = j.findPath("fecha").asText();
    	Date fecha = stringToDate(fechaStr);
    	double latitud = j.findPath("latitud").asDouble();
    	double longitud = j.findPath("longitud").asDouble();
    	int vehiculoIdent = j.findPath("vehiculoIdent").asInt();
        Posicion posicion = new Posicion(latitud, longitud, fecha, new Long(vehiculoIdent));
        return posicion;
    }

    // Auxiliar

    public static Date stringToDate(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try{
			Date parsedDate = formatter.parse(dateStr);
			return parsedDate;
		}
		catch(Exception e){
			return null;
		}
    }
	
	
	
	
}
