package models;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Usuario  extends Persona{


    public String authToken;

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private String condicion;

    @Column(unique = true)
    public String usuario;

    public String password;

    @OneToMany(cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Reserva> reservas;

    public static Finder<Integer,Usuario> find = new Finder(Integer.class, Usuario.class);

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Usuario(){ super();}

    public Usuario(String user, String pass,Long identificacion, int edad, String nombre, String tipoId, String telefono,
                   String condicion, List<Reserva> nReservas){
        super(identificacion, edad, nombre, tipoId, telefono);
        this.condicion = condicion;
        this.usuario = user;
        this.password = pass;
        reservas = nReservas;
    }
    //--------------------------------------------
    //Metodos token
    //--------------------------------------------
    public String createToken() {
        authToken = UUID.randomUUID().toString();
        save();
        return authToken;
    }

     public void deleteAuthToken() {
        authToken = null;
        save();
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

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public String getUsuario(){
        return usuario;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public static Usuario bind(JsonNode j) {
        String user = j.findPath("user").asText();
        String pass = j.findPath("pass").asText();
        Long id = new Long(j.findPath("identificacion").asInt());
        int edad = j.findPath("edad").asInt();
        String nombre = j.findPath("nombre").asText();
        String tipoId = j.findPath("tipoId").asText();
        String telefono = j.findPath("telefono").asText();
        String condicion = j.findPath("condicion").asText();
        Usuario usuario = new Usuario(user, pass, id, edad, nombre, tipoId, telefono, condicion, new ArrayList<Reserva>());
        return usuario;
    }
}