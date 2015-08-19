package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ruta extends Model {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    private String ubicaiconOrigen;

    private String ubicacionDestino;

    private String tipo;

    private  double tiempoTrayecto;

    private String terminado;

    private String tipoAccidente;

    private Mobibus bus;

    private Tranvia tranvia;

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------

    public Ruta(String ubicaiconOrigen, String ubicacionDestino,
            String tipo, double tiempoTrayecto, String terminado,
            String tipoAccidente, Mobibus bus, Tranvia tranvia) {
        super();
        this.ubicaiconOrigen = ubicaiconOrigen;
        this.ubicacionDestino = ubicacionDestino;
        this.tipo = tipo;
        this.tiempoTrayecto = tiempoTrayecto;
        this.terminado = terminado;
        this.tipoAccidente = tipoAccidente;
        this.bus = bus;
    }

    public Ruta(){

    }

    //-----------------------------------------------------------
    // Getters & Setters
    //-----------------------------------------------------------

    public String getUbicaiconOrigen() {
        return ubicaiconOrigen;
    }

    public void setUbicaiconOrigen(String ubicaiconOrigen) {
        this.ubicaiconOrigen = ubicaiconOrigen;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTiempoTrayecto() {
        return tiempoTrayecto;
    }

    public void setTiempoTrayecto(double tiempoTrayecto) {
        this.tiempoTrayecto = tiempoTrayecto;
    }

    public String getTerminado() {
        return terminado;
    }

    public void setTerminado(String terminado) {
        this.terminado = terminado;
    }

    public String getTipoAccidente() {
        return tipoAccidente;
    }

    public void setTipoAccidente(String tipoAccidente) {
        this.tipoAccidente = tipoAccidente;
    }

    public Mobibus getBus() {
        return bus;
    }

    public void setBus(Mobibus bus) {
        this.bus = bus;
    }

    public Tranvia getBus() {
        return bus;
    }

    public void setBus(Tranvia tren {
        this.tranvia = tren;
    }
    
    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    public static Ruta bind(JsonNode j) {
        String ubicacionOri = j.findPath("ubicacionOrigen").asText();
        String ubicacionDes = j.findPath("ubicacionDestino").asText();
        double tiempoTrayecto = j.findPath("tiempoTrayecto").asDouble();
        String tipo = j.findPath("tipo").asText();
        String terminado = j.findPath("terminado").asText();
        String tipoAccidente = j.findPath("accidente").asText();
        Vehiculo carro;
        if(tipo.equals("tranvia")){
            carro = Tranvia.bind(j.findPath("tranvia"));
        }
        else{
            carro = Mobibus.bind(j.findPath("mobibus"));
        }
        Ruta rout = new Ruta(ubicaiconOri, ubicacionDest,
            tipo, tiempoTrayecto, terminado, tipoAccidente, bus, tranvia)
        return mobibus;
    }
}