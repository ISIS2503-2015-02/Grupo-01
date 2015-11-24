package models;

import java.util.Date;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import com.avaje.ebean.Model;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import play.Logger;

import java.text.SimpleDateFormat;

/**
* Clase que representa una posicion de cualquier vehiculo de la empresa TBC
*/
@Entity
public class Posicion extends Model {

	//-----------------------------------
	// Atributos
	//-----------------------------------

	/**
	* Id unico de la posicion en el sistema
	*/
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	* Latitud de la posicion
	*/
	private double latitud;
	
	/**
	* Longitud de la posicion
	*/
	private double longitud;

	/**
	* Fecha en que se registro la posicion
	*/
	private Date fecha;

	/**
	* Tranvia asociado a esta posicion
	*/
	@ManyToOne
	@JoinColumn(name="tranvia_id")
	@JsonBackReference
	private Tranvia tranvia;

	/**
	* Vcub asociado a esta posicion
	*/
	@ManyToOne
	@JoinColumn(name="vcub_id")
	@JsonBackReference
	private Vcub vcub;

	/**
	* Movibus asociado a esta posicion
	*/
	@ManyToOne
	@JoinColumn(name="mobibus_id")
	@JsonBackReference
	private Mobibus mobibus;
	
	
	//-----------------------------------
	// Constructores
	//-----------------------------------
	
	/**
	* Construye una posicion sin datos asociados
	*/
	public Posicion(){

	}

	/**
	* Crea una posicion con dato de longitud, latitud, y fecha
	* @param latitud la latitud de la posicion
	* @param longitud de la posicion
	* @param fecha fecha de creacion de la posicion
	*/
	public Posicion(double latitud, double longitud, Date fecha) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.fecha = fecha;
	}

	//-----------------------------------
	// Getters & Setters
	//-----------------------------------

	/**
	* getter del id de la posicion
	* @return Long retorna el id de la posicon
	*/
	public Long getId() {
		return id;
	}


	/**
	* setter del id
	* @param id el nuevo id
	*/
	public void setId(Long id) {
		this.id = id;
	}

	/**
	* getter de la fecha de la posicion
	* @return Date la fecha 
	*/
	public Date getFecha() {
		return fecha;
	}
	/**
	* asigna a fecha
	* @param fecha - la nueva fecha - date
	*/
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	* retorna la latitud
	* @return double 
	*/
	public double getLatitud() {
		return latitud;
	}

	/**
	* asigna la latitud
	* @param latitud - double
	*/
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	/**
	* retorna la longitud
	* @return double la longitud
	*/
	public double getLongitud() {
		return longitud;
	}

	/**
	* asigna la longitud
	* @param longitudd - double
	*/
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	/**
	* retorna el tranvia asociado, en caso de que exista
	* @return Tranvia
	*/
	public Tranvia getTranvia() {
		return tranvia;
	}

	/**
	* asigna el tranvia
	* @param tranvia
	*/
	public void setTranvia(Tranvia tranvia) {
		this.tranvia = tranvia;
	}

	
	public Vcub getVcub() {
		return vcub;
	}

	public void setVcub(Vcub vcub) {
		this.vcub = vcub;
	}

	public Mobibus getMobibus() {
		return mobibus;
	}

	public void setMobibus(Mobibus mobibus) {
		this.mobibus = mobibus;
	}

	/**
	* Crea una posicion a partir de un nodo Json
	*/
    public static Posicion bind(JsonNode j) {
    	double latitud = j.findPath("latitud").asDouble();
    	double longitud = j.findPath("longitud").asDouble();
        return new Posicion(latitud, longitud, new Date());
    }

    // Auxiliar

    /**
	* Permite convertir una fecha de tipo String a tipo Date
	*/
    public static Date stringToDate(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try{
			return formatter.parse(dateStr);
		}
		catch(Exception e){
			Logger.info(e.getMessage());
			return null;
		}
    }
	
	
	
	
}
