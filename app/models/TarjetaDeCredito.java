package models;

//@Entity
public class TarjetaDeCredito {

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private String nombreTarjeta;
    private String numeroTarjeta;
    private String cvv;
    private String fechaVencimiento;

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public TarjetaDeCredito(){}
    public TarjetaDeCredito( String nNombre, String nNumero, String cvv, String nFecha){
        nombreTarjeta = nNombre;
        numeroTarjeta = nNumero;
        this.cvv = cvv;
        fechaVencimiento = nFecha;
    }
    //--------------------------------------------
    //Getters & Setters
    //--------------------------------------------


    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}