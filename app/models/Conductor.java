package models;

//@Entity
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
    public Conductor(String nLicencia, String nFechaVen, String nEstado){
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
}