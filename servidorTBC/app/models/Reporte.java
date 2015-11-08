package models;

import java.util.Date;
import com.fasterxml.jackson.annotation.*;
import com.avaje.ebean.Model;
import javax.persistence.*;
import java.util.List;

import java.text.SimpleDateFormat;



@Entity
public class Reporte extends Model {


    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private String tipoAccidenteMasComun;
    private Long conductorMasEfectivo;
    private Long conductorMenosEfectivo;
    private double tiempoPromedioRuta;
    private double tiempoPromedioTranvias;
    private double tiempoPromedioMobibuses;
    private double tasaReservasAsignadas;




    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------
    public Reporte (){
        super();
    }
    public Reporte( String fecha, String tipoAccidenteMasComun, Long masEfectivo,
                   Long menosEfectivo, double tiempoPromedioRutas, double tiempoPromedioTranvias, double tiempoPromedioMobibuses,
                    double tasaReservas){
        super();
        this.fecha = fecha;
        this.tipoAccidenteMasComun = tipoAccidenteMasComun;
        this.conductorMasEfectivo = masEfectivo;
        this.conductorMenosEfectivo = menosEfectivo;
        this.tiempoPromedioRuta = tiempoPromedioRutas;
        this.tiempoPromedioTranvias = tiempoPromedioTranvias;
        this.tiempoPromedioMobibuses = tiempoPromedioMobibuses;
        this.tasaReservasAsignadas = tasaReservas;
    }

    public static Reporte generarReporte(){
        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("DD/MM/YYYY");
        String fechaStr = format.format(fecha);
        String accidenteComun = darTipoAccidenteMasComun();
        List<Conductor> conductores = new Model.Finder(Long.class, Conductor.class).findList();
        Long conductorMasEfectivo = masEfectivo(conductores);
        Long conductorMenosEfectivo = menosEfectivo(conductores);
        double tiempoPromedioRutas = darTiempoPromedioRutas();
        double tiempoPromedioTranvias = darTiempoPromedioTranvias();
        double tiempoPromedioMobibus =  darTiempoPromedioMobibuses();
        double tasaReservas = darTasaReservasMobibuses();

        return new Reporte( fechaStr, accidenteComun, conductorMasEfectivo,
                conductorMenosEfectivo , tiempoPromedioRutas, tiempoPromedioTranvias, tiempoPromedioMobibus, tasaReservas);
    }

    //-----------------------------------------------------------
    // Metodos
    //-----------------------------------------------------------
    //Retorna el tipo de accidente mas comun, si no hay ninguno, retorna "Ninguno"
    public static String darTipoAccidenteMasComun(){
        int n1 = new Model.Finder(Long.class, Ruta.class).where().eq("tipoAccidente", Cons.EA_CHOQUE).findRowCount();
        int n2 = new Model.Finder(Long.class, Ruta.class).where().eq("tipoAccidente", Cons.EA_INCENDIO).findRowCount();
        int n3 = new Model.Finder(Long.class, Ruta.class).where().eq("tipoAccidente", Cons.EA_ATROPELLO).findRowCount();
        if(n1>n2 && n1>n3) 
            return Cons.EA_CHOQUE;
        else if(n2>n1 && n2>n3) 
            return Cons.EA_INCENDIO;
        else if (n3>n1 && n3>n2) 
            return Cons.EA_ATROPELLO;
        else 
            return "Ninguno";
    }

    //Retorna el identificador del conductor con menor tiempo promedio de rutas, null si ninguno tiene rutas
    @JsonIgnore
    public static Long masEfectivo(List<Conductor> conductores){
        Conductor masEficiente = null;
        double promedioMasEficiente = Integer.MAX_VALUE;
        for(int i = 0; i<conductores.size();i++){
            Conductor actual = conductores.get(i);
            double promedioActual = actual.darTiempoPromedioRutas();
            if(promedioActual < promedioMasEficiente)
            {
                promedioMasEficiente = promedioActual;
                masEficiente = actual;
            }
        }
        return (masEficiente==null) ?null:masEficiente.getNumeroIdentificacion();
    }

    //Retorna el identificador del conductor con menor tiempo promedio de rutas, null si ninguno tiene rutas
    @JsonIgnore
    public static Long menosEfectivo(List<Conductor> conductores){
        Conductor menosEficiente = null;
        double promedioMenosEficiente = -1;
        for(int i = 0; i<conductores.size();i++){
            Conductor actual = conductores.get(i);
            double promedioActual = actual.darTiempoPromedioRutas();
            if(promedioActual > promedioMenosEficiente)
            {
                promedioMenosEficiente = promedioActual;
                menosEficiente = actual;
            }
        }
        return (menosEficiente==null)?null:menosEficiente.getNumeroIdentificacion();
    }

    //Retorna el tiempo promedio de ruta de los tranvias. -1 si ningun tranvia tiene rutas
    @JsonIgnore
    public static double darTiempoPromedioTranvias(){
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).where().eq("bus", null ).findList();
        if(rutas.isEmpty())
            return -1;
        double tAcum = 0;
        for(int i = 0; i<rutas.size(); i++)
            tAcum+= rutas.get(i).getTiempoTrayecto();
        return tAcum/rutas.size();
    }

    //Retorna el tiempo promedio de ruta de los mobibudes. -1 si ningun mobibus tiene rutas
    @JsonIgnore
    public static double darTiempoPromedioMobibuses(){
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).where().eq("tranvia", null).findList();
        if(rutas.isEmpty())
            return -1;
        double tAcum = 0;
        for(int i = 0; i<rutas.size(); i++)
            tAcum+= rutas.get(i).getTiempoTrayecto();
        return tAcum/rutas.size();
    }

    //Retorna el tiempo promedio de ruta de cualquier vehiculo. -1 si no hay rutas
    @JsonIgnore
    public static double darTiempoPromedioRutas(){
        List<Ruta> rutas = new Model.Finder(Long.class, Ruta.class).findList();
        if(rutas.isEmpty())
            return -1;
        double tAcum = 0;
        for(int i = 0; i<rutas.size(); i++)
            tAcum+= rutas.get(i).getTiempoTrayecto();
        return tAcum/rutas.size();
    }

    //retorna el porcentaje de las reservas de mobibuses que efectivamente han sido asignadas.
    @JsonIgnore
    public static double darTasaReservasMobibuses(){
        int total = new Model.Finder(Long.class, Reserva.class).findRowCount();
        int asignadas = new Model.Finder(Long.class, Reserva.class).where().eq("estado", Cons.R_ASIGNADA).findRowCount();
        if(total==0){
            return 1;
        }
        return (double)asignadas/total;
    }

    //-----------------------------------------------------------
    // Getters && Setters
    //-----------------------------------------------------------


    public Long getId() {  
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoAccidenteMasComun() {
        return tipoAccidenteMasComun;
    }
    
    public void setTipoAccidenteMasComun(String tipoAccidenteMasComun) {
        this.tipoAccidenteMasComun = tipoAccidenteMasComun;
    }

    public Long getConductorMasEfectivo() {  
        return conductorMasEfectivo;
    }
    
    public void setConductorMasEfectivo(Long conductorMasEfectivo) {
        this.conductorMasEfectivo = conductorMasEfectivo;
    }

    public Long getConductorMenosEfectivo() {
        return conductorMenosEfectivo;
    }
    
    public void setConductorMenosEfectivo(Long conductorMenosEfectivo) {
        this.conductorMenosEfectivo = conductorMenosEfectivo;
    }

    public double getTiempoPromedioRuta() {   
        return tiempoPromedioRuta;
    }

    public void setTiempoPromedioRuta(double tiempoPromedioRuta) {
        this.tiempoPromedioRuta = tiempoPromedioRuta;
    }

    public double getTiempoPromedioTranvias() {
        return tiempoPromedioTranvias;
    }
    
    public void setTiempoPromedioTranvias(double tiempoPromedioTranvias) {
        this.tiempoPromedioTranvias = tiempoPromedioTranvias;
    }

    public double getTiempoPromedioMobibuses() {
        return tiempoPromedioMobibuses;
    }
    
    public void setTiempoPromedioMobibuses(double tiempoPromedioMobibuses) {
        this.tiempoPromedioMobibuses = tiempoPromedioMobibuses;
    }

    public double getTasaReservasAsignadas(){ 
        return tasaReservasAsignadas;
    }
    
    public void setTasaReservasAsignadas(double tasaReservasAsignadas){
        this.tasaReservasAsignadas = tasaReservasAsignadas;
    }
}