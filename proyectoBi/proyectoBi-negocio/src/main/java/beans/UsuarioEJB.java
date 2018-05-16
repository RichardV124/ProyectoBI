package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Cliente;
import entidades.Auditoria;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class UsuarioEJB {

	@EJB
	private Persistencia em;
	
	/**
	 * Método para crear un usuario en una base de datos determinada
	 * @param usuario, usuario que se desea crear
	 * @param bd base de datos en la cual se desea persistir
	 */
	public void crear(Usuario usuario, int bd){
		em.setBd(bd);
		Usuario usu = buscar(usuario.getCedula(), bd);
		if (usu == null) {
			em.crear(usuario);
		}else{
			throw new ExcepcionNegocio("El usuario ya se encuentra registrado");
		}
		
	}
	
	/**
	 * metodo para buscar un usuario en una base de datos determinada
	 * @param cedula, cedula del usuario a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el Usuario si está en la bd, d elo contrario retornará null
	 */
	public Usuario buscar(String cedula, int bd){
		em.setBd(bd);
		return (Usuario) em.buscar(Usuario.class, cedula);
	}
	

	/**
	 * metodo para buscar un Cliente en una base de datos determinada
	 * @param cedula, cedula del Cliente a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el Cliente si está en la bd, d elo contrario retornará null
	 */
	public Cliente buscarCliente(String cedula, int bd){
		em.setBd(bd);
		return (Cliente) em.buscar(Cliente.class, cedula);
	}
	
	/**
	 * Método para crear un usuario en una base de datos determinada
	 * @param usuario, usuario que se desea crear
	 * @param bd base de datos en la cual se desea persistir
	 */
	public void crearCliente(Cliente usuario, int bd){
		em.setBd(bd);
		Cliente usu = buscarCliente(usuario.getCedula(), bd);
		if (usu == null) {
			em.crear(usuario);
		}else{
			throw new ExcepcionNegocio("El cliente ya se encuentra registrado");
		}
		
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
	public void eliminar (Usuario usuario, int bd){
		em.setBd(bd);
		em.eliminar(usuario);
	}

	/**
	 * metodo para eliminar un usuario en una base de datos determinada
	 * @param bd, base de datos de la cual se desea listar
	 * @return una lista con los usuarios registrados
	 */
	public List<Usuario> listarUsuarios(int bd){
		em.setBd(bd);	
		List<Usuario> usuarios= (List<Usuario>)(Object)em.listar(Usuario.CONSULTA_LISTAR_USUARIOS);
		if (usuarios.isEmpty()) {
			throw new ExcepcionNegocio("No hay usuarios registrados en la base de datos");
		} else {
			return usuarios;
		}
		
	}
	/**
	 * Metodo para listar todos los registros de los recursos humanos
	 * @param bd base de datos de la que se desea listar
	 * @return una lista con todos los recuros humanos
	 */
	public List<Usuario> listarRecursosHumanos(int bd){
		em.setBd(bd);	
		List<Usuario> usuarios= (List<Usuario>)(Object)em.listar(Usuario.CONSULTA_LISTAR_RECURSOS_HUMANOS);
		if (usuarios.isEmpty()) {
			throw new ExcepcionNegocio("No hay usuarios registrados en la base de datos");
		} else {
			return usuarios;
		}
		
	}
	

	/**
	 * Buscar un usuario por username
	 * @param username el username del usuario a buscar
	 * @param bd base de datos en la que buscara
	 * @return el usuario en contrado, de lo contrario null
	 */
	public Usuario buscarPorUsername(String username, int bd){
		em.setBd(bd);
		List<Object> lista = em.listarConParametroString(Usuario.buscarPorUsername,username);
		if(lista.size() > 0){
			return (Usuario)lista.get(0);
		}else{
			return null;
		}
	}
	
	public List<Usuario> listarUsuariosInactivos(int bd){
		em.setBd(bd);	
		List<Usuario> usuarios= (List<Usuario>)(Object)em.listarConUnParametro(Usuario.CONSULTA_LISTAR_USUARIOS_INACTIVOS,false);
		if (usuarios.isEmpty()) {
			throw new ExcepcionNegocio("No hay usuarios registrados en la base de datos");
		} else {
			return usuarios;
		}
	}
	
}
