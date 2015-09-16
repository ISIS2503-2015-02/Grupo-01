package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Estacion;
import model.Vcub;

public class ControlPrincipal {
	
	private Estacion actual;
	
	private String path;
	
	public ControlPrincipal(String path){
		this.path = path;
		URL url;
		try {
			url = new URL("http://"+path+":9000/estaciones/1");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("GET");

			if(con.getResponseCode()!=200){
				throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
			}
			BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			StringBuffer sb = new StringBuffer();
			System.out.println("Output from server .... \n");
			while((output = buff.readLine())!=null){
				System.out.println(output);
				sb.append(output);
			}
			String json = sb.toString();
			JsonParser parser = new JsonParser();
			JsonObject job = parser.parse(json).getAsJsonObject();
			
			//{"capacidad":78,"id":1,"ubicacion":"Bulevar niza","vcubs":[],"llena":false,"ocupacion":0.0}
			JsonElement capacida = job.get("capacidad");
			JsonElement id = job.get("id");
			JsonElement ubiacion = job.get("ubicacion");
			JsonElement vcubs = job.get("vcubs");
			JsonArray arrayVcubs = vcubs.getAsJsonArray();
			actual = new Estacion();
			actual.setCantidad(arrayVcubs.size());
			actual.setCapacidad(capacida.getAsInt());
			actual.setId(id.getAsLong());
			actual.setUbicacion(ubiacion.getAsString());
			
			ArrayList<Vcub> listVcub = new ArrayList<Vcub>();
			for (int i = 0; i < arrayVcubs.size(); i++) {
				JsonObject vcub = arrayVcubs.get(i).getAsJsonObject();
				Vcub actual = new Vcub();
				//{"id":1,"estado":"Disponible","posiciones":[],"usuario":null}
				actual.setId(vcub.get("id").getAsLong());
				actual.setEstado(vcub.get("estado").getAsString());
				listVcub.add(actual);
			}
			
			actual.setVcubs(listVcub);
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void devolverVCub(String usuario, String vcub) {
		URL url;
		try {
			url = new URL("http://"+path+":9000/vcubs/restituir");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("PUT");
			
			//{  "vcubId" : 1, "estacionId" : 1,  "usuarioId" : "102837546"}
			JsonObject jsonObj = new JsonObject();
			
			jsonObj.addProperty("vcubId", vcub);
			jsonObj.addProperty("estacionId", 1);
			jsonObj.addProperty("usuarioId", usuario);
			
			OutputStream os = con.getOutputStream();
			os.write(jsonObj.toString().getBytes());
			os.flush();

			if(con.getResponseCode()!=200){

			}
			BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			StringBuffer sb = new StringBuffer();
			System.out.println("Output from server .... \n");
			while((output = buff.readLine())!=null){
				System.out.println(output);
				sb.append(output);
			}
			String json = sb.toString();
			JsonParser parser = new JsonParser();
			JsonObject job = parser.parse(json).getAsJsonObject();
			
			//{"id":1,"estado":"Prestada","posiciones":[],
			//"usuario":
			//			{"numeroIdentificacion":"102837546","edad":25,"nombre":"Paul","tipoId":"C.C","telefono":"4535678","condicion":"Paralitico","reservas":[]}}
					
			
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llevar(String usuario, String vcub) {
		URL url;
		try {
			url = new URL("http://"+path+":9000/vcubs/adquirir");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("PUT");
			
			//{  "vcubId" : 1, "estacionId" : 1,  "usuarioId" : "102837546"}
			JsonObject jsonObj = new JsonObject();
			
			jsonObj.addProperty("vcubId", vcub);
			jsonObj.addProperty("usuarioId", usuario);
			
			OutputStream os = con.getOutputStream();
			os.write(jsonObj.toString().getBytes());
			os.flush();

			if(con.getResponseCode()!=200){
				throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
			}
			BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			StringBuffer sb = new StringBuffer();
			System.out.println("Output from server .... \n");
			while((output = buff.readLine())!=null){
				System.out.println(output);
				sb.append(output);
			}
			String json = sb.toString();
			JsonParser parser = new JsonParser();
			JsonObject job = parser.parse(json).getAsJsonObject();
			
			
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
