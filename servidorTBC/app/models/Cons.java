package models;

public final class Cons  {

	//Estado vehiculos
	public final static String V_DISPONIBLE = "Disponible";
	public final static String V_OCUPADO = "Ocupado";
	public final static String V_ACCIDENTADO = "Accidentado";

	//Estado reservas
	public final static String R_ESPERA = "En espera";
	public final static String R_ASIGNADA = "Asignada";

	//Tipo de trayectos
	public final static String TT_TRANVIA = "Ruta tranvia";
	public final static String TT_MOBIBUS = "Ruta movibus";

	//Tipo de estado terminacion trayecto
	public final static String ET_TERMINADO = "Terminado";
	public final static String ET_CURSO = "En curso";
	public final static String ET_ANORMAL = "Anormal";

	//Tipo accidente trayecto
	public final static String EA_NORMAL = "Normal";
	public final static String EA_CHOQUE = "Choque";
	public final static String EA_INCENDIO = "Incendio";
	public final static String EA_ATROPELLO = "Atropello";

}