package models;


//@Entity
public class Usuario  extends Persona{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private String condicion;
    private TarjetaDeCredito tarjetaDeCredito;

    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Usuario(){ super();}
    public Usuario(String condicion){
        super();
        this.condicion = condicion;
    }

    //--------------------------------------------
    //Getters & Setters
    //--------------------------------------------
    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public TarjetaDeCredito getTarjetaDeCredito() {
        return tarjetaDeCredito;
    }

    public void registrarTarjetaDeCredito(String nombreTarjeta, String numeroTarjeta,
                                          String cvv, String fechaVencimiento) {
        TarjetaDeCredito nueva = new TarjetaDeCredito(nombreTarjeta, numeroTarjeta, cvv, fechaVencimiento);
        this.tarjetaDeCredito = nueva;
    }
}