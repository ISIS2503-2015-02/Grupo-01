package model;

import java.util.List;
import java.util.Observable;


public class Estacion extends Observable{
	
	/**
	 * Capacidad 
	 */
	private int capacidad;
	
	/**
	 * Identificador de la estacion
	 */
	private Long id;
	
	/**
	 * Ubicacion de la estacion
	 */
	private String ubicacion;
	
	/**
	 * Vector que contiene los Vcubs de la estacion
	 */
	private List<Vcub> vcubs;
	
	/**
	 * El porcentage de ocupacion de la estacion
	 */
	private int cantidad;
	
	public Estacion(){
		
	}

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * @param capacidad the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the vcubs
	 */
	public List<Vcub> getVcubs() {
		return vcubs;
	}

	/**
	 * @param vcubs the vcubs to set
	 */
	public void setVcubs(List<Vcub> vcubs) {
		this.vcubs = vcubs;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}