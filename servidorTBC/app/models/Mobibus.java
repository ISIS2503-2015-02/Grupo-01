package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

@Entity
public class Mobibus extends Model{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
    private String authToken;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    private String estado;

    @OneToMany(cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Posicion> posiciones;

    private int capacidad;

    private String placa;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Revision> revisiones;

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
    public String createToken() {
        authToken = UUID.randomUUID().toString();
        save();
        return authToken;
    }

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

    public static Mobibus bind(JsonNode j) {
        String laPlaca = j.findPath("placa").asText();
        String estadoA = j.findPath("estado").asText();
        int caps = j.findPath("capacidad").asInt();
        return new Mobibus(estadoA, caps, laPlaca, new ArrayList<Revision>());
    }
}