package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beans.AuditoriaEJB;
import entidades.Auditoria;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionAuditoriaController")
public class GestionAuditoriaController implements Serializable {

	@Inject
	private SessionController sesion;

	@EJB
	private AuditoriaEJB auditoriaEJB;

	private List<Auditoria> audi_1;

	private List<Auditoria> audi_2;

	private List<Auditoria> audi_3;

	private List<Auditoria> audi_4;

	private List<Auditoria> audi_5;

	private List<Auditoria> audi_6;

	private List<Auditoria> audi_7;

	@PostConstruct
	public void inicializar() {
		// TODO Auto-generated constructor stub
		llenarTablas();
	}

	public void llenarTablas() {
		try {
			audi_1 = auditoriaEJB.listarParametro(sesion.getBd(), "ETL");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		try {
			audi_2 = auditoriaEJB.listarParametro(sesion.getBd(), "Analisis");
			audi_2 = auditoriaEJB.listarParametro(sesion.getBd(), "HechoVenta");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		try {
			audi_3 = auditoriaEJB.listarParametro(sesion.getBd(), "Login");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		try {
			audi_4 = auditoriaEJB.listarParametro(sesion.getBd(), "Usuario");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		try {
			audi_5 = auditoriaEJB.listarParametro(sesion.getBd(), "AreaEmpresa");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		try {
			audi_6 = auditoriaEJB.listarParametro(sesion.getBd(), "Producto");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		try {
			audi_7 = auditoriaEJB.listarParametro(sesion.getBd(), "Conexion");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}

	public List<Auditoria> getAudi_1() {
		return audi_1;
	}

	public void setAudi_1(List<Auditoria> audi_1) {
		this.audi_1 = audi_1;
	}

	public List<Auditoria> getAudi_2() {
		return audi_2;
	}

	public void setAudi_2(List<Auditoria> audi_2) {
		this.audi_2 = audi_2;
	}

	public List<Auditoria> getAudi_3() {
		return audi_3;
	}

	public void setAudi_3(List<Auditoria> audi_3) {
		this.audi_3 = audi_3;
	}

	public List<Auditoria> getAudi_4() {
		return audi_4;
	}

	public void setAudi_4(List<Auditoria> audi_4) {
		this.audi_4 = audi_4;
	}

	public List<Auditoria> getAudi_5() {
		return audi_5;
	}

	public void setAudi_5(List<Auditoria> audi_5) {
		this.audi_5 = audi_5;
	}

	public List<Auditoria> getAudi_6() {
		return audi_6;
	}

	public void setAudi_6(List<Auditoria> audi_6) {
		this.audi_6 = audi_6;
	}

	public List<Auditoria> getAudi_7() {
		return audi_7;
	}

	public void setAudi_7(List<Auditoria> audi_7) {
		this.audi_7 = audi_7;
	}

}
