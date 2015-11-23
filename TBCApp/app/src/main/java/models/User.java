package models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 11/22/2015.
 */
public class User implements Serializable{

    //----------------------------------------------------------------------
    // Atributos
    //----------------------------------------------------------------------

    private String numeroIdentificacion;

    private int edad;

    private String nombre;

    private String telefono;

    private String authToken;

    private String rol;

    private String condicion;

    private String usuario;

    private String password;

    private List<ReservaMobibus> reservas;

    //-----------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------

    public User(String numeroIdentificacion, int edad, String nombre, String telefono, String authToken,
                String rol, String condicion, String usuario, String password, List<ReservaMobibus> reservas){
        this.numeroIdentificacion = numeroIdentificacion;
        this.edad = edad;
        this.nombre = nombre;
        this. telefono = telefono;
        this. authToken = authToken;
        this.rol = rol;
        this.condicion = condicion;
        this.usuario = usuario;
        this.password = password;
        this.reservas = reservas;
    }

    //------------------------------------------------------------------------
    // Getters
    //------------------------------------------------------------------------


    public String getNombre() {
        return nombre;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public int getEdad() {
        return edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getRol() {
        return rol;
    }

    public String getCondicion() {
        return condicion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public List<ReservaMobibus> getReservas() {
        return reservas;
    }

    //------------------------------------------------------------------------
    // Otros
    //------------------------------------------------------------------------
    public void addReserva(ReservaMobibus nReserva){
        reservas.add(nReserva);
    }
}
