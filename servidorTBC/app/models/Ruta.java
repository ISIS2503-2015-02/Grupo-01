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

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String ubicaiconOrigen;

    private String ubicacionDestino;

    private String tipo;

    private double tiempoTrayecto;

    private String terminado;

    private String tipoAccidente;

    @OneToOne
    @JoinColumn(name="mobibus_id")
    private Mobibus bus;

    @OneToOne
    @JoinColumn(name="tranvia_id")
    private Tranvia tranvia;

    @ManyToOne
    @JoinColumn(name="conductor_numero_identificacion")
    private Conductor conductor;

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------

    public Ruta(String ubicaiconOrigen, String ubicacionDestino,
            String tipo, double tiempoTrayecto, String terminado,
            String tipoAccidente, Mobibus bus, Tranvia tranvia, 
            Conductor conductor) {
        super();
        this.ubicaiconOrigen = ubicaiconOrigen;
        this.ubicacionDestino = ubicacionDestino;
        this.tipo = tipo;
        this.tiempoTrayecto = tiempoTrayecto;
        this.terminado = terminado;
        this.tipoAccidente = tipoAccidente;
        this.bus = bus;
        this.tranvia = tranvia;
        this.conductor = conductor;
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

    public Tranvia getTranvia() {
        return tranvia;
    }

    public void setTranvia(Tranvia tren) {
        this.tranvia = tren;
    }

    public Conductor getConductor(){
        return conductor;
    }

    public void setConductor(Conductor conductor){
        this.conductor = conductor;
    }
    
    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    public static Ruta bind(JsonNode j) {
        String ubicacionOri = j.findPath("ubicacionOrigen").asText();
        String ubicacionDes = j.findPath("ubicacionDestino").asText();
        double tiempoTrayecto = j.findPath("tiempoTrayecto").asDouble();
        String tipoo = j.findPath("tipo").asText();
        String terminado = j.findPath("terminado").asText();
        String tipoAccidente = j.findPath("accidente").asText();
        Ruta rout = new Ruta(ubicacionOri, ubicacionDes, tipoo, tiempoTrayecto, terminado, tipoAccidente,null, null, null);
        return rout;
    }
}