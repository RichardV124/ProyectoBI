package etl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beans.AuditoriaEJB;
import beansETL.ExtracionEJB;
import entidades.Auditoria;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionExtraccionController")
public class GestionExtraccionController implements Serializable {

	@Inject
	private SessionController sesion;

	@EJB
	private ExtracionEJB extracionEJB;

	public static List<Auditoria> listaAuditorias;


	@PostConstruct
	public void inicializar() {
		// TODO Auto-generated constructor stub
		llenarTablas();
	}

	public void llenarTablas() {
		try {
			listaAuditorias = extracionEJB.listarAuditorias();
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}

	public List<Auditoria> getListaAuditorias() {
		return listaAuditorias;
	}

	public void setListaAuditorias(List<Auditoria> listaAuditorias) {
		this.listaAuditorias = listaAuditorias;
	}

	
}
