package beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Login;
import entidades.Login;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class LoginEJB {

	@EJB
	private Persistencia em;
	
	/**
	 * Método para crear un Login en una base de datos determinada
	 * @param Login, Login que se desea crear
	 * @param bd base de datos en la cual se desea persistir
	 */
	public void crear(Login login, int bd){
		em.setBd(bd);
		em.crear(login);
	}
	
	/**
	 * metodo para buscar un Login en una base de datos determinada
	 * @param cedula, cedula del Login a buscar
	 * @param bd, base de datos en la cual se desea buscar 
	 * @return el Login si está en la bd, d elo contrario retornará null
	 */
	public Login buscar(String nick, int bd){
		em.setBd(bd);
		return (Login) em.buscar(Login.class, nick);
	}
	
	/**
	 * metodo para editar un Login en una base de datos determinada
	 * @param Login, Login que se desea editar en la bd
	 * @param bd, base de datos en la cual se desea editar el Login
	 */
	public void editar(Login login, int bd){
		em.setBd(bd);
		em.editar(login);
	}
	
	/**
	 * metodo para eliminar un Login en una base de datos determinada
	 * @param Login, Login que se desea eliminar en la bd
	 * @param bd, base de datos en la cual se desea eliminar el Login
	 */
	public void eliminar (Login login, int bd){
		em.setBd(bd);
		em.eliminar(login);
	}
}
