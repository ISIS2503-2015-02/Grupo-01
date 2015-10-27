package utils;

import models.*;
import play.Logger;
import java.util.Date;

import java.util.ArrayList;

public class DummyData {

    public static Usuario user1;
    public static Usuario user2;
    public static Usuario user3;
    public static Usuario user4;

    public static void loadDemoData() {
        
        Logger.info("Loading Demo Data");

        user1 = new Usuario("admin", "admin",new Long(123), 1, "Adm", "cc", "123",
                   "si", null, Cons.ROL_ADMIN);
        user1.save();

        user2 = new Usuario("us", "us",new Long(344), 1, "Us", "cc", "456",
                   "no", null, Cons.ROL_USUARIO);
        user2.save();
        
        for (int i = 0;i < 100 ; i++) {
            Tranvia tranv = new Tranvia(Cons.V_DISPONIBLE, 0.0, 17.5,
            false, new ArrayList<Revision>());

            double[] posm = Utilidad.coordenadasNuevas();
            Posicion pos1 = new Posicion(posm[0], posm[1], new Date());
            tranv.save();
            tranv.createToken();
            tranv.agregarPosicion(pos1);
            tranv.update();

            double[] post = Utilidad.coordenadasNuevas();
            Posicion pos2 = new Posicion(post[0], post[1], new Date());

            Mobibus mobi = new Mobibus(Cons.V_DISPONIBLE, 10, "VEN024", new ArrayList<Revision>());
            mobi.save();
            mobi.createToken();
            mobi.agregarPosicion(pos2);
            mobi.update();
        }

        for (int i = 0; i < 10;i++ ) {
            double[] pos = Utilidad.coordenadasNuevas();
            Estacion est = new Estacion(40, "Norte", pos[0], pos[1]);
            est.createToken();

            for(int j = 0; j < 30; j++){
                Vcub vc = new Vcub(Cons.V_DISPONIBLE);

                double[] coords = Utilidad.coordenadasNuevas();
                Posicion pos1 = new Posicion(coords[0], coords[1], new Date());
                vc.agregarPosicion(pos1);
                vc.createToken();

                est.agregarVcub(vc);
            }
            
        }
    
    }

}