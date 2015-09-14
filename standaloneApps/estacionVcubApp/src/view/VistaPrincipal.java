package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import controller.ControlPrincipal;

public class VistaPrincipal extends JFrame{
	
	private PanelPedir panelPedir;
	
	private PaneTabla panelTabla;
	
	private PanelAlquilar panelAlquilar;
	
	private ControlPrincipal control;
	
	private JTabbedPane tabs;
	
	//Constructor
	
	public VistaPrincipal(ControlPrincipal control){
		this.control = control;
		setLayout(new GridLayout(1, 2));
		
		panelPedir = new PanelPedir(this);
		panelTabla = new PaneTabla(this);
		panelAlquilar = new PanelAlquilar(this);
		tabs = new JTabbedPane();
		
		setTitle("Estación Vcub");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600,400));
		setSize(600, 400);
		setResizable(false);
		
		this.add(panelTabla);
		this.add(tabs);
		
		tabs.add("Alquilar", panelAlquilar);
		tabs.add("Pedir", panelPedir);
	}
	
	//Metodos
	
	//Dispose
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	
	}
	
	//Main
	
	public static void main(String[] args) {
		ControlPrincipal control = new ControlPrincipal();
		VistaPrincipal app = new VistaPrincipal(control);
		app.setVisible(true);
	}

	public void devolver(String usuario, String vcub) {
		control.devolverVCub(usuario, vcub);
	}

	public void llevar(String usuario, String vcub) {
		control.llevar(usuario, vcub);
		
	}

	public void pedir() {
		// TODO Auto-generated method stub
		
	}
}