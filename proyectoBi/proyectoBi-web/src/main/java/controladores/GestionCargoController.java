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
import beans.CargoEJB;
import entidades.Auditoria;
import entidades.Cargo;
import entidades.TipoProducto;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionCargoController")
public class GestionCargoController implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@EJB
	private CargoEJB cargoEJB;
	
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe añadir una descripcion")
	private String descripcion;
	
	private int id;
	
	private List<Cargo> tipoCargo;
	
	@PostConstruct
	public void inicializar(){
		llenarTabla();
	}
	
	public void registrar() {

		try {
			Cargo u = new Cargo();
			u.setNombre(nombre);
			u.setDescripcion(descripcion);

				//creando tipo del usuario

				cargoEJB.crear(u, sesion.getBd());
				llenarTabla();
				limpiar();
				//Creando auditoria
				crearAuditoria("Cargo",u.getId()+"","Crear", sesion.getBd());
				Messages.addFlashGlobalInfo("Registro exitoso");
			
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		

	}
	
	public void buscar(){
		try{
			
			Cargo u = cargoEJB.buscar(id, sesion.getBd());
			nombre=u.getNombre();
			descripcion = u.getDescripcion();

			//Creando auditoria
			crearAuditoria("Cargo",u.getId()+"","Buscar", sesion.getBd());
			
		} catch (Exception e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void editar(){
		try {
			Cargo u = cargoEJB.buscar(id, sesion.getBd());
			u.setNombre(nombre);
			u.setDescripcion(descripcion);

			cargoEJB.editar(u, sesion.getBd());
			limpiar();
			llenarTabla();
			//Creando auditoria
			crearAuditoria("Cargo",u.getId()+"","Editar", sesion.getBd());
			
			Messages.addFlashGlobalInfo("Edicion exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	//Meetodo para eliminar un cargo 
	public void eliminar(){
		Cargo c = new Cargo();
		c.setId(id);
		c.setNombre(nombre);
		c.setDescripcion(descripcion);
		
		cargoEJB.eliminar(c, sesion.getBd());
		limpiar();
		//creando la auditoria
		crearAuditoria("Cargo",c.getId()+"", "Eliminar", 2);
		llenarTabla();
		Messages.addFlashGlobalInfo("Se ha eliminado correctamente");
		
	}
	
	/**
	 * Llenamos la tabla de tipos usuarios
	 */
	public void llenarTabla(){
		try{
			tipoCargo = cargoEJB.listar(sesion.getBd());
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

	public List<Cargo> getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoAreas(List<Cargo> tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
