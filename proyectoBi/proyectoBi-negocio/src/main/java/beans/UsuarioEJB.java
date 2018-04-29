package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Usuario;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class UsuarioEJB {

	@EJB
	private Persistencia em;
	
	/**
	 * M�todo para crear un usuario en una base de datos determinada
	 * @param usuario, usuario que se desea crear
	 * @param bd base de datos en la cual se desea persistir
	 */
	public void crear(Usuario usuario, int bd){
		em.setBd(bd);
		em.crear(usuario);
	}
	
	/**
	 * metodo para buscar un usuario en una base de datos determinada
	 * @param cedula, cedula del usuario a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el Usuario si est� en la bd, d elo contrario retornar� null
	 */
	public Usuario buscar(String cedula, int bd){
		em.setBd(bd);
		return (Usuario) em.buscar(Usuario.class, cedula);
	}
	
	/**
	 * metodo para editar un usuario en una base de datos determinada
	 * @param Usuario, usuario que se desea editar en la bd
	 * @param bd, base de datos en la cual se desea editar el usuario
	 */
	public void editar(Usuario Usuario, int bd){
		em.setBd(bd);
		em.editar(Usuario);
	}
	
	/**
	 * metodo para eliminar un usuario en una base de datos determinada
	 * @param Usuario, usuario que se desea eliminar en la bd
	 * @param bd, base de datos en la cual se desea eliminar el usuario
	 */
	public void eliminar (Usuario Usuario, int bd){
		em.setBd(bd);
		em.eliminar(Usuario);
	}

	/**
	 * metodo para eliminar un usuario en una base de datos determinada
	 * @param bd, base de datos de la cual se desea listar
	 * @return una lista con los usuarios registrados
	 */
	public List<Usuario> listarUsuarios(int bd){
		em.setBd(bd);	
		return (List<Usuario>)(Object)em.listar(Usuario.CONSULTA_LISTAR_USUARIOS);
	}
	
}
