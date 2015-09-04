
package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Vcub extends Model
{
		
	//Atributos 
		
		/**
		 * Estado del Vcub
		 */
		@Id
   		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long id;
	
		private String estado;

		@OneToMany(cascade=CascadeType.ALL)
		private List<Posicion> posiciones;

		private Usuario usuario;
		
		@ManyToOne
		@JoinColumn(name="estacion_id")
		private Estacion estacion;

		
	//Constructor 
		

	public Vcub (String estado)
	{
		this.estado = estado;
		this.posiciones = new ArrayList<Posicion>();
		this.usuario = null;
		this.estacion = null;
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
