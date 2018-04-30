package session;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.AccesoTipoUsuarioEJB;
import beans.ConexionEJB;
import beans.UsuarioEJB;
import beansSeguridad.SeguridadEJB;
import entidades.Acceso;
import entidades.Conexion;
import entidades.Usuario;

@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

	@EJB
	private SeguridadEJB seguridadEJB;

	@EJB
	private UsuarioEJB usuarioEJB;

	@EJB
	private AccesoTipoUsuarioEJB accesoTipoUsuarioEJB;

	@EJB
	private ConexionEJB conexionEJB;

	private String username;

	private String password;

	/**
	 * Es la base de datos que el sistema esta usando actualmente
	 */
	private int bd = 2;

	/**
	 * El usuario que ha iniciado sesion en la aplicacion
	 */
	private Usuario usuario;

	/**
	 * Listado de accesos que tiene el usuario que inicio sesion
	 */
	private List<Acceso> accesos;

	/**
	 * Iniciar sesion
	 */
	public String login() {
		Usuario usu = usuarioEJB.buscarPorUsername(username, 2);

		if (usu != null) {

			usuario = usu;

			if (usu.getLogin().getPassword().equals(password)) {

				if (usu.getTipoUsuario().getId() == 1) { // administrador

					Faces.setSessionAttribute("user", usuario);
					System.out.println("Inicio sesion administrador");

					accesos = accesoTipoUsuarioEJB.listarAccesosPorTipo(usu.getTipoUsuario(), bd);
					
					Conexion c = conexionEJB.buscar(2, bd);
					
					this.bd=c.getId();
					
					Faces.setSessionAttribute("bd", bd);

					Messages.addFlashGlobalInfo("Ha iniciado sesion correctamente");

					return "/paginas/seguro/administrador/inicioAdministrador.xhtml?faces-redirect=true";

				} else if (usu.getTipoUsuario().getId() == 2) { // cliente
					Faces.setSessionAttribute("user", usuario);
					System.out.println("Inicio sesion cliente");

					accesos = accesoTipoUsuarioEJB.listarAccesosPorTipo(usu.getTipoUsuario(), bd);

					bd = conexionEJB.buscar(2, bd).getId();
					Faces.setSessionAttribute("bd", bd);

					Messages.addFlashGlobalInfo("Ha iniciado sesion correctamente");

					return "/paginas/seguro/administrador/inicioCliente.xhtml?faces-redirect=true";

				} else if (usu.getTipoUsuario().getId() == 3) { // el otro
					Faces.setSessionAttribute("user", usuario);
					System.out.println("Inicio sesion medico");

					accesos = accesoTipoUsuarioEJB.listarAccesosPorTipo(usu.getTipoUsuario(), bd);

					bd = conexionEJB.buscar(2, bd).getId();
					Faces.setSessionAttribute("bd", bd);

					Messages.addFlashGlobalInfo("Ha iniciado sesion correctamente");

					return "/paginas/seguro/medico/citaMedico.xhtml?faces-redirect=true";

				} else {
					Messages.addFlashGlobalError("Usuario o Password incorrectos");
				}
			} else {
				Messages.addFlashGlobalError("Usuario o Password incorrectos");
			}
		} else {
			Messages.addFlashGlobalError("Usuario o Password incorrectos");
		}
		return null;
	}

	/**
	 * Cierra la sesion en la aplicacion
	 * 
	 * @return la pagina de login
	 */
	public String cerrarSesion() {
		usuario = null;
		HttpSession sesion;
		sesion = (HttpSession) Faces.getSession();
		sesion.invalidate();
		return "/paginas/publico/login.xhtml?faces-redirect=true";
	}

	public boolean isSesion() {
		return usuario != null;
	}

	/**
	 * @return the accesos
	 */
	public List<Acceso> getAccesos() {
		return accesos;
	}

	/**
	 * @param accesos
	 *            the accesos to set
	 */
	public void setAccesos(List<Acceso> accesos) {
		this.accesos = accesos;
	}

	/**
	 * @return the bd
	 */
	public int getBd() {
		return bd;
	}

	/**
	 * @param bd
	 *            the bd to set
	 */
	public void setBd(int bd) {
		this.bd = bd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
