package controllers;

import com.avaje.ebean.Model;
import com.avaje.ebean.LikeType;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import actions.CorsComposition;
import actions.ForceHttps;

import java.util.List;
import java.util.ArrayList;

@CorsComposition.Cors
public class ReporteController extends Controller{


    public Result generarReporte(){
        Reporte nuevoReporte = Reporte.generarReporte();
        nuevoReporte.save();
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(nuevoReporte));
    }

    public Result darReporte(Long id){
        Reporte reporte = (Reporte) new Model.Finder(Long.class, Reporte.class).byId(id);
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(reporte));
    }

    public Result darReportes(){
        List<Reporte> reportes = new Model.Finder(Long.class, Reporte.class).findList();
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(reportes));
    }

    public Result eliminarReportes(){
        List<Reporte> reportes = new Model.Finder(Long.class, Reporte.class).findList();
        for(int i = 0;i < reportes.size();i++)
            reportes.get(i).delete();
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }

    public Result eliminarReporte(Long id){
        Reporte reporte =(Reporte) new Model.Finder(Long.class, Reporte.class).byId(id);
        reporte.delete();
        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(Json.toJson(""));
    }

}
