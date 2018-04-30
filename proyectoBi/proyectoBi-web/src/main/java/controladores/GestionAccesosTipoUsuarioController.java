package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import beans.AccesoEJB;
import beans.AccesoTipoUsuarioEJB;
import beans.TipoUsuarioEJB;
import entidades.Acceso;
import entidades.AccesoTipoUsuario;
import entidades.TipoUsuario;
import session.SessionController;

@Named("gestionAccesosRol")
@ViewScoped
public class GestionAccesosTipoUsuarioController implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private AccesoTipoUsuarioEJB accesoTipoUsuarioEJB;

	@EJB
	private TipoUsuarioEJB rolEJB;
	
	@EJB
	private AccesoEJB accesoEJB;
	
	private List<TipoUsuario> roles;
	
	private TipoUsuario rol;
	
	private List<Acceso> accesos;
	
	private Acceso acceso;
	
	private List<AccesoTipoUsuario> accesosTipoUsuario;
	
	
	@PostConstruct
	public void inicializar(){
		listar();
	}
	
	
	/**
	 * Listar en los combobox
	 */
	public void listar(){
		roles = rolEJB.listar(sesion.getBd());
		accesos = accesoEJB.listar(sesion.getBd());
		accesosTipoUsuario = accesoTipoUsuarioEJB.listarAccesosTipoPorTipo(roles.get(0), sesion.getBd());
	}

}
