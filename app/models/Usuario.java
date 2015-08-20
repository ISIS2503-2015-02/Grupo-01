package models;


import java.util.ArrayList;
import java.util.List;

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
    public Usuario(String identificacion, int edad, String nombre, String tipoId, String telefono,
                   String condicion, List<Reserva> nReservas){
        super(identificacion, edad, nombre, tipoId, telefono);
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

    public static Usuario bind(JsonNode j) {
        String identificacion = j.findPath("identificacion").asText();
        int edad = j.findPath("edad").asDouble();
        String nombre = j.findPath("nombre").asText();
        String tipoId = j.findPath("tipoId").asText();
        String telefono = j.findPath("telefono").asText();
        String condicion = j.findPath("condicion").asText();
        Usuario usuario = new Usuario(identificacion, edad, nombre, tipoId, telefono, condicion, new ArrayList<Reserva>());
        return usuario;
    }

}