package session;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.ConexionEJB;
import beans.LoginEJB;
import beans.UsuarioEJB;
import beansSeguridad.SeguridadEJB;
import entidades.Acceso;
import entidades.Login;
import entidades.Usuario;


@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {
	
	@EJB
	private SeguridadEJB seguridadEJB;
	
	@EJB
	private UsuarioEJB usuarioEJB;
	
	
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
	public void login() {
		if(username.isEmpty() || password.isEmpty()){
			Messages.addFlashGlobalError("Por favor, ingrese todos los campos");
		}else{
			Usuario u = usuarioEJB.buscarPorUsername(username, bd);
			if(u != null){
				if(u.getLogin().getPassword().equals(password)){
					usuario = u;
					accesos = seguridadEJB.listarAccesosTipoUsuario(usuario.getTipoUsuario(), bd);
					Faces.setSessionAttribute("usuario", usuario);

					bd = conexionEJB.buscar(1, 1).getId();
					Faces.setSessionAttribute("bd", bd);
					Messages.addFlashGlobalInfo("Ha iniciado sesion correctamente");
					
				}else{
					// Contraseña incorrecta
					Messages.addFlashGlobalError("Username o contraseña incorrectos");
				}
			}else{
				// Usuario no existe
				Messages.addFlashGlobalError("Username o contraseña incorrectos");
			}
		}
	}
	
	/**
	 * Cierra la sesion en la aplicacion
	 * @return la pagina de login
	 */
	public String cerrarSesion() {
		usuario = null;
		HttpSession sesion;
		sesion = (HttpSession) Faces.getSession();
		sesion.invalidate();
		return "/paginas/publico/login.xhtml?faces-redirect=true";
	}
	
	
	/**
	 * @return the accesos
	 */
	public List<Acceso> getAccesos() {
		return accesos;
	}

	/**
	 * @param accesos the accesos to set
	 */
	public void setAccesos(List<Acceso> accesos) {
		this.accesos = accesos;
	}

	/**
	 * @return the bd
	 */
	public int getBd() {
		return 1;
	}

	/**
	 * @param bd the bd to set
	 */
	public void setBd(int bd) {
		this.bd = bd;
	}	
}
