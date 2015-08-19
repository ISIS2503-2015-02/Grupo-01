package models;

import models.Vehiculo;
import models.Estacion;

public class Vcub extends Vehiculo
{
	//Constantes
	
		public final static String PRESTADA = "prestada";
		
		public final static String RESTITUIDA = "restituida";
		
	//Atributos 
		
		/**
		 * Estacion donde se encuentra ubicado el Vcub
		 */
		private Estacion estacion;
		
		/**
		 * Estado del Vcub
		 */
		private String estado;
		
		/**
		 * Identificador del Vcub
		 */
		private String ID;
		
	//Constructor 
		
		/**
		 * 
		 * @param estacion
		 * @param ID
		 */
		public Vcub (Estacion estacion, String ID)
		{
			super(Vehiculo.VCUB);
			this.estacion = estacion;
			this.estado = RESTITUIDA;
		}
		
	// Metodos
		
		/**
		 * 
		 * @return
		 */
		public Estacion darEstacion()
		{
			return estacion;
		}
		
		/**
		 * 
		 * @param nueva
		 */
		public void cambiarEstacion(Estacion nueva)
		{
			this.estacion = nueva;
		}
		
		/**
		 * Devuelve el estado del Vcub
		 * @return estado. String
		 */
		public String darEstado()
		{
			return estado;
		}
		
		/**
		 * Cambia el estado del Vcub de presada a restituida y viceversa
		 */
		public void cambiarEstado()
		{
			if(estado.equals(PRESTADA))
			{
				estado = RESTITUIDA;
			}
			else if(estado.equals(RESTITUIDA))
			{
				estado = PRESTADA;
			}
		}
		
		/**
		 * Devuelve el ID del Vcub
		 * @return
		 */
		public String darID()
		{
			return ID;
		}
	
}
