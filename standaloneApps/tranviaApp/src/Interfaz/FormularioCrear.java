package Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.accessibility.AccessibleAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormularioCrear extends JFrame implements ActionListener{
	
	public final static String CREAR = "Crear";
	public final static String LIMPIAR = "Limpiar";
	public final static String VOLVER = "Volver";
	
	private JPanel panelFormas;
	private JTextField ubicacionX;
	private JTextField ubicacionY;
	private JTextField estado;
	private JTextField presionChoque;
	private JTextField temperatura;
	
	private JPanel panelBotones;
	private JButton butCrear;
	private JButton butLimpiar;
	private JButton butVolver;
	
	private static FormularioCrear instancia;
	private InterfazTranvia principal;
	
	public FormularioCrear(InterfazTranvia ventana){
		principal = ventana;
		this.setTitle("Crear nuevo Tranvia");
		this.setSize(250, 300);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		ubicacionX = new JTextField();
		ubicacionY = new JTextField();
		estado = new JTextField();
		presionChoque = new JTextField();
		temperatura = new JTextField();
		
		panelFormas = new JPanel();
		panelFormas.setLayout(new GridLayout(5,2));
		panelFormas.add(new JLabel("Ubicacion X: "));
		panelFormas.add(ubicacionX);
		panelFormas.add(new JLabel("Ubicacion Y: "));
		panelFormas.add(ubicacionY);
		panelFormas.add(new JLabel("Estado: "));
		panelFormas.add(estado);
		panelFormas.add(new JLabel("Presion choque: "));
		panelFormas.add(presionChoque);
		panelFormas.add(new JLabel("Temperatura: "));
		panelFormas.add(temperatura);
		this.add(panelFormas, BorderLayout.NORTH);
		
		butCrear = new JButton("Crear");
		butCrear.addActionListener(this);
		butCrear.setActionCommand(CREAR);
		butLimpiar = new JButton("Limpiar");
		butLimpiar.addActionListener(this);
		butLimpiar.setActionCommand(LIMPIAR);
		butVolver = new JButton("Volver");
		butVolver.addActionListener(this);
		butVolver.setActionCommand(VOLVER);
		
		panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(1,3));getContentPane();
		panelBotones.add(butCrear);
		panelBotones.add(butLimpiar);
		panelBotones.add(butVolver);
		this.add(panelBotones, BorderLayout.SOUTH);
	}
	
	public static FormularioCrear darInstancia(InterfazTranvia ventana){
		if(instancia == null)
			instancia = new FormularioCrear(ventana);
		return instancia;
	}
	
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(CREAR)){
			try{
				double x = Double.parseDouble(ubicacionX.getText());
				double y = Double.parseDouble(ubicacionY.getText());
				double presionChoque = Double.parseDouble(this.presionChoque.getText());
				double temperatura = Double.parseDouble(this.temperatura.getText());
				principal.crearTranvia(x, y, estado.getText(), presionChoque, temperatura);
			}catch(Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Revise los valores ingresados e intente nuevamente");
			}
		}
		else if(comando.equals(LIMPIAR)){
			ubicacionX.setText("");
			ubicacionY.setText("");
			estado.setText("");
			presionChoque.setText("");
			temperatura.setText("");
		}
		else{
			principal.setVisible(true);
			this.setVisible(false);
		}
		
	}

}