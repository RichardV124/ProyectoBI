package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.AreaEmpresa;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class AreaEmpresaEJB {

	@EJB
	private Persistencia em;
	
	/**
	 * Método para crear un AreaEmpresa en una base de datos determinada
	 * @param AreaEmpresa, AreaEmpresa que se desea crear
	 * @param bd base de datos en la cual se desea persistir
	 */
	public void crear(AreaEmpresa area, int bd){
		em.setBd(bd);
		AreaEmpresa a = buscar(area.getId(), bd);
		if(a==null){
			em.crear(area);
		}else{
			throw new ExcepcionNegocio("El area de la empresa ya existe");
		}
		
	}
	
	/**
	 * metodo para buscar un AreaEmpresa en una base de datos determinada
	 * @param id, id del AreaEmpresa a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el AreaEmpresa si está en la bd, d elo contrario retornará null
	 */
	public AreaEmpresa buscar(int id, int bd){
		em.setBd(bd);
		return (AreaEmpresa) em.buscar(AreaEmpresa.class, id);
	}
	
	/**
	 * metodo para editar un AreaEmpresa en una base de datos determinada
	 * @param AreaEmpresa, AreaEmpresa que se desea editar en la bd
	 * @param bd, base de datos en la cual se desea editar el AreaEmpresa
	 */
	public void editar(AreaEmpresa AreaEmpresa, int bd){
		em.setBd(bd);
		em.editar(AreaEmpresa);
	}
	
	/**
	 * metodo para eliminar un AreaEmpresa en una base de datos determinada
	 * @param AreaEmpresa, AreaEmpresa que se desea eliminar en la bd
	 * @param bd, base de datos en la cual se desea eliminar el AreaEmpresa
	 */
	public void eliminar (AreaEmpresa AreaEmpresa, int bd){
		em.setBd(bd);
		em.eliminar(AreaEmpresa);
	}
	
	/**
	 * metodo para listar las areas en una base de datos determinada
	 * @param bd, base de datos de la cual se desea listar
	 * @return una lista con los areas registrados
	 */
	public List<AreaEmpresa> listarUsuarios(int bd){
		em.setBd(bd);	
		List<AreaEmpresa> areas= (List<AreaEmpresa>)(Object)em.listar(AreaEmpresa.CONSULTA_LISTAR_AREAS_EMPRESA);
		if (areas.isEmpty()) {
			throw new ExcepcionNegocio("No hay areas registrados en la base de datos");
		} else {
			return areas;
		}
		
	}
}
