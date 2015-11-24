package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*; 

/**
* Clase que representa una ruta que sigue un vehiculo de TBC
*/
@Entity
public class Ruta extends Model {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
    * Id unico de la ruta
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    /**
    * La ubicacion en donde inicia la ruta
    */
    private String ubicaiconOrigen;

    /**
    * La ubicacion en donde acaba la ruta
    */
    private String ubicacionDestino;

    /**
    * El tipo de la ruta
    */
    private String tipo;

    /**
    * El tiempo transcurrido desde el inicio del recorrido de la ruta
    */
    private double tiempoTrayecto;

    /**
    * Indica si la ruta ya fue terminada o no
    */
    private String terminado;

    /**
    * Indica el tipo de accidente que se presento en la ruta
    */
    private String tipoAccidente;

    /**
    * Movibus asociado a la ruta
    */
    @OneToOne
    @JoinColumn(name="mobibus_id")
    private Mobibus bus;

    /**
    * Tranvia asociado a la ruta
    */
    @OneToOne
    @JoinColumn(name="tranvia_id")
    private Tranvia tranvia;

    /**
    * Conductor asignado a la ruta
    */
    @ManyToOne
    @JoinColumn(name="conductor_numero_identificacion")
    private Conductor conductor;

    /**
    * Reserva asociada a la ruta
    */
    @OneToOne
    @JoinColumn(name="reserva_id")
    private Reserva reserva;

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

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

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

    @JsonIgnore
    public Reserva getReserva(){
        return reserva;
    }

    public void setReserva(Reserva reserva){
        this.reserva = reserva;
    }

    
    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    /**
    * Permite la creacion de una ruta a partir de un nodo Json
    */
    public static Ruta bind(JsonNode j) {
        String ubicacionOri = j.findPath("ubicacionOrigen").asText();
        String ubicacionDes = j.findPath("ubicacionDestino").asText();
        String tipoo = j.findPath("tipo").asText();
        double tiempoTrayecto = j.findPath("tiempoTrayecto").asDouble();
        return new Ruta(ubicacionOri, ubicacionDes, tipoo, tiempoTrayecto, Cons.ET_CURSO, Cons.EA_NORMAL ,null , null , null);
    }
}