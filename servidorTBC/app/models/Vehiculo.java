package models;

import com.avaje.ebean.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="tipo_vehiculo",
    discriminatorType=DiscriminatorType.STRING
)
public class Vehiculo extends Model{
	//--------------------------------------------
	//Atributos
	//--------------------------------------------
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String estado;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Posicion> posiciones;
	
	//--------------------------------------------
	//Constructores
	//--------------------------------------------
	
	public Vehiculo(){}

	public Vehiculo(String estado) {
		this.estado = estado;
		this.posiciones = new ArrayList<Posicion>();
	}

	//Getters & Setters
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
	
}
