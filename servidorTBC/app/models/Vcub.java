
package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;


@Entity
public class Vcub extends Model
{
		
	//Atributos 
		
		/**
		 * Estado del Vcub
		 */

		public String authToken;
		
		@Id
   		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long id;
	
		private String estado;

		@OneToMany(cascade=CascadeType.ALL)
		private List<Posicion> posiciones;

		@OneToOne
		@JoinColumn(name="usuario_numero_identificacion")
		private Usuario usuario;
		
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
        authToken = UUID.randomUUID().toString();
        save();
        return authToken;
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
		
	public static Vcub bind(JsonNode j) {
       	String estado = j.findPath("estado").asText();
       	Vcub vcub= new Vcub(estado);
       	return vcub;
   	}	
}
