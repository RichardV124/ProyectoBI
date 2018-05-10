package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beans.TipoUsuarioEJB;
import entidades.AreaEmpresa;
import entidades.Genero;
import entidades.Login;
import entidades.Municipio;
import entidades.TipoUsuario;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionTipoUsuarioController")
public class GestionTipoUsuarioController implements Serializable {
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private TipoUsuarioEJB rolEJB;
	
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe añadir una descripcion")
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
			
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}

	}
	
	public void buscar(){
		try{
			
			TipoUsuario u = rolEJB.buscar(id, sesion.getBd());
			nombre=u.getNombre();
			descripcion = u.getDescripcion();
			
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
			
			Messages.addFlashGlobalInfo("Edicion exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
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
	
	public void limpiar(){
		
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
