package models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 11/22/2015.
 */
public class ReservaMobibus implements Serializable {

    //----------------------------------------------------------------------
    // Atributos
    //----------------------------------------------------------------------

    private String id;

    private String estado;

    private String ubicacionOrigen;

    private String ubicacionDestino;

    private Date fecha;

    //----------------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------------


    public ReservaMobibus(String id, Date fecha, String ubicacionDestino, String ubicacionOrigen, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.ubicacionDestino = ubicacionDestino;
        this.ubicacionOrigen = ubicacionOrigen;
        this.estado = estado;
    }

    //----------------------------------------------------------------------
    // Getters
    //----------------------------------------------------------------------


    public String getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getUbicacionOrigen() {
        return ubicacionOrigen;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public Date getFecha() {
        return fecha;
    }
}
