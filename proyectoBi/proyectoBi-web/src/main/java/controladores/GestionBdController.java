package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beans.ConexionEJB;
import entidades.Conexion;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionBdController")
public class GestionBdController implements Serializable {

	@Inject
	private SessionController sesion;

	@EJB
	private ConexionEJB bdEJB;

	private int bdSeleccionado;

	private List<Conexion> listabd;

	@PostConstruct
	public void inicializar() {
		llenarCombo();
	}

	public void llenarCombo() {

		try {
			listabd = bdEJB.listar(2);

		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}

	}

	public void aceptar() {
		
		if(sesion.getBd()!=bdSeleccionado){
		sesion.setBd(bdSeleccionado);
		System.out.println(bdSeleccionado+"ASDAJSDKADSJKJ");
		Conexion con = bdEJB.buscar(bdSeleccionado, 2);
		bdEJB.editar(con, 2);
		Messages.addFlashGlobalInfo("Se ha cambiado la base de datos correctamente");
		}else{
			Messages.addFlashGlobalInfo("El sistema ya esta trabajando en esta base de datos");
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
