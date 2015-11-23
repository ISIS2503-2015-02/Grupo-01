package restcall;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import models.ReservaMobibus;
import models.User;

/**
 * Created by user on 11/22/2015.
 */
public class Client {

    public final static String URL = "https://transporte-tbc.herokuapp.com";

    public Client(){
    }

    //---------------------------------------------------------------------------
    // Metodos
    //---------------------------------------------------------------------------



    public User buildUser(JSONObject j){
    try {
        String numeroIdentificacion = j.getLong("numeroIdentificacion") + "";
        int edad = j.getInt("edad");
        String nombre = j.getString("nombre");
        String telefono = j.getString("telefono");
        String authToken = j.getString("authToken");
        String rol = j.getString("rol");
        String condicion = j.getString("condicion");
        String usuario = j.getString("usuario");
        String password = j.getString("password");


        User newUs = new User(numeroIdentificacion, edad, nombre, telefono, authToken, rol, condicion
                , usuario, password, new ArrayList<ReservaMobibus>());

        return newUs;
    }
    catch(Exception e){
        e.printStackTrace();
    }
        return null;
    }

    public ReservaMobibus buildReserva(JSONObject j){
        return null;
    }

    public User login(String usuario, String passw){
        JSONObject jsonObj;
        try {

            URL url = new URL("https://transporte-tbc.herokuapp.com/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"user\":\""+usuario+"\",\"pass\":\""+passw+"\"}";

            System.out.println(input);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            output = br.readLine();
            System.out.println(output);
            jsonObj = new JSONObject(output);


            System.out.println(jsonObj.getString("authToken"));

            conn.disconnect();
            return buildUser(jsonObj);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
