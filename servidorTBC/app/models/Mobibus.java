package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import play.libs.Crypto;

import java.util.UUID;

/**
* Clase que representa un movibus de la empresa TBC
*/
@Entity
public class Mobibus extends Model{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
    /**
    * Token de sesion para controlar autorizacion en el sistema
    */
    private String authToken;
    
    /**
    * Id unico del movibus en el sistema
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    /**
    * Estado actual del movibus
    */
    private String estado;

    /**
    * Lista de todas las posiciones que ha tenido el movibus
    */
    @OneToMany(cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Posicion> posiciones;

    /**
    * Capacidad maxima de usuarios que pueden subir al movibus
    */
    private int capacidad;

    /**
    * Placa del movibus
    */
    private String placa;

    /**
    * Lista con todas las revisiones tecnomecanicas que ha tenido el movibus
    */
    @OneToMany(cascade=CascadeType.ALL)
    private List<Revision> revisiones;

    /**
    * Ruta actual que esta siguiendo el mobibus
    */
    @ManyToOne
    @JoinColumn(name="ruta_id")
    @JsonBackReference
    private Ruta ruta;

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------


    public Mobibus(){
        
    }

    public Mobibus(String estado, int capacidad, String placa, List<Revision> revisiones) {
        this.estado = estado;
        this.posiciones = new ArrayList<Posicion>();
        this.capacidad = capacidad;
        this.placa = placa;
        this.revisiones = revisiones;
    }

    //--------------------------------------------
    //Metodos token
    //--------------------------------------------

    /**
    * Asigna un nuevo token de sesion aleatorio a la estacion
    */
    public String createToken() {
        String tok = UUID.randomUUID().toString();
        authToken = tok;
        save();
        return tok;
    }

    /**
    * Elimina el token de sesion
    */
     public void deleteAuthToken() {
        authToken = null;
        save();
    }

    //-----------------------------------------------------------
    // Getters & Setters
    //-----------------------------------------------------------

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

    public void agregarPosicion(Posicion pos){
        posiciones.add(pos);
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String nPlaca) {
        placa = nPlaca;
    }

    public int getCapacidad(){
        return capacidad;
    }

    public void setCapacidad(int nCapacidad){
        capacidad = nCapacidad;
    }

    public List<Revision> getRevisiones() {
        return revisiones;
    }

    public void setRevisiones(List<Revision> revisiones) {
        this.revisiones = revisiones;
    }

    public void agregarRevision(Revision rev){
        this.revisiones.add(rev);
    }

    @JsonIgnore
    public Ruta getRuta(){
        return ruta;
    }

    public void setRuta(Ruta ruta){
        this.ruta = ruta;
    }

    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------
    /**
    * Permite crear un nuevo movibus a partir de un nodo Json
    */
    public static Mobibus bind(JsonNode j) {
        String laPlaca = j.findPath("placa").asText();
        String estadoA = j.findPath("estado").asText();
        int caps = j.findPath("capacidad").asInt();
        return new Mobibus(estadoA, caps, laPlaca, new ArrayList<Revision>());
    }
}