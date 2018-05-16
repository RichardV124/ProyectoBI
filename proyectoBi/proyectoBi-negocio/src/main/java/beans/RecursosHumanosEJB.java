package beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class RecursosHumanosEJB {
	
	@EJB
	private Persistencia em;
	
	
	/**
	 * Método para crear recursos humanos en una base de datos determinada
	 * @param usuario, usuario que se desea crear
	 * @param bd base de datos en la cual se desea persistir
	 */
	public void crearRecursoH(Usuario usuario, int bd){
		em.setBd(bd);
		Usuario usu = buscarRecursoH(usuario.getCedula(), bd);
		if (usu == null) {
			em.crear(usuario);
		}else{
			throw new ExcepcionNegocio("Ya se encuentra registrado");
		}
		
	}
	
	/**
	 * metodo para buscar un usuario en una base de datos determinada
	 * @param cedula, cedula del usuario a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el Usuario si está en la bd, d elo contrario retornará null
	 */
	public Usuario buscarRecursoH(String cedula, int bd){
		em.setBd(bd);
		return (Usuario) em.buscar(Usuario.class, cedula);
	}

}
