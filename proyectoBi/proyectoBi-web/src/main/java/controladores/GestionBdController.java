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

import beans.AuditoriaEJB;
import beans.ConexionEJB;
import entidades.Auditoria;
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

	private int conexionSeleccionada;

	private List<Conexion> listabd;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;

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

		System.out.println(" COMBO: " + conexionSeleccionada);

		Conexion conNueva = bdEJB.buscar(conexionSeleccionada, 2);
		if (conNueva !=null) {
			if(sesion.getBd() != conexionSeleccionada){

				// Cambiamos el estado ( inactivo ) a la bd anterior
				Conexion conVieja = bdEJB.buscar(sesion.getBd(), 2);
				conVieja.setActivo(false);

				System.out.println(conVieja.getNombre() + " ---- ID :" + conVieja.getId());

				// Cambiamos el estado ( activo ) a la bd seleccionada
				conNueva.setActivo(true);

				System.out.println(conNueva.getNombre() + " ---- ID :" + conNueva.getId());

				bdEJB.editar(conNueva, 2);
				bdEJB.editar(conVieja, 2);
								
				//creando auditoria
				crearAuditoria("Conexion",conVieja.getId()+"","Editar", sesion.getBd());
				
				sesion.setBd(conexionSeleccionada);
				
				Messages.addFlashGlobalInfo("Se ha cambiado la base de datos correctamente");
			} else {
				Messages.addFlashGlobalInfo("El sistema ya esta trabajando en esta base de datos");
			}
		}else{
			Messages.addFlashGlobalInfo("Debe seleccionar una bd");
		}

	}
	
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}



	public int getConexionSeleccionada() {
		return conexionSeleccionada;
	}

	public void setConexionSeleccionada(int conexionSeleccionada) {
		this.conexionSeleccionada = conexionSeleccionada;
	}

	public List<Conexion> getListabd() {
		return listabd;
	}

	public void setListabd(List<Conexion> listabd) {
		this.listabd = listabd;
	}

}
