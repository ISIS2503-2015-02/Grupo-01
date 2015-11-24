package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import play.Logger;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
* Clase que representa un reserva de movibus de un usuario
*/
@Entity
public class Reserva extends Model {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
    * Id unico de la reserva en el sistema
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**
    * Estado actual de la reserva
    */
    private String estado;

    /**
    * Fecha para la que se solicito el servicio de movibus
    */
    private Date fecha;

    /**
    * Costo del servicio de transporte
    */
    private double costo;

    /**
    * Turno de atencion para la reserva
    */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int turno;

    /**
    * Ruta que se quiere recorrer en movibus
    */
    @OneToOne
    @JoinColumn(name="ruta_id")
    private Ruta ruta;

    /**
    * Usuario que hizo la reserva
    */
    @ManyToOne
    @JoinColumn(name="usuario_numero_identificacion")
    @JsonBackReference
    private Usuario usuario;

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------

    public Reserva(){
        
    }

    public Reserva (String nEstado, Date nFecha, double nCosto, Ruta nRuta){
        estado = nEstado;
        fecha = nFecha;
        ruta = nRuta;
        costo = nCosto;
    }

    //-----------------------------------------------------------
    // Getters & Setters
    //-----------------------------------------------------------

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getEstado(){
        return estado;
    }

    public Date getFecha(){
        return fecha;
    }

    public double getCosto(){
        return costo;
    }

    public int getTurno(){
        return turno;
    }

    public Ruta getRuta(){
        return ruta;
    }

    public void setEstado(String nEstado){
        estado = nEstado;
    }

    public void setFecha(Date nFecha){
        fecha = nFecha;
    }

    public void setCosto(double nCosto){
        costo = nCosto;
    }

    public void setTurno(int nTurno){
        turno = nTurno;
    }

    public void setRuta(Ruta ruta){
        this.ruta = ruta;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    /**
    * Permite la creacion de una nueva reserva a partir de un nodo Json
    */
    public static Reserva bind(JsonNode j){
        String fechaa = j.findPath("fecha").asText();
        Date fechaDate = stringToDate(fechaa);
        return new Reserva(Cons.R_ESPERA, fechaDate, 15000, null);
    }

    /**
    * Permite convertir una fecha de tipo String a tipo Date
    */
    public static Date stringToDate(String dateStr){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            return formatter.parse(dateStr);
        } catch(Exception e) {
            Logger.info(e.getMessage());
            return null;
        }
    }
}