package models;


//@Entity
public class Usuario  extends Persona{

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private String condicion;

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

}