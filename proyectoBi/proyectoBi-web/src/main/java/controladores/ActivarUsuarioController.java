package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.AuditoriaUsuarioEJB;
import beans.UsuarioEJB;
import entidades.AuditoriaUsuario;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@Named("ActivarUsuarioController")
@ViewScoped
public class ActivarUsuarioController implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private UsuarioEJB usuarioEJB;
	
	private List<Usuario> usuarios;
	
	@EJB
	private AuditoriaUsuarioEJB auditoriaEJB;
	
	
	private String usernameBuscar;
	
	@PostConstruct
	public void inicializar(){
		// TODO Auto-generated constructor stub
		
		listarUsuarios();
	}
	
	public void listarUsuarios(){
		try{
		usuarios = usuarioEJB.listarUsuariosInactivos(sesion.getBd());
	} catch (ExcepcionNegocio e) {
		Messages.addFlashGlobalInfo(e.getMessage());
	}
		}
	
	public void activarUsuario(Usuario u){
		try{
	u.getLogin().setActivo(true);
	usuarioEJB.editar(u, sesion.getBd());
	//creando la auditoria
	crearAuditoria(u.getCedula()+"", "Activar Usuario", 2);
	Messages.addFlashGlobalInfo("Se ha activado el usuario " +u.getLogin().getUsername());
	listarUsuarios();
	} catch (ExcepcionNegocio e) {
		Messages.addFlashGlobalInfo(e.getMessage());
	}
	}
	
	public void buscar(){
		Usuario usu = usuarioEJB.buscarPorUsername(usernameBuscar, sesion.getBd());
		
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getUsernameBuscar() {
		return usernameBuscar;
	}

	public void setUsernameBuscar(String usernameBuscar) {
		this.usernameBuscar = usernameBuscar;
	}
	
	public void crearAuditoria(String u, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		AuditoriaUsuario auditoria = new AuditoriaUsuario();
		auditoria.setEntidad("Usuario");
		auditoria.setObjetoAuditado(u);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}
	
	

}
