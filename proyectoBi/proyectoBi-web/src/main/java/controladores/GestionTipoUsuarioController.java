package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.AuditoriaEJB;
import beans.TipoUsuarioEJB;
import entidades.Auditoria;
import entidades.Cargo;
import entidades.TipoUsuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionTipoUsuarioController")
public class GestionTipoUsuarioController implements Serializable {
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private TipoUsuarioEJB rolEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
		
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe a�adir una descripcion")
	private String descripcion;
	
	private int id;
	
	private List<TipoUsuario> tipoUsuarios;
	
	@PostConstruct
	public void inicializar(){
		llenarTabla();
	}
	
	public void registrar() {

		try {
			TipoUsuario u = new TipoUsuario();
			u.setNombre(nombre);
			u.setDescripcion(descripcion);

				//creando tipo del usuario

				rolEJB.crear(u, 2);
				llenarTabla();
				limpiar();
				Messages.addFlashGlobalInfo("Registro exitoso");
				//creando auditoria
				crearAuditoria("TipoUsuario",u.getId()+"","Crear", sesion.getBd());
			
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}

	}
	
		
	public void buscar(){
		try{
			
			TipoUsuario u = rolEJB.buscar(id, sesion.getBd());
			nombre=u.getNombre();
			descripcion = u.getDescripcion();
			//Creando auditoria
			crearAuditoria("TipoUsuario",u.getId()+"","Buscar", sesion.getBd());
			
			Messages.addFlashGlobalInfo("buscando");
		} catch (Exception e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void editar(){
		try {
			TipoUsuario u = rolEJB.buscar(id, sesion.getBd());
			u.setNombre(nombre);
			u.setDescripcion(descripcion);

			rolEJB.editar(u, sesion.getBd());
			limpiar();
			//Creando auditoria
			crearAuditoria("TipoUsuario",u.getId()+"","Editar", sesion.getBd());
			
			Messages.addFlashGlobalInfo("Edicion exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	//Meetodo para eliminar un tipo de usuario
	public void eliminar(){
		TipoUsuario c = new TipoUsuario();
		c.setId(id);
		c.setNombre(nombre);
		c.setDescripcion(descripcion);
		
		rolEJB.eliminar(c, sesion.getBd());
		limpiar();
		//creando la auditoria
		crearAuditoria("TipoUsuario",c.getId()+"", "Eliminar", 2);
		llenarTabla();
		Messages.addFlashGlobalInfo("Se ha eliminado correctamente");
		
	}
	
	
	/**
	 * Llenamos la tabla de tipos usuarios
	 */
	public void llenarTabla(){
		try{
			tipoUsuarios = rolEJB.listar(2);
		}catch(ExcepcionNegocio e){
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	/**
	 * Metodo para crear la auditoria
	 * @param entidad
	 * @param objeto
	 * @param accion
	 * @param bd
	 */
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}
	
	public void limpiar(){
		id = 0;
		nombre = "";
		descripcion = "";
	}

	public SessionController getSesion() {
		return sesion;
	}

	public void setSesion(SessionController sesion) {
		this.sesion = sesion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<TipoUsuario> getTipoUsuarios() {
		return tipoUsuarios;
	}

	public void setTipoUsuarios(List<TipoUsuario> tipoUsuarios) {
		this.tipoUsuarios = tipoUsuarios;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
