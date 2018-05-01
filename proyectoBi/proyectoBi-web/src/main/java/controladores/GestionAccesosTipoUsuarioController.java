package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beans.AccesoEJB;
import beans.AccesoTipoUsuarioEJB;
import beans.TipoUsuarioEJB;
import entidades.Acceso;
import entidades.AccesoTipoUsuario;
import entidades.TipoUsuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@Named("GestionAccesoTipoUsuarioController")
@ViewScoped
public class GestionAccesosTipoUsuarioController implements Serializable {

	@Inject
	private SessionController sesion;

	@EJB
	private AccesoTipoUsuarioEJB accesoTipoUsuarioEJB;

	@EJB
	private TipoUsuarioEJB rolEJB;

	@EJB
	private AccesoEJB accesoEJB;

	private List<TipoUsuario> roles;

	private List<Acceso> accesos;

	private List<AccesoTipoUsuario> accesosTipoUsuario;

	private int accesoSeleccionado;

	private int rolSeleccionado;

	@PostConstruct
	public void inicializar() {
		System.out.println("BASE DE DATOS " + sesion.getBd() + " AKKK");
		listar();
	}

	/**
	 * Listar en los combobox
	 */
	public void listar() {
		try {
			listarRoles();
			listarAccesos();
			listarAccesosTipoUsuario();
			System.out.println("CANT: "+accesosTipoUsuario.size());
			System.out.println("PRIMERO: "+accesosTipoUsuario.get(0));
		} catch (ExcepcionNegocio e) {

		}
	}

	public void listarRoles() {
		roles = rolEJB.listar(sesion.getBd());
	}

	public void listarAccesos() {
		accesos = accesoEJB.listar(sesion.getBd());
	}

	/**
	 * Otorga el acceso a un tipo de usuario
	 */
	public void asignar() {
		try {
			System.out.println("BD: " +sesion.getBd());
			System.out.println("ROL SELECCIONADO: " +rolSeleccionado);
			System.out.println("ACCESO SELECCIONADO: " +accesoSeleccionado);
			TipoUsuario rol = rolEJB.buscar(rolSeleccionado, sesion.getBd());
			Acceso acceso= accesoEJB.buscar(accesoSeleccionado, sesion.getBd());
			if (rol != null && acceso != null) {
				AccesoTipoUsuario rolAcceso = new AccesoTipoUsuario(rol, acceso);
				accesoTipoUsuarioEJB.crear(rolAcceso, sesion.getBd());
				Messages.addFlashGlobalInfo(
						"Se ha asignado el acceso " + acceso.getNombre() + " a el tipo de usuario " + rol.getNombre());
				// Guardamos en la auditoria
				listarAccesosTipoUsuario();
			} else {
				Messages.addFlashGlobalInfo("Seleccione el rol y el acceso");
			}
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalWarn(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void quitar(AccesoTipoUsuario ac) {
		accesoTipoUsuarioEJB.eliminar(tipoUsuarioAcceso, bd);

	}

	/**
	 * Lista de accesosRol de un determinado rol
	 */
	public void listarAccesosTipoUsuario() {
		accesosTipoUsuario = accesoTipoUsuarioEJB.listar(sesion.getBd());
	}

	public List<TipoUsuario> getRoles() {
		return roles;
	}

	public void setRoles(List<TipoUsuario> roles) {
		this.roles = roles;
	}


	public List<Acceso> getAccesos() {
		return accesos;
	}

	public void setAccesos(List<Acceso> accesos) {
		this.accesos = accesos;
	}



	public List<AccesoTipoUsuario> getAccesosTipoUsuario() {
		return accesosTipoUsuario;
	}

	public void setAccesosTipoUsuario(List<AccesoTipoUsuario> accesosTipoUsuario) {
		this.accesosTipoUsuario = accesosTipoUsuario;
	}

	public int getAccesoSeleccionado() {
		return accesoSeleccionado;
	}

	public void setAccesoSeleccionado(int accesoSeleccionado) {
		this.accesoSeleccionado = accesoSeleccionado;
	}

	public int getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(int rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

}
