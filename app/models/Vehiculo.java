package models;

import com.avaje.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class Vehiculo extends Model{
	//--------------------------------------------
	//Atributos
	//--------------------------------------------
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private double ubicacionX;
	
	private double ubicacionY;
	
	private String estado;
	
	//--------------------------------------------
	//Constructores
	//--------------------------------------------
	
	public Vehiculo(){}

	public Vehiculo(double ubicacionX, double ubicacionY, String estado) {
		this.ubicacionX = ubicacionX;
		this.ubicacionY = ubicacionY;
		this.estado = estado;
	}

	//Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getUbicacionX() {
		return ubicacionX;
	}

	public void setUbicacionX(double ubicacionX) {
		this.ubicacionX = ubicacionX;
	}

	public double getUbicacionY() {
		return ubicacionY;
	}

	public void setUbicacionY(double ubicacionY) {
		this.ubicacionY = ubicacionY;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
