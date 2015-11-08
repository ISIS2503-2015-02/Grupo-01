package models;

public final class Cons  {

	//Estado vehiculos
	public static final String V_DISPONIBLE = "Disponible";
	public static final String V_OCUPADO = "Ocupado";
	public static final String V_ACCIDENTADO = "Accidentado";

	//Estado reservas
	public static final String R_ESPERA = "En espera";
	public static final String R_ASIGNADA = "Asignada";

	//Tipo de trayectos
	public static final String TT_TRANVIA = "Ruta tranvia";
	public static final String TT_MOBIBUS = "Ruta movibus";

	//Tipo de estado terminacion trayecto
	public static final String ET_TERMINADO = "Terminado";
	public static final String ET_CURSO = "En curso";
	public static final String ET_ANORMAL = "Anormal";

	//Tipo accidente trayecto
	public static final String EA_NORMAL = "Normal";
	public static final String EA_CHOQUE = "Choque";
	public static final String EA_INCENDIO = "Incendio";
	public static final String EA_ATROPELLO = "Atropello";

	//Roles del sistema
	public static final String ROL_USUARIO = "usuario";
	public static final String ROL_ADMIN = "admin";
	public static final String ROL_VCUB = "vcub";
	public static final String ROL_TRANVIA = "tranvia";
	public static final String ROL_MOBIBUS = "mobibus";
	public static final String ROL_ESTACION = "estacion";

	//CORS
	public static final String CORS = "Access-Control-Allow-Origin";
	public static final String ALL = "*";
	private Cons(){
		
	}

}