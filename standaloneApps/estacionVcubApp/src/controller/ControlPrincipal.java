package controller;

import java.net.MalformedURLException;
import java.net.URL;

import model.Estacion;

public class ControlPrincipal {
	
	private Estacion actual;

	public void devolverVCub(String usuario, String vcub) {
		// TODO Auto-generated method stub
		
	}

	public void llevar(String usuario, String vcub) {
		try {
			URL url = new URL("http://192.168.0.12:9000");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
