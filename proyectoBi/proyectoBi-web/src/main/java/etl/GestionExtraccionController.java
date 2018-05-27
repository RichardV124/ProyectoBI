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
import beansETL.ExtracionEJB;
import entidades.Auditoria;
import entidades.DetalleVenta;
import entidades.Venta;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionExtraccionController")
public class GestionExtraccionController implements Serializable {


	@EJB
	private ExtracionEJB extracionEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@Inject
	private SessionController sesion;

	public static List<Auditoria> listaAuditorias;
	
	public static List<DetalleVenta> listaVentas;


	@PostConstruct
	public void inicializar() {
		// TODO Auto-generated constructor stub
		System.out.println(GestionETLController.tipo + "YOOOOOOOOOOOOOOOOOOOOOOOOOOLO");
		llenarTablas();
		
	}

	public void llenarTablas() {
		if(GestionETLController.tipo.equalsIgnoreCase("rolling")){
			try {
				listaAuditorias = extracionEJB.listarAuditorias();
			} catch (ExcepcionNegocio e) {
				Messages.addFlashGlobalInfo(e.getMessage());
			}
			
			try {
				listaVentas = extracionEJB.listarVentas();
			} catch (ExcepcionNegocio e) {
				Messages.addFlashGlobalInfo(e.getMessage());
			}
		} else{
			try {
				listaAuditorias = extracionEJB.listarAuditoriasFecha(GestionETLController.fechaInicio, GestionETLController.fechaFin);
			} catch (ExcepcionNegocio e) {
				Messages.addFlashGlobalInfo(e.getMessage());
			}
			
			try {
				listaVentas = extracionEJB.listarVentasFecha(GestionETLController.fechaInicio, GestionETLController.fechaFin);
			} catch (ExcepcionNegocio e) {
				Messages.addFlashGlobalInfo(e.getMessage());
			}
		}
	}
	
	public String transformar(){
		//creando auditoria
		crearAuditoria("ETL",GestionETLController.tipo,"Transformacion", sesion.getBd());
		
		return "/paginas/seguro/administrador/etl/GestionTransformacion.xhtml?faces-redirect=true";
	}
	
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}

	public List<Auditoria> getListaAuditorias() {
		return listaAuditorias;
	}

	public void setListaAuditorias(List<Auditoria> listaAuditorias) {
		this.listaAuditorias = listaAuditorias;
	}

	public List<DetalleVenta> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(List<DetalleVenta> listaVentas) {
		GestionExtraccionController.listaVentas = listaVentas;
	}
	
	
}
