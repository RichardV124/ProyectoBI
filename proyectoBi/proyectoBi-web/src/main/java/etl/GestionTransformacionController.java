package etl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beansETL.TransformacionEJB;
import excepciones.ExcepcionNegocio;

@ViewScoped
@Named("gestionTransformacionController")
public class GestionTransformacionController implements Serializable{
	
	@EJB
	private TransformacionEJB transformacionEJB;
	
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
