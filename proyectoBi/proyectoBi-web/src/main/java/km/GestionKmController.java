package km;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import BeansKM.GestionKmEJB;

@ViewScoped
@Named("gestionKmController")
public class GestionKmController implements Serializable{
	
	@EJB
	private GestionKmEJB gestionKmEJB;

	@PostConstruct
	public void inicializar() {
		// TODO Auto-generated constructor stub	
	}
	
	public void gestionar(){
		try{
			gestionKmEJB.guardarUsers();
			Messages.addFlashGlobalInfo("Proceso ETL de la GEstion del conocieminto Exitoso");
		} catch(Exception e){
			Messages.addFlashGlobalInfo("Problema en el Proceso ETL");
		}
	}
}
