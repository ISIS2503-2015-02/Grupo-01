package Mundo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.Timer;

import Interfaz.InterfazTranvia;

import com.newrelic.deps.org.apache.http.HttpResponse;
import com.newrelic.deps.org.apache.http.client.methods.HttpGet;
import com.newrelic.deps.org.apache.http.client.methods.HttpPost;
import com.newrelic.deps.org.apache.http.client.methods.HttpPut;
import com.newrelic.deps.org.apache.http.entity.StringEntity;
import com.newrelic.deps.org.apache.http.impl.client.BasicResponseHandler;
import com.newrelic.deps.org.apache.http.impl.client.CloseableHttpClient;
import com.newrelic.deps.org.apache.http.impl.client.HttpClientBuilder;
import com.newrelic.deps.org.json.simple.JSONArray;
import com.newrelic.deps.org.json.simple.JSONObject;
import com.newrelic.deps.org.json.simple.parser.JSONParser;

public class Mundo extends JFrame implements ActionListener {
	
	private String id;
	private String url;
	private String strDate;
	private double ultimaLatitud;
	private double ultimaLongitud;
	private Timer timer;
	private InterfazTranvia ppal;
	
	public Mundo(InterfazTranvia ventana, String url){ 
		ppal = ventana;
		strDate = DateFormat.getInstance().getTimeInstance().format(new Date());
		this.url = url;
		timer = new Timer(2500, this);
	}
	
	public JSONObject crearTranvia(double posX, double posY, String estado,
			double presion, double temperatura) throws IOException{	
		JSONObject json = tVToJson(posX, posY, estado, presion, temperatura);
		String urlCompleto = url+"/tranvias";
		return post(json, urlCompleto);
	}
	
	public JSONObject cambiarPosicion(String id, double latitud, double longitud ) throws IOException{
		JSONObject json = positionToJson(id, latitud, longitud);
		String urlCompleto = url+"/tranvias/posicion";
		return post(json, urlCompleto);
	}
	
	public void empezarActualizaciones (String id){
		this.id = id;
		timer.setRepeats(true);
		timer.start();
	}
	
	public void detenerActualziaciones(){
		timer.stop();
	}

	public JSONObject darTranvia(String id)throws Exception{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url+"/tranvias/"+id);
		HttpResponse response = httpClient.execute(request);
		String responseString = new BasicResponseHandler().handleResponse(response);
		httpClient.close();
		JSONObject json = (JSONObject)new JSONParser().parse(responseString); 
		return json;
	}

	public JSONObject post(JSONObject json, String url) throws IOException{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    String responseString = new BasicResponseHandler().handleResponse(response);
		    json = (JSONObject)new JSONParser().parse(responseString); 
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		    httpClient.close();
		}
		return json;
	}
	
	public double[][] ultimaPosicionTranvia(String id){
		double[][] latitudLongitud = new double[1][2];
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
		    HttpGet request = new HttpGet(url+"/tranvias/1");
		    HttpResponse response = httpClient.execute(request);
		    String responseString = new BasicResponseHandler().handleResponse(response);
		    JSONObject json = (JSONObject)new JSONParser().parse(responseString);
		    JSONArray posiciones = (JSONArray) json.get("posiciones");
		    JSONObject posicion = (JSONObject) posiciones.get(posiciones.size()-1);
		    ultimaLatitud = latitudLongitud[0][0]=(double) posicion.get("latitud");
		    ultimaLongitud = latitudLongitud[0][1]=(double) posicion.get("longitud");
		} catch (Exception ex) {
			ex.printStackTrace();
			ultimaLatitud = latitudLongitud[0][0]=0;
		    ultimaLongitud = latitudLongitud[0][1]=0;
		} finally {
		    try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return latitudLongitud;
	}
	
	public JSONObject tVToJson(double posX, double posY, String estado, double presion, double temperatura){
		JSONObject json = new JSONObject();
		json.put("ubicacionX", posX);
		json.put("ubicacionY", posY);
		json.put("estado", estado);
		json.put("presionChoque", presion);
		json.put("temperatura", temperatura);
		json.put("panico", false);
		return json;
	}
	
	public JSONObject positionToJson(String id, double latitud, double longitud){
		JSONObject json = new JSONObject();
		json.put("tranviaId", id);
		json.put("fecha", strDate);
		json.put("latitud", latitud);
		json.put("longitud", longitud);
		return json;
	}
	
	public void actionPerformed(ActionEvent e){
		double aumentoX = (Math.random()*3)-1;
		double aumentoY = (Math.random()*3)-1;
		double event = Math.random();
		if(event<=0.4)
			aumentoX += 0;
		else if(event <= 0.8)
			aumentoY += 0;
		else{
			aumentoX = 0;
			aumentoY = 0;
		}
		try {
			ultimaLatitud  += aumentoX;
			ultimaLongitud += aumentoY;
			cambiarPosicion(id, ultimaLatitud, ultimaLongitud);
			ppal.refrescarPosicionVista(ultimaLatitud, ultimaLongitud);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
