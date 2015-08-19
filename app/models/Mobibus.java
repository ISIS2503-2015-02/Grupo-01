package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mobibus extends Vehiculo{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
    private int capacidad;

    private String placa;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Revision> revisiones;

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------



    public Mobibus(){
        
    }

    public Mobibus(double ubicacionX, double ubicacionY,
            String estado, int capacida, String placa, ArrayList<Revision> revisiones) {
        super(ubicacionX, ubicacionY, estado);
        this.capacidad = capacidad;
        this.placa = placa;
        this.revisiones = revisiones;
    }

    //-----------------------------------------------------------
    // Getters & Setters
    //-----------------------------------------------------------

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

    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    public static Mobibus bind(JsonNode j) {
        String laPlaca = j.findPath("placa").asText();
        double x = j.findPath("ubicacionX").asDouble();
        double y = j.findPath("ubicacionY").asDouble();
        String estadoA = j.findPath("estado").asText();
        int caps = j.findPath("capacidad").asInt();
        Mobibus mobibus = new Mobibus(x,y,estadoA, caps, laPlaca, new ArrayList<Revision>());
        return mobibus;
    }
}