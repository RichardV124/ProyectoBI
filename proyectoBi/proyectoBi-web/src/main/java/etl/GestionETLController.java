package etl;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import beans.AuditoriaEJB;
import entidades.Auditoria;
import session.SessionController;

@ViewScoped
@Named("gestionETLController")
public class GestionETLController implements Serializable{
	
	public static String tipo;
	
	public static Date fechaInicio;
	
	public static Date fechaFin;

	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@Inject
	private SessionController sesion;
	
	@PostConstruct
	public void inicializar() {
		// TODO Auto-generated constructor stub	
	}
	
	public String iniciar(){
		//creando auditoria
		crearAuditoria("ETL",GestionETLController.tipo,"Extraccion", sesion.getBd());
		
		return "/paginas/seguro/administrador/etl/GestionExtraccion.xhtml?faces-redirect=true";
	}
	
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
}
