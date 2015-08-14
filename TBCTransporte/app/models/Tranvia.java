
package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tranvia extends Model {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    private String id;

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------

    public Tranvia(String nombre) {
        this.id = id;
    }

    //-----------------------------------------------------------
    // Getters & Setters
    //-----------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //-----------------------------------------------------------
    // Métodos auxiliares
    //-----------------------------------------------------------

    public static Tranvia bind(JsonNode j) {
        String id = j.findPath("id").asText();
        Tranvia tranvia = new Tranvia(id);
        return tranvia;
    }
}
