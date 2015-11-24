package models;
import com.avaje.ebean.Model;
import javax.persistence.MappedSuperclass;
import javax.persistence.Id;

/**
* Clase que representa a una persona que juega algun papel en el sistema TBC
*/
@MappedSuperclass
public class Persona extends Model

{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    
    /**
    * Numero de indentificacion unico de la persona en el sistema
    */
    @Id
    private Long numeroIdentificacion;

    /**
    * Elimina el token de sesion
    */
    private int edad;

    /**
    * Nombre de la persona
    */
    private String nombre;

    /**
    * Tipo de numero de identificacion de la persona
    */
    private String tipoId;

    /**
    * Telefono fijo o celular de la persona
    */
    private String telefono;

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Persona(){

    }
    
    public Persona (Long numeroIdentificacion, int edad, String nombre,
                    String tipoId, String telefono){
        this.numeroIdentificacion = numeroIdentificacion;
        this.edad = edad;
        this.nombre = nombre;
        this.tipoId = tipoId;
        this.telefono = telefono;
    }

    //--------------------------------------------
    //Getters & Setters
    //--------------------------------------------

    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(Long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}