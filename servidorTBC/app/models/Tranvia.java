package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import play.libs.Crypto;
import java.util.UUID;

/**
* Clase que representa a un tranvia de TBC
*/
@Entity
public class Tranvia extends Model{

	//-----------------------------------
	// Atributos
	//-----------------------------------

	/**
    * Token de sesion
    */
	private String authToken;
	
	/**
    * Id unico del tranvia
    */
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	/**
    * Estado actual del tranvia
    */
	private String estado;

	/**
    * Lista con todas las posiciones del tranvia
    */
	@OneToMany(cascade=CascadeType.ALL)
	private List<Posicion> posiciones;

	/**
    * Valor que marca el sensor de presion de choque del tranvia
    */
	private double presionChoque;

	/**
    * Valor que marca el sensor de temperatura del tranvia
    */
	private double temperatura;

	/**
    * Indica si el boton de panico del tranvia fue oprimido
    */
	private boolean panico;
	
	/**
    * Lista de todas las revisiones tecnomecanicas del tranvia
    */
	@OneToMany(cascade=CascadeType.ALL)
	private List<Revision> revisiones;

	/**
    * Ruta actual que recorre el tranvia
    */
	@ManyToOne
	@JoinColumn(name="ruta_id")
	@JsonBackReference
	private Ruta ruta;

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

	//--------------------------------------------
    //Metodos token
    //--------------------------------------------

    /**
    * Asigna un nuevo token de sesion para autorizacion
    */
    public String createToken() {
        String tok = UUID.randomUUID().toString();
        authToken = tok;
        save();
        return tok;
    }

    /**
    * Elimina el token de sesion
    */
     public void deleteAuthToken() {
        authToken = null;
        save();
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

	@JsonIgnore
	public Ruta getRuta(){
        return ruta;
    }

    public void setRuta(Ruta ruta){
        this.ruta = ruta;
    }
    
	// Crea un objeto a partir de un nodo JSon
    public static Tranvia bind(JsonNode j) {
        String estado = j.findPath("estado").asText();
        double presionChoque = j.findPath("presionChoque").asDouble();
        double temperatura = j.findPath("temperatura").asDouble();
        boolean panico = j.findPath("panico").asBoolean();
        List<Revision> revs = new ArrayList<Revision>();
        return new Tranvia( estado, presionChoque,
         temperatura, panico, revs);
    }

}
