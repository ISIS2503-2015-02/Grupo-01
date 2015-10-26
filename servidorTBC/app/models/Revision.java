package models;

import java.util.Date;
import com.fasterxml.jackson.databind.JsonNode;
import com.avaje.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import utils.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
public class Revision extends Model {

	//-----------------------------------
	// Atributos
	//-----------------------------------

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Date fechaAnterior;
	
	private Date fecha;
	
	private double kilometraje;

	@ManyToOne
	@JoinColumn(name="tranvia_id")
	private Tranvia tranv;

	@ManyToOne
	@JoinColumn(name="mobibus_id")
	private Mobibus mobi;	
	
	//-----------------------------------
	// Constructores
	//-----------------------------------
	
	public Revision(){}

	public Revision(Date fechaAnterior, Date fecha, double kilometraje) {
		this.fechaAnterior = fechaAnterior;
		this.fecha = fecha;
		this.kilometraje = kilometraje;
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
        Revision revision = new Revision(fechaAnt, fecha, kilometraje);
        return revision;
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
