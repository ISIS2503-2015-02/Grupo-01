package models;

import java.util.Date;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import com.avaje.ebean.Model;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import play.Logger;

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

	@ManyToOne
	@JoinColumn(name="tranvia_id")
	@JsonBackReference
	private Tranvia tranvia;

	@ManyToOne
	@JoinColumn(name="vcub_id")
	@JsonBackReference
	private Vcub vcub;

	@ManyToOne
	@JoinColumn(name="mobibus_id")
	@JsonBackReference
	private Mobibus mobibus;
	
	
	//-----------------------------------
	// Constructores
	//-----------------------------------
	
	public Posicion(){

	}

	public Posicion(double latitud, double longitud, Date fecha) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.fecha = fecha;
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

	public Tranvia getTranvia() {
		return tranvia;
	}

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

	// Crea un objeto a partir de un nodo JSon
    public static Posicion bind(JsonNode j) {
    	double latitud = j.findPath("latitud").asDouble();
    	double longitud = j.findPath("longitud").asDouble();
        return new Posicion(latitud, longitud, new Date());
    }

    // Auxiliar

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
