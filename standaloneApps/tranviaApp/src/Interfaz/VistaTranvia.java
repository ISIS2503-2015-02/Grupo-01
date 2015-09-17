package Interfaz;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.newrelic.deps.org.json.simple.JSONObject;
public class VistaTranvia extends JFrame implements ActionListener{
	
	public final static String TRASMITIR = "Trasmitir";
	public final static String DETENER = "Detener";
	public final static String VOLVER = "Volver";
	
	private static VistaTranvia instancia;
	private String idTranvia;
	private JLabel labLatitud;
	private JLabel labLongitud;
	private JLabel labTemperatura;
	private JLabel labEstado;
	private JLabel labChoque;
	private JButton trasmitir, detener, volver;
	private InterfazTranvia ppal;

	public VistaTranvia (InterfazTranvia ventana, JSONObject tranvia){
		ppal = ventana;
		idTranvia = ""+ tranvia.get("id");
		setTitle("Tranvia : " + idTranvia);
		setLayout(new BorderLayout());	
		setSize(350,250);
		setLocationRelativeTo(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder("Trasmitiendo"));
		panel1.setLayout(new GridLayout(2,2));
		labLatitud = new JLabel((String) tranvia.get("UbicacionX"));
		labLongitud = new JLabel((String) tranvia.get("UbicacionY"));
		panel1.add(new JLabel("latitud"));
		panel1.add( labLatitud);
		panel1.add(new JLabel("longitud"));
		panel1.add(labLongitud);
		
		JPanel panel2= new JPanel();
		panel2.setBorder(new TitledBorder("Detalles Tranvia "+ idTranvia));
		panel2.setLayout(new GridLayout(3,2));
		labEstado = new JLabel((String) tranvia.get("estado"));
		labTemperatura = new JLabel(""+(double) tranvia.get("temperatura"));
		labChoque = new JLabel(""+(double)tranvia.get("presionChoque"));
		panel2.add(new JLabel("Estado :"));
		panel2.add(labEstado);
		panel2.add(new JLabel("Temperatura :"));
		panel2.add(labTemperatura);
		panel2.add(new JLabel("Presion de choque :"));
		panel2.add(labChoque);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1,3));
		trasmitir = new  JButton(TRASMITIR);
		trasmitir.addActionListener(this);
		trasmitir.setActionCommand(TRASMITIR);
		detener = new JButton(DETENER);
		detener.addActionListener(this);
		detener.setActionCommand(DETENER);
		volver = new JButton(VOLVER);
		volver.addActionListener(this);
		volver.setActionCommand(VOLVER);
		panel3.add(trasmitir);
		panel3.add(detener);
		panel3.add(volver);
		
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(panel3, BorderLayout.SOUTH);
		
	}
	public static VistaTranvia darInstancia(InterfazTranvia ventana, JSONObject tranvia){
		instancia = null;
		instancia = new VistaTranvia(ventana, tranvia);
		return instancia;
	}
	
	public void actualizarPosicion(double latitud, double longitud){
		labLatitud.setText(""+latitud);
		labLongitud.setText(""+longitud);
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if(c.equals(TRASMITIR)){
			ppal.empezarATrasmitirPosiciones(idTranvia);
		}else if(c.equals(DETENER)){
			ppal.detenerTrasmision();
		}else{
			ppal.detenerTrasmision();
			ppal.setVisible(true);
			this.setVisible(false);
		}
	}
}