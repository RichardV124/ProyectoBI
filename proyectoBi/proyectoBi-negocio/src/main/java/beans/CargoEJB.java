package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.AreaEmpresa;
import entidades.Cargo;
import entidades.TipoProducto;
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
		Cargo a = buscar(cargo.getId(), bd);
		if(a==null){
			em.crear(cargo);
		}else{
			throw new ExcepcionNegocio("Este cargo ya existe");
		}
		
	}
	
	/**
	 * metodo para buscar un cargo en una base de datos determinada
	 * @param nombre, nombre del cargo a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el Cargo si está en la bd, de lo contrario retornará null
	 */
	public Cargo buscar(int id, int bd){
		em.setBd(bd);
		return (Cargo) em.buscar(Cargo.class, id);
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
	public void eliminar(Cargo cargo, int bd){
		em.setBd(bd);
		Cargo tu = buscar(cargo.getId(), bd);
		if(tu != null){
			em.eliminar(cargo);
		}else{
			throw new ExcepcionNegocio("No existe un tipo de producto con el codigo "+cargo.getId());
		}
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
