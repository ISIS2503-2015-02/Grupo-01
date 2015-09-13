package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PanelAlquilar extends JPanel implements ActionListener{
	
	// UID
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5772754747373570932L;
	
	// Constantes
	
	
	private final static String DEVOLVER = "devolver";
	private final static String LLEVAR = "llevar";
	
	// Atributos 
	
	private JPanel devolver;
	
	private JPanel llevar;
	
	private JButton btnDevolver;
	
	private JButton btnLlevar;

	private JTextField txtVCub1;
	private JTextField txtVCub2;
	
	private JTextField txtUsuario1;
	private JTextField txtUsuario2;
	
	private JTextField txtEstacion1;
	private JTextField txtEstacion2;
	
	private JLabel labVCub1;
	private JLabel labUsuario1;
	private JLabel labEstacion1;
	private JLabel labVCub2;
	private JLabel labUsuario2;
	private JLabel labEstacion2;
	private VistaPrincipal vista;
	
	public PanelAlquilar(VistaPrincipal vista) {
		this.vista = vista;
		this.setPreferredSize(new Dimension(300,400));
		this.setLayout(new GridLayout(2,1));
		this.setBorder(BorderFactory.createTitledBorder("Alquiler"));
	
		devolver = new JPanel();
		devolver.setLayout(new GridLayout(4, 2));
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Devolver");
		devolver.setBorder(title);
		labVCub1 = new JLabel("Id Vcub");
		labUsuario1 = new JLabel("ID usuario");
		labEstacion1 = new JLabel("Id estación");
		
		txtEstacion1 = new JTextField();
		txtVCub1 = new JTextField();
		txtUsuario1 = new JTextField();
		
		btnDevolver = new JButton("Devolver");
		btnDevolver.addActionListener(this);
		btnDevolver.setActionCommand(DEVOLVER);
		
		labVCub2 = new JLabel("Id Vcub");
		labUsuario2 = new JLabel("ID usuario");
		labEstacion2 = new JLabel("Id estación");
		
		devolver.add(labVCub1);
		devolver.add(txtVCub1);
		devolver.add(labUsuario1);
		devolver.add(txtUsuario1);
		devolver.add(labEstacion1);
		devolver.add(txtEstacion1);
		devolver.add(btnDevolver);
		
		this.add(devolver);
		
		llevar = new JPanel();
		llevar.setLayout(new GridLayout(4, 2));
		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder("Llevar");
		llevar.setBorder(title2);
		
		txtEstacion2 = new JTextField();
		txtVCub2 = new JTextField();
		txtUsuario2 = new JTextField();
		
		btnLlevar = new JButton("Llevar");
		btnLlevar.addActionListener(this);
		btnLlevar.setActionCommand(LLEVAR);
		
		llevar.add(labVCub2);
		llevar.add(txtVCub2);
		llevar.add(labUsuario2);
		llevar.add(txtUsuario2);
		llevar.add(labEstacion2);
		llevar.add(txtEstacion2);
		llevar.add(btnLlevar);
		
		this.add(llevar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(DEVOLVER)){
			vista.devolver(txtUsuario1.getText(), txtVCub1.getText());
		}
		else if(comando.equals(LLEVAR)){
			vista.llevar(txtUsuario2.getText(), txtVCub2.getText());
		}
	}
}
