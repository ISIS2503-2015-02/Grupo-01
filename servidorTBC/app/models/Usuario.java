package models;

import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import play.libs.Crypto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
* Clase que representa a un usuario del sistema de reservas y monitoreo TBC
*/
@Entity
public class Usuario extends Persona{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    

    /**
    * Token de sesion 
    */
    private String authToken;

    /**
    * Rol que otorga ciertos permisos a un usuario
    */
    private String rol;

    /**
    * Condicion de salud del usuario
    */
    private String condicion;

    /**
    * Nombre de usuario para el portal web
    */
    @Column(unique = true)
    private String user;

    /**
    * Contrasena de usuario para el portal web
    */
    private String password;

    /**
    * Lista de todas las reservas de movibus que ha hecho el usuario
    */
    @OneToMany(cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Reserva> reservas;

    /**
    * permite encontrar un usuario
    */
    public static final Finder<Integer,Usuario> find = new Finder(Integer.class, Usuario.class);

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Usuario(){
        super();
    }

    public Usuario(String user, String pass,Long identificacion, int edad, String nombre, String tipoId, String telefono,
                   String condicion, List<Reserva> nReservas, String rol){
        super(identificacion, edad, nombre, tipoId, telefono);
        this.condicion = condicion;
        this.user = user;
        this.password = pass;
        reservas = nReservas;
        this.rol = rol;
    }
    //--------------------------------------------
    //Metodos token
    //--------------------------------------------

    /**
    * Crea un token de sesion para controlar autorizacion
    */
    public String createToken() {
        String tok = UUID.randomUUID().toString();
        authToken = tok;
        save();
        return tok;
    }

    /**
    * Elimina un token de sesion
    */
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
        this.user = usuario;
    }

    public String getUsuario(){
        return user;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(String rol){
        this.rol = rol;
    }

    /**
    * Permite crear un usuario a partir de un nodo Json
    */
    public static Usuario bind(JsonNode j) {
        String user = j.findPath("user").asText();
        String pass = j.findPath("pass").asText();
        Long id = new Long(j.findPath("identificacion").asInt());
        int edad = j.findPath("edad").asInt();
        String nombre = j.findPath("nombre").asText();
        String tipoId = j.findPath("tipoId").asText();
        String telefono = j.findPath("telefono").asText();
        String condicion = j.findPath("condicion").asText();
        String rol = j.findPath("rol").asText();
        return new Usuario(user, pass, id, edad, nombre, tipoId, telefono, condicion, new ArrayList<Reserva>(), rol);
    }
}