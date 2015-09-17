package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.Estacion;

public class PanelPedir extends JPanel implements ActionListener{

	// Costantes
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5757613798705256945L;

	private final static String PEDIR = "pedir";
	
	// Atributos
	
	private VistaPrincipal vista;
	
	private JButton btnPedir;
	
	private JLabel labCapacidad;
	
	private JLabel labCantidad;
	
	private JLabel labMensaje;
	
	private Estacion estacion;
	
	// Constructor
	
	public PanelPedir(VistaPrincipal vistaPrincipal) {
		setLayout(new GridLayout(4, 1));
		vista = vistaPrincipal;
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Estacion ");
		this.setBorder(title);
		
		btnPedir = new JButton("Pedir");
		btnPedir.addActionListener(this);
		btnPedir.setActionCommand(PEDIR);
		
		labCapacidad = new JLabel();
		labCantidad = new JLabel();
		labMensaje = new JLabel();
		
		add(labCantidad);
		add(labCapacidad);
		add(labMensaje);
		add(btnPedir);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(PEDIR)){
			vista.pedir();
		}
	}

}