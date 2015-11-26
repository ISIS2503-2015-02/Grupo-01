package restcall;


import android.content.SyncStatusObserver;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.ReservaMobibus;
import models.User;

/**
 * Created by user on 11/22/2015.
 */
public class Client {

    public final static String URL = "https://tbc-transporte-tbc.herokuapp.com";

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
            String authToken = j.getString("token");
            String rol = j.getString("rol");
            String condicion = j.getString("condicion");
            String usuario = j.getString("usuario");
            String password = j.getString("password");
            ;

            JSONArray reservas = j.getJSONArray("reservas");

            User newUs = new User(numeroIdentificacion, edad, nombre, telefono, authToken, rol, condicion
                    , usuario, password);


            buildReservas(reservas, newUs);

            return newUs;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void buildReservas(JSONArray j, User us){

        try{
            for(int i = 0; i< j.length();i++){
                JSONObject act = j.getJSONObject(i);
                ReservaMobibus reser = buildReservaMobibus(act);
                us.getReservas().add(reser);
                System.out.println(us.getReservas().get(0).getId());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public ReservaMobibus buildReservaMobibus(JSONObject j){
        try{
            String id = j.getString("id");
            String estado = j.getString("estado");
            JSONObject jRut = j.getJSONObject("ruta");
            String ubicacionOrigen = jRut.getString("ubicaiconOrigen");
            String ubicacionDestino = jRut.getString("ubicacionDestino");



            ReservaMobibus newRes = new ReservaMobibus(id,new Date(),ubicacionDestino,ubicacionOrigen, estado);
            return newRes;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public User login(String usuario, String passw){
        JSONObject jsonObj;
        try {

            URL url = new URL(URL + "/login");
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


            System.out.println(jsonObj.getString("token"));

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

    public ReservaMobibus createReserva(String ubOrigen, String ubDestino, User usuario){
        JSONObject jsonObj;
        try {

            URL url = new URL(URL + "/usuarios/reserva");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-AUTH-TOKEN", usuario.getAuthToken());

            String input = "{\"usuarioId\":\""+usuario.getNumeroIdentificacion()+"\",\"ruta\":{\"ubicacionOrigen\":\""+ubOrigen+"\", \"ubicacionDestino\":\""+ubDestino+"\"" +
                    ", \"tipo\":\"Ruta Mobibus\"}}";

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
            System.out.println(jsonObj);
            conn.disconnect();

            return buildReservaMobibus(jsonObj);

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
