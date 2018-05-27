package etl;

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
import beansETL.TransformacionEJB;
import entidades.Auditoria;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionTransformacionController")
public class GestionTransformacionController implements Serializable{
	
	@EJB
	private TransformacionEJB transformacionEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@Inject
	private SessionController sesion;
	
	public static List<HechoAuditoria> listaAuditoriasTrans;
	
	public static List<HechoVenta> listaVentasTrans;
	
	@PostConstruct
	public void inicializar() {
		// TODO Auto-generated constructor stub
		llenarTablas();
	}
	
	public void llenarTablas() {
		try {
			listaAuditoriasTrans = transformacionEJB.transformarAuditorias(GestionExtraccionController.listaAuditorias);
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		try {
			listaVentasTrans = transformacionEJB.transformarVentas(GestionExtraccionController.listaVentas);
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public String cargar(){
		try{
			if(GestionETLController.tipo.equalsIgnoreCase("rolling")){
				transformacionEJB.cargar(listaAuditoriasTrans, listaVentasTrans);
			} else{
				transformacionEJB.cargarAcumulacionSimple(listaAuditoriasTrans, listaVentasTrans);
			}
			crearAuditoria("ETL",GestionETLController.tipo,"Carga", sesion.getBd());
			Messages.addFlashGlobalInfo("Carga exitosa");
			return "/paginas/seguro/administrador/etl/GestionETL.xhtml?faces-redirect=true";
		} catch(Exception e){
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		return null;
	}
	
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}

	public List<HechoAuditoria> getListaAuditoriasTrans() {
		return listaAuditoriasTrans;
	}

	public void setListaAuditoriasTrans(List<HechoAuditoria> listaAuditoriasTrans) {
		GestionTransformacionController.listaAuditoriasTrans = listaAuditoriasTrans;
	}

	public List<HechoVenta> getListaVentasTrans() {
		return listaVentasTrans;
	}

	public void setListaVentasTrans(List<HechoVenta> listaVentasTrans) {
		GestionTransformacionController.listaVentasTrans = listaVentasTrans;
	}


	

}
