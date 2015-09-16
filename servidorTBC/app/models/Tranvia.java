package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Tranvia extends Model{

	//-----------------------------------
	// Atributos
	//-----------------------------------

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String estado;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Posicion> posiciones;

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

	public Tranvia(String estado, double presionChoque, double temperatura,
			boolean panico, List<Revision> revisiones) {
		this.estado = estado;
		this.posiciones = new ArrayList<Posicion>();
		this.presionChoque = presionChoque;
		this.temperatura = temperatura;
		this.panico = panico;
		this.revisiones = revisiones;
	}

	// Getters & Setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public List<Posicion> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(List<Posicion> posiciones) {
		this.posiciones = posiciones;
	}

	public void agregarPosicion(Posicion pos){
        posiciones.add(pos);
    }
	
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
        String estado = j.findPath("estado").asText();
        double presionChoque = j.findPath("presionChoque").asDouble();
        double temperatura = j.findPath("temperatura").asDouble();
        boolean panico = j.findPath("panico").asBoolean();
        ArrayList<Revision> revs = new ArrayList<Revision>();
        Tranvia tranvia = new Tranvia( estado, presionChoque,
         temperatura, panico, new ArrayList<Revision>());
        return tranvia;
    }

}
