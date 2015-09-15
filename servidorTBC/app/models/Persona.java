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
import java.util.Date;


@MappedSuperclass
public class Persona extends Model

{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long numeroIdentificacion;

    private int edad;

    private String nombre;

    private String tipoId;

    private String telefono;

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Persona(){};
    public Persona (int edad, String nombre,
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


    public static Persona bind(JsonNode j) {
        int edad = j.findPath("edad").asInt();
        String nombre = j.findPath("nombre").asText();
        String tipoId = j.findPath("tipoId").asText();
        String telefono = j.findPath("telefono").asText();
        Persona persona = new Persona(edad,nombre,tipoId,telefono);
        return persona;
    }


}