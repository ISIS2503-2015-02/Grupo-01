package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controller.ControlPrincipal;

public class VistaPrincipal extends JFrame implements ActionListener{

	private final static String TEMPERATURA = "temp";
	
	private final static String ACCIDENTE = "accidente";
	
	private final static String UBICACION = "ubicacion";
	
	private ControlPrincipal control;
	
	private JPanel panelDatos;
	
	private JPanel panelTranvia;
	
	private JButton btnAccidente;
	
	private JButton btnUbicacion;
	
	private JLabel lblStub;
	
	private JButton btnTemperatura;
	
	private JTextField txtAccidente;
	
	private JTextField txtLongitud;
	
	private JTextField txtLatitud;
	
	private JTextField txtTemperatura;
	
	private JLabel labTemperatura;
	
	private JLabel labLongitud;
	
	private JLabel labLatitud;
	
	private JLabel labAccidente;
	
	private JLabel labRuta;
	
	private JLabel labTranvia;
	
	private JTextField txtTranvia;
	
	private JTextField txtRuta;
	
	public VistaPrincipal(ControlPrincipal control){
		this.control = control;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600, 600);
		this.setPreferredSize(new Dimension(600,600));
		this.setLayout(new BorderLayout());
		
		this.setTitle("Tranvia");

		panelDatos = new JPanel();
		panelTranvia = new JPanel();
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Mobibus");
		panelDatos.setBorder(title);
		
		panelDatos.setLayout(new GridLayout(4, 3));
		panelTranvia.setLayout(new GridLayout(1, 4));
		
		labRuta = new JLabel("Ruta:");
		txtRuta = new JTextField();
		
		panelTranvia.add(labRuta);
		panelTranvia.add(txtRuta);
		
		labTranvia = new JLabel("Tranvia:");
		txtTranvia = new JTextField();
	
		panelTranvia.add(labTranvia);
		panelTranvia.add(txtTranvia);
		
		add(panelTranvia, BorderLayout.PAGE_START);
		add(panelDatos, BorderLayout.CENTER);
		
		labTemperatura = new JLabel("Temperatura:");
		txtTemperatura = new JTextField();
		btnTemperatura = new JButton("Actualizar");
		btnTemperatura.addActionListener(this);
		btnTemperatura.setActionCommand(TEMPERATURA);
		
		panelDatos.add(labTemperatura);
		panelDatos.add(txtTemperatura);
		panelDatos.add(btnTemperatura);
		
		labAccidente = new JLabel("Accidente:");
		txtAccidente = new JTextField();
		btnAccidente = new JButton("Informar");
		btnAccidente.setActionCommand(ACCIDENTE);
		btnAccidente.addActionListener(this);
		
		panelDatos.add(labAccidente);
		panelDatos.add(txtAccidente);
		panelDatos.add(btnAccidente);
		
		labLatitud = new JLabel("Latitud:");
		txtLatitud = new JTextField();
		lblStub = new JLabel();
		labLongitud = new JLabel("Longitud:");
		txtLongitud = new JTextField();
		btnUbicacion = new JButton("Actualizar pos");
		btnUbicacion.setActionCommand(UBICACION);
		btnUbicacion.addActionListener(this);
		
		panelDatos.add(labLatitud);
		panelDatos.add(txtLatitud);
		panelDatos.add(lblStub);
		panelDatos.add(labLongitud);
		panelDatos.add(txtLongitud);
		panelDatos.add(btnUbicacion);
		
	}
	
	public static void main(String[] args) {
		ControlPrincipal control = new ControlPrincipal();
		VistaPrincipal vista = new VistaPrincipal(control);
		vista.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String comando = arg0.getActionCommand();
		if (comando.equals(UBICACION)) {
			control.actualizarUbicacion(txtRuta.getText(), txtTranvia.getText(), txtLatitud.getText(), txtLongitud.getText());
		}
		else if(comando.equals(TEMPERATURA)) {
			control.actualizarTemperatura(txtRuta.getText(), txtTranvia.getText(), txtTemperatura.getText());
		}
		else if(comando.equals(ACCIDENTE)){
			control.reportarAccidente(txtRuta.getText(), txtTranvia.getText(), txtAccidente.getText());
		}
	}
}
