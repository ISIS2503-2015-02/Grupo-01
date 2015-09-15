package Interfaz;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.newrelic.deps.org.json.simple.JSONObject;

import Mundo.Mundo;

public class InterfazTranvia  extends JFrame implements ActionListener{
	
	public final static String CREAR = "Crear nuevo Tranvia";
	public final static String CONSULTAR = "Buscar existente";
	public final static String ACTUALIZAR = "Actualizar";
	public final static String ELIMINAR = "Eliminar";
	
	private static String url;
	private JButton butCrear;
	private JButton butConsultar;
	private JButton butActualizar;
	private JButton butEliminar;
	
	private FormularioCrear formCrear;
	private VistaTranvia vTranvia;
	private Mundo mundo;
	
	public InterfazTranvia(){
		formCrear = FormularioCrear.darInstancia(this);
		mundo = new Mundo(this, url);
		setSize(200, 300);
		setTitle("Tranvia StandAlone");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		butCrear = new JButton("Crear");
		butCrear.addActionListener(this);
		butCrear.setActionCommand(CREAR);
		butConsultar = new JButton("Consultar");
		butConsultar.addActionListener(this);
		butConsultar.setActionCommand(CONSULTAR);
		butActualizar = new JButton("Actualizar");
		butActualizar.addActionListener(this);
		butActualizar.setActionCommand(ACTUALIZAR);
		butEliminar = new JButton("Eliminar");
		butEliminar.addActionListener(this);
		butEliminar.setActionCommand(ELIMINAR);
		
		this.setLayout(new GridLayout(9,1));
		add(new JLabel());
		add(new JLabel());
		add(butCrear);
		add(butConsultar);
		add(new JLabel("vacio temp"));
		add(new JLabel("vacio temp"));
		add(new JLabel());
		add(new JLabel());
	}
	
	public void crearTranvia(double posX, double posY, String estado, double presion, double temperatura){
		try {
			JSONObject jsonBack = mundo.crearTranvia(posX, posY, estado, presion, temperatura);
			abrirVistaTranvia(jsonBack);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void abrirVistaTranvia(JSONObject tranvia){
		formCrear.setVisible(false);
		vTranvia = VistaTranvia.darInstancia(this, tranvia);
		vTranvia.setVisible(true);
		double[][] latilong = mundo.ultimaPosicionTranvia(""+tranvia.get("id"));
		vTranvia.actualizarPosicion(latilong[0][0], latilong[0][1]);
		this.setVisible(false);
	}
	public void empezarATrasmitirPosiciones(String id){
		mundo.empezarActualizaciones(id);
	}
	
	public void detenerTrasmision(){
		mundo.detenerActualziaciones();
	}
	
	public void refrescarPosicionVista(double latitud, double longitud){
		System.out.println(latitud +  " - " + longitud);
		vTranvia.actualizarPosicion(latitud, longitud);
	}

	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(CREAR)){
			formCrear.setVisible(true);
			this.setVisible(false);
		}
		else if(comando.equals(CONSULTAR)){
			String id = JOptionPane.showInputDialog(null,"Introduzca el id del Tranvia: ");
			try{
				abrirVistaTranvia(mundo.darTranvia(id));
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		else if(comando.equals(ACTUALIZAR)){
			
		}
		else{
			
		}
		
	}

	
	public static void main(String[] args) {
		url = JOptionPane.showInputDialog(null,"Introduzca la url de la app", "http://localhost:9000");
		InterfazTranvia interfaz = new InterfazTranvia();
		interfaz.setLocationRelativeTo(null);
		interfaz.setVisible(true);
	}
}
