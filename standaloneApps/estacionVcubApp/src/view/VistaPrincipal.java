package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import controller.ControlPrincipal;

public class VistaPrincipal extends JFrame{
	
	private PanelPedir panelPedir;
	
	private JList listaVcubs;
	
	private JScrollPane scroll;
	
	private PanelAlquilar panelAlquilar;
	
	private ControlPrincipal control;
	
	private JTabbedPane tabs;
	
	//Constructor
	
	public VistaPrincipal(ControlPrincipal control){
		this.control = control;
		setLayout(new GridLayout(1, 2));
		
		panelPedir = new PanelPedir(this);
		panelAlquilar = new PanelAlquilar(this);
		tabs = new JTabbedPane();
		
		setTitle("Estación Vcub");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600,400));
		setSize(600, 400);
		setResizable(false);
		
		listaVcubs = new JList();
		listaVcubs.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		scroll = new JScrollPane( listaVcubs );
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        
		
		this.add(scroll);
		this.add(tabs);
		
		listaVcubs.setListData( control.darEstacion().getVcubs().toArray( ) );
		
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
		ControlPrincipal control = new ControlPrincipal("http://fast-taiga-5201.herokuapp.com");
		VistaPrincipal app = new VistaPrincipal(control);
		app.setVisible(true);
	}

	public void devolver(String usuario, String vcub) {
		control.devolverVCub(usuario, vcub);
		listaVcubs.setListData( control.darEstacion().getVcubs().toArray( ) );
	}

	public void llevar(String usuario, String vcub) {
		control.llevar(usuario, vcub);
		listaVcubs.setListData( control.darEstacion().getVcubs().toArray( ) );
		
	}

	public void pedir() {
		control.llenarEstacion("1");
		listaVcubs.setListData( control.darEstacion().getVcubs().toArray( ) );
		
	}
}