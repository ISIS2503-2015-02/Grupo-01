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

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Conductor(){super();}
    public Conductor(String identificacion, int edad, String nombre, String tipoId, String telefono,
                     String nLicencia, String nFechaVen, String nEstado){
        super(identificacion, edad, nombre, tipoId, telefono);
        licenciaDeConduccion = nLicencia;
        fechaVencimientoLicencia = nFechaVen;
        estado = nEstado;
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


    public static Conductor bind(JsonNode j) {
        String identificacion = j.findPath("identificacion").asText();
        int edad = j.findPath("edad").asDouble();
        String nombre = j.findPath("nombre").asText();
        String tipoId = j.findPath("tipoId").asText();
        String telefono = j.findPath("telefono").asText();
        String licenciaConducccion = j.findPath("licenciaConducccion").asText();
        String fechaVenLicencia = j.findPath("fechaVenLicencia").asText();
        String estado = j.findPath("estado").asText();
        Conductor conductor = new Conductor(identificacion, edad, nombre, tipoId, telefono, licenciaConducccion, fechaVenLicencia, estado);
        return conductor;
    }
}