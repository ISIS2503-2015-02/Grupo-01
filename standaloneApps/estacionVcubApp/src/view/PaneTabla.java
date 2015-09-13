package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import model.Estacion;

public class PaneTabla extends JPanel implements Observer{
	
	private final static String[] columnas = {"Vcub" , "Usuario"};
	
	private String[][] datos; 
	
	private JTable table;

	public PaneTabla(VistaPrincipal vistaPrincipal) {
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Vcubs");
		this.setBorder(title);
		
		datos = new String[0][0];
		
		table = new JTable(datos, columnas);
		table.setSize(200,300);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.add(scrollPane);
		
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Estacion estacion = (Estacion) arg0;
		
	}

}
