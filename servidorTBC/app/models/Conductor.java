package models;

import com.fasterxml.jackson.databind.JsonNode;
import models.Persona;
import play.mvc.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*; 


@Entity
/**
* Clase que representa el modelo de un conductor en el contexto de TBC transporte
*/
public class Conductor extends Persona{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    
    /**
    * Representa el número de licencia de conduncción del conductor
    */
    private String licenciaDeConduccion;
    
    /**
    * Representa la fecha de vencimiento de la licencia de condución
    */
    private String fechaVencimientoLicencia;
    
    /**
    * Representa el estado actual del condutor, puede ser libre o asignado
    */
    private String estado;

    /**
    * Representa las rutas que tiene asigndas el condutor
    */
    @OneToMany(cascade=CascadeType.ALL)
    private List<Ruta> rutas;

    //--------------------------------------------
    //Constructores
    //--------------------------------------------

    /**
    * Metodo constructor de un conductor sin valores asignados
    */
    public Conductor(){
        super();
    }

    /**
    * Metodo constructor  de un conductor con todos sus valores
    * @param identificacion del conductor
    * @param edad del conductor
    * @paran nombre del conductor
    * @param tipoId el tipo de identificaciion del conductor
    * @param telefono el telefono de contacto del conductor
    * @param nLicencia numero de licencia de conduccion
    * @param nFechaVen fecha de vencimineto de la licencia
    * @param estado estado actual del conductor
    */
    public Conductor(Long identificacion, int edad, String nombre, String tipoId, String telefono, String nLicencia, String nFechaVen, String nEstado, List<Ruta> rutas){
        super(identificacion, edad, nombre, tipoId, telefono);
        licenciaDeConduccion = nLicencia;
        fechaVencimientoLicencia = nFechaVen;
        estado = nEstado;
        this.rutas = rutas;
    }

    //--------------------------------------------
    //Getters & Setters
    //--------------------------------------------

    /**
    * getter de la licencia de conduccion
    * @return String la licencia del conductor
    */
    public String getLicenciaDeConduccion() {
        return licenciaDeConduccion;
    }

    /**
    * setter de la licencia de conduccion
    * actualiza el valor del numero de la licencia
    * @param licenciaDeConduccion String
    */
    public void setLicenciaDeConduccion(String licenciaDeConduccion) {
        this.licenciaDeConduccion = licenciaDeConduccion;
    }

    /**
    * getter de la licencia de conduccion
    * @return String la licencia del conductor
    */
    public String getFechaVencimientoLicencia() {
        return fechaVencimientoLicencia;
    }

    /**
    * setter de la licencia de conduccion
    * actualiza el valor del numero de la licencia
    * @param licenciaDeConduccion String
    */
    public void setFechaVencimientoLicencia(String fechaVencimientoLicencia) {
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    /**
    * getter del estado
    * @return String el estado del conductor
    */
    public String getEstado() {
        return estado;
    }

    /**
    * setter del etado
    * actualiza el valor del estado
    * @param estado String
    */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public List<Ruta> getRutas(){
        return rutas;
    }

    /**
    * setter de las rutas
    * actualiza el valor del numero de las rutas
    * @param rutas las nuevas utas del conductor
    */
    public void setRutas(List<Ruta> rutas){
        this.rutas = rutas;
    }

    /**
    * Crea un nuevo objeto de tipo conductor a partir de un nodo Json
    * @param j JsonNode, un nodo en json que representa al conductor
    * @return Conductor retorna un conductor
    */
    public static Conductor bind(JsonNode j) {
        Long id = new Long(j.findPath("identificacion").asInt());
        int edad = j.findPath("edad").asInt();
        String nombre = j.findPath("nombre").asText();
        String tipoId = j.findPath("tipoId").asText();
        String telefono = j.findPath("telefono").asText();
        String licenciaConducccion = j.findPath("licenciaConduccion").asText();
        String fechaVenLicencia = j.findPath("fechaVenLicencia").asText();
        return new Conductor(id,edad, nombre, tipoId, telefono, licenciaConducccion,
         fechaVenLicencia, Cons.V_DISPONIBLE, new ArrayList<Ruta>());
    }

    /**
    * Da el tiempo promedio de todas las rutas del conductor
    */
    public double darTiempoPromedioRutas(){
        double acum = 0;
        for(int i = 0; i< rutas.size(); i++)
            acum+=rutas.get(i).getTiempoTrayecto();
        if(rutas.isEmpty())
            return acum/rutas.size();
        return -1;
    }
}