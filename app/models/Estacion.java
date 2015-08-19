import java.util.ArrayList;
import java.util.List;




public class Estacion 
{
	//Constante
	/**
	 * Capacidad de la estacion
	 */
	private int capacidad;

	//Atributos
	
	/**
	 * Identificador de la estacion
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	/**
	 * Ubicacion de la estacion
	 */
	private String ubicacion;
	
	/**
	 * Vector que contiene los Vcubs de la estacion
	 */
	@OneToMany(cascade=CascadeType.ALL)
	private List<Vcub> vcubs;
	
	/**
	 * Indica si la estacion esta llena
	 */
	private boolean llena;
	
	/**
	 * El porcentage de ocupacion de la estacion
	 */
	private double ocupacion;
	
	public Estacion(){}
	
	//Constructor
	/**
	 * 
	 * @param ID
	 * @param ubicacion
	 */
	public Estacion (String ID, String ubicacion)
	{
		this.id = id;
		this.ubicacion = ubicacion;
		this.vcubs = new ArrayList<Vcub>();
		this.llena = false;
		this.ocupacion = 0.0;
	}

	//Metodos
	
	/**
	 * Devuelve el ID de la estacion
	 * @return El ID. String
	 */
	public String darID() 
	{
		return ID;
	}

	/**
	 * Devuelve la ubicacion de la ciudad en donde esta la estacion 
	 * @return La ubicacion. String
	 */
	public String darUbicacion() {
		return ubicacion;
	}
	
	/**
	 * Devuelve en vector con los Vcub de la estacion
	 * @return Los Vcub. ArrayList
	 */
	public List<Vcub> darVcubs()
	{
		return vcubs;
	}

	/**
	 * Indica si la estacion esta ocupada
	 * @return True si esta ocupada, false de lo contrario
	 */
	public boolean estaLlena() 
	{
		return llena;
	}

	/**
	 * Cambia el estado de la estacion
	 * @param llena. Boolean
	 */
	public void cambiarEstado(boolean llena) 
	{
		this.llena = llena;
	}

	/**
	 * Devuelve el porcentage de ocupacion de la estacion
	 * @return
	 */
	public double darOcupacion() 
	{
		return ocupacion;
	}

	/**
	 * Asigna una nueva ocupacion a la estacion
	 * @param ocupacion. Double
	 */
	public void asignarOcupacion(double ocupacion) 
	{
		this.ocupacion = ocupacion;
	}
	
	/**
	 * 
	 */
	public void agregarVcub(Vcub bicicleta) throws Exception
	{
		if(!estaLlena())
		{
			if(bicicleta != null)
			{
				vcubs.add(bicicleta);			
			}
			double p = (vcubs.size()/CAPACIDAD); 
			asignarOcupacion(p);
			if(vcubs.size() == CAPACIDAD)
			{
				cambiarEstado(true);
			}
		}
		else
		{
			throw new Exception("No hay capacidad en la estacion");
		}
	}
		
	
	/**
	 * Elimina un Vcub de la estacion dado su ID
	 */
	public void retirarVcub(String ID)
	{
		for (int i = 0; i < vcubs.size(); i++) 
		{
			Vcub actual = vcubs.get(i);
			if(actual.darID().equals(ID))
			{
				vcubs.remove(actual);				
			}
		}
		double p = (vcubs.size()/CAPACIDAD); 
		asignarOcupacion(p);
	}

	

	
	
	
	
	
}
