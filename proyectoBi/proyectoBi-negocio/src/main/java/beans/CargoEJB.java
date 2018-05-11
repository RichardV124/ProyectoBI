package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Cargo;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class CargoEJB {

	@EJB
	private Persistencia em;
	
	/**
	 * Método para crear un cargo en una base de datos determinada
	 * @param cargo, cargo que se desea crear
	 * @param bd base de datos en la cual se desea persistir
	 */
	public void crear(Cargo cargo, int bd){
		em.setBd(bd);
		Cargo c = buscar(cargo.getNombre(), bd);
		if (c == null) {
			em.crear(cargo);
		}else{
			throw new ExcepcionNegocio("El cargo ya se encuentra registrado");
		}
		
	}
	
	/**
	 * metodo para buscar un cargo en una base de datos determinada
	 * @param nombre, nombre del cargo a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el Cargo si está en la bd, de lo contrario retornará null
	 */
	public Cargo buscar(String nombre, int bd){
		em.setBd(bd);
		return (Cargo) em.buscar(Cargo.class, nombre);
	}
	
	/**
	 * metodo para editar un cargo en una base de datos determinada
	 * @param cargo, cargo que se desea editar en la bd
	 * @param bd, base de datos en la cual se desea editar el usuario
	 */
	public void editar(Cargo cargo, int bd){
		em.setBd(bd);
		em.editar(cargo);
	}
	
	/**
	 * metodo para eliminar un cargo en una base de datos determinada
	 * @param cargo, cargo que se desea eliminar en la bd
	 * @param bd, base de datos en la cual se desea eliminar el usuario
	 */
	public void eliminar (Cargo cargo, int bd){
		em.setBd(bd);
		em.eliminar(cargo);
	}

	/**
	 * metodo para eliminar un usuario en una base de datos determinada
	 * @param bd, base de datos de la cual se desea listar
	 * @return una lista con los usuarios registrados
	 */
	public List<Cargo> listar(int bd){
		em.setBd(bd);	
		List<Cargo> cargos= (List<Cargo>)(Object)em.listar(Cargo.CONSULTA_LISTAR_CARGOS);
		if (cargos.isEmpty()) {
			throw new ExcepcionNegocio("No hay cargos registrados en la base de datos");
		} else {
			return cargos;
		}
		
	}
}
