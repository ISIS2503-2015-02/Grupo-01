package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reserva extends Model {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    private String estado;

    private String fecha;

    private double costo;

    private int turno;

    private Ruta ruta;

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------

    public Reserva (String nEstado, String nFecha, double nCosto, int nTurno, Ruta nRuta){
        estado = nEstado;
        fecha = nFecha;
        costo = nCosto;
        turno = nTurno;
        ruta = nRuta;
    }

    //-----------------------------------------------------------
    // Getters & Setters
    //-----------------------------------------------------------

    public String getEstado(){
        return estado;
    }

    public String getFecha(){
        return fecha;
    }

    public double getCosto(){
        return costo;
    }

    public int getTurno(){
        return turno;
    }

    public void setEstado(String nEstado){
        estado = nEstado;
    }

    public void setFecha(String nFecha){
        fecha = nFecha;
    }

    public void setCosto(double nCosto){
        costo = nCosto;
    }

    public void setTurno(int nTurno){
        turno = nTurno;
    }

    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    public static Reserva bind(JsonNode j){
        String esta = j.findPath("estado").asText();
        String fechaa = j.findPath("fecha").asText();
        double elCosto = j.findPath("costo").asDouble();
        int turnoEnFila = j.findPath("turno").asInt();
        JsonNode rutaJson = j.get("ruta");
        Ruta rout = Ruta.bind(rutaJson);
        Reserva reserva = new Reserva(esta, fechaa, elCosto, turnoEnFila, rout);
        return reserva;
    }
}