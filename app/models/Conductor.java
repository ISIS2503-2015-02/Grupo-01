package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.Persona;
import play.libs.Json;
import play.mvc.*;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.persistence.*;

@Entity
public class Conductor extends Persona{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private String licenciaDeConduccion;
    private String fechaVencimientoLicencia;
    private String estado;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Conductor> conductores;

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Conductor(){super();}
    public Conductor(String identificacion, int edad, String nombre, String tipoId, String telefono,
                     String nLicencia, String nFechaVen, String nEstado, List<Conductor> conductores){
        super(identificacion, edad, nombre, tipoId, telefono);
        licenciaDeConduccion = nLicencia;
        fechaVencimientoLicencia = nFechaVen;
        estado = nEstado;
        this.conductores = conductores;
    }

    //--------------------------------------------
    //Getters & Setters
    //--------------------------------------------


    public String getLicenciaDeConduccion() {
        return licenciaDeConduccion;
    }

    public void setLicenciaDeConduccion(String licenciaDeConduccion) {
        this.licenciaDeConduccion = licenciaDeConduccion;
    }

    public String getFechaVencimientoLicencia() {
        return fechaVencimientoLicencia;
    }

    public void setFechaVencimientoLicencia(String fechaVencimientoLicencia) {
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Conductor> getConductores(){
        return conductores;
    }

    public void setConductores(List<Conductor> conductores){
        this.conductores = conductores;
    }


    public static Conductor bind(JsonNode j) {
        String identificacion = j.findPath("identificacion").asText();
        double edad = j.findPath("edad").asDouble();
        double nombre = j.findPath("nombre").asDouble();
        String tipoId = j.findPath("tipoId").asText();
        int telefono = j.findPath("telefono").asInt();
        double licenciaConducccion = j.findPath("licenciaConducccion").asDouble();
        String fechaVenLicencia = j.findPath("fechaVenLicencia").asText();
        int estado = j.findPath("estado").asInt();
        Conductor conductor = new Conductor(identificacion, edad, nombre, tipoId, telefono, licenciaConducccion, fechaVenLicencia, estado);
        return conductor;
    }
}