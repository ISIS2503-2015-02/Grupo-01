package model;

public class Vcub {

	/**
	 * Estado del Vcub
	 */
	private long id;

	private String estado;

	private Usuario usuario;
	
	private Estacion estacion;
	
	public Vcub(){
		
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the estacion
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * @param estacion the estacion to set
	 */
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}
	
	public String toString(){
		return "Vcub -" + id;
	}

}
