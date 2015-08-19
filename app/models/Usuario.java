package models;


//@Entity
public class Usuario  extends Persona{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private String condicion;
    private List<Reserva> reservas;


    //--------------------------------------------
    //Constructores
    //--------------------------------------------
    public Usuario(){ super();}
    public Usuario(String condicion, List<Reserva> nReservas){
        super();
        this.condicion = condicion;
        reservas = nReservas;
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
    
    public void setReservas(List<Reserva> reservas){
        this.reservas = reservas;
    }

    public List<Reserva> getReservas(){
        return reservas;
    }

    public void addReserva(Reserva nReserva){
        reservas.add(nReserva);
    }

}