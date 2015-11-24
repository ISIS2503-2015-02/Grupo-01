package models;

import java.util.Date;
import com.fasterxml.jackson.databind.JsonNode;
import com.avaje.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.*;
import play.Logger;

import java.text.SimpleDateFormat;
/**
* Clase que representa una revision tecnomecanica de un vehiculo de TBC
*/
@Entity
public class Revision extends Model {

	//-----------------------------------
	// Atributos
	//-----------------------------------

	/**
    * Id unico de la revision
    */
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
    * Fecha de la ultima revision anterior a esta
    */
	private Date fechaAnterior;
	
	/**
    * Fecha en la que se hizo la revision
    */
	private Date fecha;
	
	/**
    * Kilometraje registrado en la revision
    */
	private double kilometraje;

	/**
    * Tranvia asociado a la revision
    */
	@ManyToOne
	@JoinColumn(name="tranvia_id")
	private Tranvia tranv;

	/**
    * Movibus asociado a la revision
    */
	@ManyToOne
	@JoinColumn(name="mobibus_id")
	private Mobibus mobi;	
	
	//-----------------------------------
	// Constructores
	//-----------------------------------
	
	public Revision(){

	}

	public Revision(Date fechaAnterior, Date fecha, double kilometraje) {
		this.fechaAnterior = fechaAnterior;
		this.fecha = fecha;
		this.kilometraje = kilometraje;
	}

	public void setTranvia(Tranvia tranv){
		this.tranv = tranv;
	}

	public void setMobibus(Mobibus mobi){
		this.mobi = mobi;
	}

	public Tranvia getTranvia(){
		return tranv;
	}

	public Mobibus getMobibus(){
		return mobi;
	}
	
	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaAnterior() {
		return fechaAnterior;
	}

	public void setFechaAnterior(Date fechaAnterior) {
		this.fechaAnterior = fechaAnterior;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(double kilometraje) {
		this.kilometraje = kilometraje;
	}

	// Crea un objeto a partir de un nodo JSon
    public static Revision bind(JsonNode j) {
    	String fechaAntStr = j.findPath("fechaAnt").asText();
    	Date fechaAnt = stringToDate(fechaAntStr);
    	String fechaStr = j.findPath("fecha").asText();
    	Date fecha = stringToDate(fechaStr);
    	double kilometraje = j.findPath("kilometraje").asDouble();
        return new Revision(fechaAnt, fecha, kilometraje);
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
