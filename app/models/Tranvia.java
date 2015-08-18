package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Tranvia extends Vehiculo{

	//-----------------------------------
	// Atributos
	//-----------------------------------

	private double presionChoque;

	private double temperatura;

	private boolean panico;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Revision> revisiones;

	//-----------------------------------
	// Constructores
	//-----------------------------------

	public Tranvia(){
	}

	public Tranvia(double ubicacionX, double ubicacionY,
			String estado, double presionChoque, double temperatura,
			boolean panico, List<Revision> revisiones) {
		super(ubicacionX, ubicacionY, estado);
		this.presionChoque = presionChoque;
		this.temperatura = temperatura;
		this.panico = panico;
		this.revisiones = revisiones;
	}

	// Getters & Setters
	public double getPresionChoque() {
		return presionChoque;
	}

	public void setPresionChoque(double presionChoque) {
		this.presionChoque = presionChoque;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public boolean isPanico() {
		return panico;
	}

	public void setPanico(boolean panico) {
		this.panico = panico;
	}

	public List<Revision> getRevisiones() {
		return revisiones;
	}

	public void setRevisiones(List<Revision> revisiones) {
		this.revisiones = revisiones;
	}

	public void agregarRevision(Revision rev){
		this.revisiones.add(rev);
	}
	// Crea un objeto a partir de un nodo JSon
    public static Tranvia bind(JsonNode j) {
        double ubicacionX = j.findPath("ubicacionX").asDouble();
        double ubicacionY = j.findPath("ubicacionY").asDouble();
        String estado = j.findPath("estado").asText();
        double presionChoque = j.findPath("presionChoque").asDouble();
        double temperatura = j.findPath("temperatura").asDouble();
        boolean panico = j.findPath("panico").asBoolean();
        ArrayList<Revision> revs = new ArrayList<Revision>();
        revs.add(crearRevisionDummy());
        Tranvia tranvia = new Tranvia(ubicacionX, ubicacionY, estado, presionChoque,
         temperatura, panico, new ArrayList<Revision>());
        return tranvia;
    }

}
