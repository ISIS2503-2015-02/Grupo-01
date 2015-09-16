package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mobibus extends Model{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
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

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------



    public Mobibus(){
        
    }

    public Mobibus(String estado, int capacidad, String placa, ArrayList<Revision> revisiones) {
        this.estado = estado;
        this.posiciones = new ArrayList<Posicion>();
        this.capacidad = capacidad;
        this.placa = placa;
        this.revisiones = revisiones;
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

    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    public static Mobibus bind(JsonNode j) {
        String laPlaca = j.findPath("placa").asText();
        String estadoA = j.findPath("estado").asText();
        int caps = j.findPath("capacidad").asInt();
        Posicion pos = Posicion.bind(j);
        Mobibus mobibus = new Mobibus(estadoA, caps, laPlaca, new ArrayList<Revision>());
        return mobibus;
    }
}