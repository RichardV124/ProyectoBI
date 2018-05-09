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
import beans.TipoProductoEJB;
import entidades.Auditoria;
import entidades.Genero;
import entidades.Login;
import entidades.Municipio;
import entidades.TipoProducto;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@Named("GestionTipoProductoController")
@ViewScoped
public class GestionTipoProductoController  implements Serializable {
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private TipoProductoEJB tipoProductoEJB;
	
	@NotNull(message = "Debe ingresar su nombre")
	private String nombre;
	
	@NotNull(message = "Debe ingresar su descripcion0")
	private String descripcion;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	private List<TipoProducto> listaTipos;
	
	@PostConstruct
	public void inicializar(){

		listarTiposProducto();
	}
	
	public void listarTiposProducto(){
		try{
			listaTipos = tipoProductoEJB.listar(sesion.getBd());
		}catch(ExcepcionNegocio e){
			
		}
	}
	
	public void registrar() {

		try {

			TipoProducto tipo = new TipoProducto();
			
			tipo.setDescripcion(descripcion);
			tipo.setNombre(nombre);

			tipoProductoEJB.crear(tipo, sesion.getBd());

			//creando la auditoria
			crearAuditoria(tipo.getId()+"", "Crear", 2);
			
			//limpiamos campos
			limpiar();
			listarTiposProducto();
			Messages.addFlashGlobalInfo("Registro exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}

	}
	
	public void crearAuditoria(String u, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad("Tipo Producto");
		auditoria.setObjetoAuditado(u);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}
	
	public void buscar(){
		try{
			TipoProducto u = tipoProductoEJB.buscarPorNombre(nombre, sesion.getBd());
			descripcion=u.getDescripcion();
			nombre=u.getNombre();
			
		} catch (Exception e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void limpiar(){
		nombre="";
		descripcion="";
	}

	
	

	public void eliminar(TipoProducto t){
		tipoProductoEJB.eliminar(t, sesion.getBd());
		//creando la auditoria
		crearAuditoria(t.getId()+"", "Eliminar", 2);
		listarTiposProducto();
		
		Messages.addFlashGlobalInfo("Se ha eliminado correctamente");
		
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

	public List<TipoProducto> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<TipoProducto> listaTipos) {
		this.listaTipos = listaTipos;
	}
	
	

}
