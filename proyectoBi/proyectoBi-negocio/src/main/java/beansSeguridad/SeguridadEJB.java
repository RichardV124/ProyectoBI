package beansSeguridad;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.AccesoTipoUsuarioEJB;
import entidades.Acceso;
import entidades.TipoUsuario;

/**
 * 
 * @author Richard Vanegas
 *
 */
@LocalBean
@Stateless
public class SeguridadEJB {
	
	@EJB
	private AccesoTipoUsuarioEJB ejb;
	

	/**
	 * Método que lista los accesos validos para un tipo de usuario
	 */
	public List<Acceso> listarAccesosTipoUsuario(TipoUsuario rol, int bd) {
		return ejb.listarByRol(rol, bd);
	}
}