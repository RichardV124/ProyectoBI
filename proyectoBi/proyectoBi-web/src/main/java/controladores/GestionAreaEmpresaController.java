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

import beans.AreaEmpresaEJB;
import beans.AuditoriaEJB;
import entidades.AreaEmpresa;
import entidades.Auditoria;
import entidades.Cargo;
import entidades.TipoUsuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionAreaEmpresaController")
public class GestionAreaEmpresaController implements Serializable {


	@Inject
	private SessionController sesion;
	
	@EJB
	private AreaEmpresaEJB areaEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe añadir una descripcion")
	private String descripcion;
	
	private int id;
	
	private List<AreaEmpresa> tipoAreas;
	
	@PostConstruct
	public void inicializar(){
		llenarTabla();
	}
	
	public void registrar() {

		try {
			AreaEmpresa u = new AreaEmpresa();
			u.setNombre(nombre);
			u.setDescripcion(descripcion);

				//creando tipo del usuario

				areaEJB.crear(u, sesion.getBd());
				llenarTabla();
				limpiar();
				//Creando auditoria
				crearAuditoria("AreaEmpresa",u.getId()+"","Crear", sesion.getBd());
				Messages.addFlashGlobalInfo("Registro exitoso");
			
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		

	}
	
	public void buscar(){
		try{
			
			AreaEmpresa u = areaEJB.buscar(id, sesion.getBd());
			nombre=u.getNombre();
			descripcion = u.getDescripcion();

			//Creando auditoria
			crearAuditoria("AreaEmpresa",u.getId()+"","Buscar", sesion.getBd());
			
		} catch (Exception e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void editar(){
		try {
			AreaEmpresa u = areaEJB.buscar(id, sesion.getBd());
			u.setNombre(nombre);
			u.setDescripcion(descripcion);

			areaEJB.editar(u, sesion.getBd());
			limpiar();
			//Creando auditoria
			crearAuditoria("AreaEmpresa",u.getId()+"","Editar", sesion.getBd());
			
			Messages.addFlashGlobalInfo("Edicion exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	//Meetodo para eliminar un cargo 
		public void eliminar(){
			AreaEmpresa a = new AreaEmpresa();
			a.setId(id);
			a.setNombre(nombre);
			a.setDescripcion(descripcion);
			
			areaEJB.eliminar(a, sesion.getBd());
			limpiar();
			//creando la auditoria
			crearAuditoria("AreaEmpresa",a.getId()+"", "Eliminar", 2);
			llenarTabla();
			Messages.addFlashGlobalInfo("Se ha eliminado correctamente");
			
		}
	
	
	/**
	 * Llenamos la tabla de tipos usuarios
	 */
	public void llenarTabla(){
		try{
			tipoAreas = areaEJB.listarUsuarios(sesion.getBd());
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
		id =0;
		nombre = "";
		descripcion = "";
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

	public List<AreaEmpresa> getTipoAreas() {
		return tipoAreas;
	}

	public void setTipoAreas(List<AreaEmpresa> tipoAreas) {
		this.tipoAreas = tipoAreas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	
	
}
