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

import beans.AreaEmpresaEJB;
import entidades.AreaEmpresa;
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
	
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe añadir una descripcion")
	private String descripcion;
	
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
				Messages.addFlashGlobalInfo("Registro exitoso");
			
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}

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
	
	public void limpiar(){
		
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

	
	
}
