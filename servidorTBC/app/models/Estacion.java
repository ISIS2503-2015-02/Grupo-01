
package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import play.mvc.*;
import javax.persistence.*;
import play.libs.Crypto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* Clase que representa una estacion de vcubs en el contexto de la empresa TBC
*/
@Entity
public class Estacion extends Model 
{

	/**
	* Token de sesion para controlar autorizacion en el sistema
	*/
	private String authToken;

	/**
	 * Capacidad de la estacion
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

	/**
	* Longitud de la ubicacion de la estacion
	*/
	private double longitud;

	/**
	* Latitud de la ubicacion de la estacion
	*/
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
	
	/**
	* Constructor vacio requerido
	*/
	public Estacion(){

	}
	
	//--------------------------
	//Constructor
	//--------------------------

	/**
	* Construye una estacion con datos iniciales
	* @param capacidad - int la capacidad maxima de la estacion
	* @param ubicacion - String la ubicacion de la estacion
	* @param longitud - double longitud donde se encuentra la estacion
	* @param latitud - double latitud ddonde se encuentra la estacion
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

	//---------------------------------
	// Metodos
	//---------------------------------

		//--------------------------------------------
	    //Metodos token
	    //--------------------------------------------

    /**
	* Asigna un nuevo token de sesion aleatorio a la estacion
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

    //--------------------------------------------
    //Getters & Setters
    //--------------------------------------------

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

	/**
	* Cambia la capacidad de la estacion
	* @param capacidad int
	*/
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

	/**
	* Asigna el valor de la ubicacion
	* @param ubicacion Strin la nueva ubicacion
	*/
	public void setUbiacion(String ubicacion){
		this.ubicacion = ubicacion;
	}

	/**
	* Asigna el valor de la las vcbus de la estacion
	*/
	public void setVcubs(List<Vcub> vcubs){
		this.vcubs = vcubs;
	}
	
	/**
	* Asigna e valor de la ocupacion de la estacion
	* lo hace con respecto a la cantidad que esten en la lista, y su capacidad
	*/
	public void setOcupacion(){
		this.ocupacion = (double) this.vcubs.size()/this.capacidad;
	}

	/**
	 * Agrega una bicicleta a la estacion
	 * @param bicicleta Vcub una bicicleta
	 */
	public void agregarVcub(Vcub bicicleta)
	{
		vcubs.add(bicicleta);
		actualizarOcupacion();			
	}

	/**
	* Actualizza el valor de la ocupacion de la estacion
	* he indica si el cambio es la dejo llena
	*/
	public void actualizarOcupacion(){
		ocupacion = (double) vcubs.size()/capacidad;
		if(vcubs.size()-capacidad == 0){
			llena=true;
		}
	}

	/**
	*  Asigna el valor de la longitud de la estacion
	* @param longitud la longitud de a poner - double
	*/
	public void setLongitud(double longitud){
		this.longitud = longitud;
	}

	/**
	* retorna la longitud
	* @return double la longitud
	*/
	public double getLongitud(){
		return longitud;
	}

	/**
	* asigna la latitud
	* @param latitud double 
	*/
	public void setLatitud(double latitud){
		this.latitud = latitud;
	}

	/**
	* retorna la latitud 
	* @eturn latitud
	*/
	public double getLatitud(){
		return latitud;
	}
		
	/**
    * Permite crear una nueva estacion a partir de un nodo Json
    */
	public static Estacion bind(JsonNode j) {
        int capacidad = j.findPath("capacidad").asInt();
        String ubicacion = j.findPath("ubicacion").asText();
        double latitud = j.findPath("latitud").asDouble();
        double longitud = j.findPath("longitud").asDouble();
        return new Estacion(capacidad, ubicacion, longitud, latitud);
    }	
}
