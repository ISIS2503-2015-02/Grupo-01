package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Calendar;

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

    public Reserva (String nEstado, Date nFecha, double nCosto, int nTurno, Ruta nRuta){
        estado = nEstado;
        fecha = nFecha;
        costo = nCosto;
        turno = nTurno;
        ruta = nRuta;
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
        String esta = j.findPath("estado").asText();
        String fechaa = j.findPath("fecha").asText();
        Date fechaDate = stringToDate(fechaa);
        double elCosto = j.findPath("costo").asDouble();
        int turnoEnFila = j.findPath("turno").asInt();
        Reserva reserva = new Reserva(esta, fechaDate, elCosto, turnoEnFila, null);
        return reserva;
    }

    public static Date stringToDate(String dateStr){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date parsedDate = formatter.parse(dateStr);
            return parsedDate;
        }
        catch(Exception e){
            return null;
        }
    }

    public static Date maniana(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Calendar cal =  Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }
}