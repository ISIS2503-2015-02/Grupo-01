package models;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@MappedSuperclass
public class Persona { //extends  Model{


    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    @Id
    private String numeroIdentificacion;
    private int edad;
    private String nombre;
    private String tipoId;
    private String telefono;

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Persona(){};
    public Persona (String numeroIdentificacion, int edad, String nombre,
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

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
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