package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.LoginEJB;
import beans.UsuarioEJB;
import entidades.Usuario;
import session.SessionController;

@Named("loginController")
@ViewScoped
public class LoginController implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private UsuarioEJB usuarioEJB;
		
	private String username;
	
	private String password;
	
	private Usuario usuario;
	
	private List<Usuario> usuarios;

	@PostConstruct
	public void inicializar(){

	}
	
	/**
	 * Iniciar sesion
	 */
	public void login() {
		if(username.isEmpty() || password.isEmpty()){
			Messages.addFlashGlobalError("Por favor, ingrese todos los campos");
		}else{
			Usuario u = usuarioEJB.buscarPorUsername(username, 1);
			if(u != null){
				if(u.getLogin().getPassword().equals(password)){
					usuario = u;
					Faces.setSessionAttribute("usuario", usuario);
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
