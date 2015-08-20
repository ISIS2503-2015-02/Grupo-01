package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Persona;
import play.libs.Json;
import play.mvc.*;
import javax.persistence.*;
import javax.management.modelmbean.ModelMBeanAttributeInfo;


@Entity
public class Vcub extends Vehiculo
{
		
	//Atributos 
		
		/**
		 * Estado del Vcub
		 */
		private Usuario usuario;
		
		@ManyToOne
		@JoinColumn(name="estacion_id")
		private Estacion estacion;

		
	//Constructor 
		

		public Vcub (double ubicacionX, double ubicacionY, String estado)
		{
			super(ubicacionX, ubicacionY, estado);
			this.usuario = null;
			this.estacion = null;
		}
		
	// Metodos
		
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
        	double ubicacionX = j.findPath("ubicacionX").asDouble();
        	double ubicacionY = j.findPath("ubicacionY").asDouble();
        	String estado = j.findPath("estado").asText();
        	Vcub vcub= new Vcub(ubicacionX, ubicacionY, estado);
        	return vcub;
    	}	
}
