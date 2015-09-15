package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ControlPrincipal {

	private String path;
	
	public ControlPrincipal(String path){
		this.path = path;
	}

	public void reportarAccidente(String ruta, String mobibus, String accidente, String latitud, String longitud) {

		URL url;
		try {
			url = new URL("http://"+path+":9000/rutas/"+ruta+"/accidente/tranvia/"+accidente+"/"+longitud+"/"+latitud+"/");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			if(con.getResponseCode()!=200){
				throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
			}
			BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			System.out.println("Output from server .... \n");
			while((output = buff.readLine())!=null){
				System.out.println(output);
			}
			con.disconnect();		

		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void actualizarTemperatura(String ruta, String tranvia, String temperatura) {
		URL url;
		try {
			url = new URL("http://"+path+":9000/mobibuses/posicion");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			
			JsonObject jsonObj = new JsonObject();
			
			OutputStream os = con.getOutputStream();
			os.write(jsonObj.toString().getBytes());
			os.flush();

			if(con.getResponseCode()!=200){
				throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
			}
			BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			System.out.println("Output from server .... \n");
			while((output = buff.readLine())!=null){
				System.out.println(output);
			}
			con.disconnect();		

		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void actualizarUbicacion(String ruta, String mobibus, String latitud, String longitud) {
		URL url;
		try {
			url = new URL("http://"+path+":9000/mobibuses/posicion");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			
			JsonObject jsonObj = new JsonObject();
			
			jsonObj.addProperty("mobibusId", mobibus);
			jsonObj.addProperty("fecha", new Date().toString());
			jsonObj.addProperty("latitud", latitud);
			jsonObj.addProperty("longitud", longitud);
			
			OutputStream os = con.getOutputStream();
			os.write(jsonObj.toString().getBytes());
			os.flush();

			if(con.getResponseCode()!=200){
				throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
			}
			BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			System.out.println("Output from server .... \n");
			while((output = buff.readLine())!=null){
				System.out.println(output);
			}
			con.disconnect();		

		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
