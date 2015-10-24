package utils;

import models.*;
import play.Logger;

import java.util.ArrayList;

public class DummyData {

    public static Usuario user1;
    public static Usuario user2;

    public static void loadDemoData() {
        
        Logger.info("Loading Demo Data");

        user1 = new Usuario("admin", "admin",new Long(123), 1, "Adm", "cc", "123",
                   "si", null, Cons.ROL_ADMIN);
        user1.save();

        user2 = new Usuario("us", "us",new Long(344), 1, "Us", "cc", "456",
                   "no", null, Cons.ROL_USUARIO);
        user2.save();

    }

}