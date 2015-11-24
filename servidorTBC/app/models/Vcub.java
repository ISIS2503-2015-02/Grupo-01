
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
* Clase que representa una vcub en el sistema TBC
*/
@Entity
public class Vcub extends Model
{
	//-----------------------------------------------------	
	//Atributos 
	//-----------------------------------------------------	

	/**
    * Token de sesion
    */
	private String authToken;
		
	/**
    * Id unico del vcub
    */	
	@Id
   	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	/**
    * Estado actual del vcub
    */
	private String estado;

	/**
    * Lista con todas las posiciones de la vcub
    */
	@OneToMany(cascade=CascadeType.ALL)
	private List<Posicion> posiciones;

	/**
    * Usuario que tiene rentada la vcub actualmente
    */
	@OneToOne
	@JoinColumn(name="usuario_numero_identificacion")
	private Usuario usuario;
		
	/**
    * Estacion en donde se encuentra la vcub actualmente
    */	
	@ManyToOne
	@JoinColumn(name="estacion_id")
	@JsonBackReference
	private Estacion estacion;

		
	//Constructor 
		

	public Vcub (String estado)
	{
		this.estado = estado;
		this.posiciones = new ArrayList<Posicion>();
		this.usuario = null;
		this.estacion = null;
	}
		
	//--------------------------------------------
    //Metodos token
    //--------------------------------------------
    public String createToken() {
        String tok = UUID.randomUUID().toString();
        authToken = tok;
        save();
        return tok;
    }

     public void deleteAuthToken() {
        authToken = null;
        save();
    }

	// Metodos
		
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

	public Usuario getUsuario(){
		return usuario;
	}

	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}

	public Estacion getEstacion(){
		return estacion;
	}

	public void setEstacion(Estacion estacion){
		this.estacion = estacion;
	}
		
	/**
    * Permite crear una vcub a partir de un nodo Json
    */	
	public static Vcub bind(JsonNode j) {
       	String estado = j.findPath("estado").asText();
       	return new Vcub(estado);
   	}	
}
