
package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import models.Persona;
import play.libs.Json;
import play.mvc.*;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Estacion extends Model 
{
	public String authToken;
	/**
	 * Capacidad 
	 */
	private int capacidad;
	
	/**
	 * Identificador de la estacion
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Ubicacion de la estacion
	 */
	private String ubicacion;

	private double longitud;

	private double latitud;
	
	/**
	 * Vector que contiene los Vcubs de la estacion
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Vcub> vcubs;
	
	/**
	 * Indica si la estacion esta llena
	 */
	private boolean llena;
	
	/**
	 * El porcentage de ocupacion de la estacion
	 */
	private double ocupacion;
	
	public Estacion(){}
	
	//Constructor
	/**
	 * 
	 * @param ID
	 * @param ubicacion
	 */
	public Estacion (int capacidad, String ubicacion, double longitud, double latitud)
	{
		this.capacidad = capacidad;
		this.ubicacion = ubicacion;
		this.vcubs = new ArrayList<Vcub>();
		this.llena = false;
		this.ocupacion = 0.0;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	//Metodos
	
	/**
	 * Devuelve el ID de la estacion
	 * @return El ID. String
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * Devuelve la ubicacion de la ciudad en donde esta la estacion 
	 * @return La ubicacion. String
	 */
	public String getUbicacion() {
		return ubicacion;
	}
	
	/**
	 * Devuelve en vector con los Vcub de la estacion
	 * @return Los Vcub. ArrayList
	 */
	public List<Vcub> getVcubs()
	{
		return vcubs;
	}

	public int getCapacidad(){
		return capacidad;
	}

	/**
	 * Indica si la estacion esta ocupada
	 * @return True si esta ocupada, false de lo contrario
	 */
	public boolean isLlena() 
	{
		return llena;
	}

		/**
	 * Devuelve el porcentage de ocupacion de la estacion
	 * @return
	 */
	public double getOcupacion() 
	{
		return ocupacion;
	}

	/**
	 * Cambia el estado de la estacion
	 * @param llena. Boolean
	 */
	public void setEstado(boolean llena) 
	{
		this.llena = llena;
	}

	public void setCapacidad(int capacidad){
		this.capacidad = capacidad;
	}


	/**
	 * Asigna una nueva ocupacion a la estacion
	 * @param ocupacion. Double
	 */
	public void setOcupacion(double ocupacion) 
	{
		this.ocupacion = ocupacion;
	}

	public void setUbiacion(String ubicacion){
		this.ubicacion = ubicacion;
	}

	public void setVcubs(List<Vcub> vcubs){
		this.vcubs = vcubs;
	}
	
	public void setOcupacion(){
		this.ocupacion = this.vcubs.size()/this.capacidad;
	}
	/**
	 * 
	 */
	public void agregarVcub(Vcub bicicleta)
	{
		vcubs.add(bicicleta);
		actualizarOcupacion();			
	}

	public void actualizarOcupacion(){
		ocupacion = (double) vcubs.size()/capacidad;
		if(ocupacion==1){
			llena=true;
		}
	}

	public void setLongitud(double longitud){
		this.longitud = longitud;
	}

	public double getLongitud(){
		return longitud;
	}

	public void setLatitud(double latitud){
		this.latitud = latitud;
	}

	public double getLatitud(){
		return latitud;
	}
		
	
	/**
	 * Elimina un Vcub de la estacion dado su ID
	 */
	//public void retirarVcub(Long ID)
	//{
	//	for (int i = 0; i < vcubs.size(); i++) 
	//	{
	//		Vcub actual = vcubs.get(i);
	//		if(actual.getId().equals(ID))
	//		{
	//			vcubs.remove(actual);				
	//		}
	//	}
	//	actualizarOcupacion();
	//}

	public static Estacion bind(JsonNode j) {
        int capacidad = j.findPath("capacidad").asInt();
        String ubicacion = j.findPath("ubicacion").asText();
        double latitud = j.findPath("latitud").asDouble();
        double longitud = j.findPath("longitud").asDouble();
        Estacion estacion= new Estacion(capacidad, ubicacion, longitud, latitud);
        return estacion;
    }	
}
