package controladores;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beans.ConexionEJB;
import beans.TipoUsuarioEJB;
import entidades.Conexion;
import entidades.TipoUsuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionBdController")
public class GestionBdController {
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private ConexionEJB bdEJB;
	
	private int bdSeleccionado;
		
	private List<Conexion> listabd;
	
	@PostConstruct
	public void inicializar(){
		llenarCombo();
	}
	
	public void llenarCombo(){
		
		try{
			listabd = bdEJB.listar(sesion.getBd());
		}catch(ExcepcionNegocio e){
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
	}
	
	
	

	public int getBdSeleccionado() {
		return bdSeleccionado;
	}

	public void setBdSeleccionado(int bdSeleccionado) {
		this.bdSeleccionado = bdSeleccionado;
	}

	public List<Conexion> getListabd() {
		return listabd;
	}

	public void setListabd(List<Conexion> listabd) {
		this.listabd = listabd;
	}
	
	
	

}
