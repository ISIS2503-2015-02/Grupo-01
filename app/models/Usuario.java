package models;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario  extends Persona{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private String condicion;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Reserva> reservas;


    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Usuario(){ super();}
    public Usuario(String numeroIdentificacion, int edad, String nombre,
                    String tipoId, String telefono, String condicion, List<Reserva> nReservas){
        super(numeroIdentificacion, edad, nombre, tipoId, telefono);
        this.condicion = condicion;
        reservas = nReservas;
    }

    //--------------------------------------------
    //Getters & Setters
    //--------------------------------------------
    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
    
    public void setReservas(List<Reserva> reservas){
        this.reservas = reservas;
    }

    public List<Reserva> getReservas(){
        return reservas;
    }

    public void addReserva(Reserva nReserva){
        reservas.add(nReserva);
    }


    public static Usuario bind(JsonNode j) {
        String identificacion = j.findPath("identificacion").asText();
        int edad = j.findPath("edad").asInt();
        String nombre = j.findPath("nombre").asText();
        String tipoId = j.findPath("tipoId").asText();
        String telefono = j.findPath("telefono").asText();
        String condicion = j.findPath("condicion").asText();
        Usuario usuario = new Usuario(identificacion, edad, nombre, tipoId, telefono, condicion, new ArrayList<Reserva>());
        return usuario;
    }
}