package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import play.Logger;

import java.text.SimpleDateFormat;

import java.util.Date;

@Entity
public class Reserva extends Model {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String estado;

    private Date fecha;

    private double costo;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int turno;

    @OneToOne
    @JoinColumn(name="ruta_id")
    private Ruta ruta;

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

    public static Reserva bind(JsonNode j){
        String fechaa = j.findPath("fecha").asText();
        Date fechaDate = stringToDate(fechaa);
        return new Reserva(Cons.R_ESPERA, fechaDate, 15000, null);
    }

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