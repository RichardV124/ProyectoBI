package controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.AuditoriaEJB;
import beans.ProductoEJB;
import beans.TipoProductoEJB;
import entidades.Producto;
import entidades.TipoProducto;
import excepciones.ExcepcionNegocio;
import entidades.Auditoria;
import entidades.Lote;
import session.SessionController;

@ViewScoped
@Named("gestionProductoController")
public class GestionProductoController implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	private int codigo;
	private String nombre;
	private String descripcion;
	private double peso;
	private String dimension;
	private int inventarioSeleccionado;
	private double valor;
	private int cantidad;
	private List<Lote> lotes;
	private int loteSeleccionado;
	private List<Producto> productos;
	private List<TipoProducto> tiposProducto;
	private Producto productoBuscado;
	private int tipoProductoSeleccionado;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;

	@EJB
	private ProductoEJB productoEJB;
	
	
	@EJB
	private TipoProductoEJB tipoProductoEJB;
	
	@PostConstruct
	private void inicializar() {

		lotes = productoEJB.lotes(sesion.getBd());
		listarTiposProducto();
		listarProductos();
	}
	
	/**
	 * Lista los tipos de producto registrados
	 */
	private void listarTiposProducto() {
		tiposProducto = tipoProductoEJB.listar(sesion.getBd());
	}
	
	/**
	 * Lista los productos registrados en la base de datos
	 */
	public void listarProductos() {
		productos = productoEJB.listarProductos(sesion.getBd());
	}
	
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}
	
	/**
	 * Busca un producto por su codigo
	 */
	public void buscar() {

		productoBuscado = productoEJB.buscarProducto(codigo,sesion.getBd());

		if (productoBuscado != null) {

			try {

				//creando auditoria
				crearAuditoria("Producto",productoBuscado+"","Editar", sesion.getBd());

			} catch (Exception e) {
				e.printStackTrace();
			}

			codigo = productoBuscado.getId();
			nombre = productoBuscado.getNombre();
			descripcion = productoBuscado.getDescripcion();
			peso = productoBuscado.getPeso();
			dimension = productoBuscado.getDimensiones();
			valor = productoBuscado.getValor();
			loteSeleccionado = productoBuscado.getLote().getId();

		} else {

			Messages.addFlashGlobalInfo("El producto que intenta buscar no esta registrado");

		}

	}
	
	private boolean validarCampos() {
		if (dimension.equals("") || peso <= 0 || nombre.equals("") || valor <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Edita un producto previamente buscado
	 */
	public void editar() {

		if (productoBuscado == null) {
			Messages.addFlashGlobalInfo("Debe buscar un producto previamente");
		} else {

			if (validarCampos()|| codigo>0) {

				productoBuscado.setDescripcion(descripcion);
				productoBuscado.setNombre(nombre);
				productoBuscado.setDimensiones(dimension);
				productoBuscado.setLote(productoEJB.buscarloteProducto(loteSeleccionado,sesion.getBd()));
				productoBuscado.setValor(valor);
				productoBuscado.setPeso(peso);
				productoBuscado.setCantidad(cantidad);

				productoEJB.editarProducto(productoBuscado,sesion.getBd());

				try {

					//creando auditoria
					crearAuditoria("Producto",productoBuscado+"","Editar", sesion.getBd());

				} catch (Exception e) {
					e.printStackTrace();
				}

				Messages.addFlashGlobalInfo("Se ha editado el producto correctamente");

				limpiarCampos();
				listarProductos();

			} else {
				Messages.addFlashGlobalInfo("Debe ingresar todos los campos");
			}
		}

	}

	/**
	 * Limpia los campos del producto
	 */
	private void limpiarCampos() {
		productoBuscado = null;
		dimension = "";
		codigo = 0;
		nombre = "";
		descripcion = "";
		valor = 0;
		peso = 0;
		cantidad =0;
	}
	
	
	/**
	 * Registra el producto
	 */
	public void registrar() {

		if (validarCampos()||codigo>0) {

			Producto producto = new Producto();
			producto.setId(codigo);
			producto.setDimensiones(dimension);
			producto.setFechaIngreso(new Date());
			producto.setPeso(peso);
			producto.setNombre(nombre);
			producto.setDescripcion(descripcion);
			producto.setValor(valor);
			producto.setCantidad(cantidad);
			producto.setLote(productoEJB.buscarloteProducto(loteSeleccionado,sesion.getBd()));
			producto.setUsuario(sesion.getUsuario());

			TipoProducto tipoProducto = tipoProductoEJB.buscar(tipoProductoSeleccionado,sesion.getBd());

			producto.setTipoProducto(tipoProducto);

			try {

				productoEJB.registrarProducto(producto,sesion.getBd());

				//creando auditoria
				crearAuditoria("Producto",producto.getId()+"","Crear", sesion.getBd());

			} catch (ExcepcionNegocio e) {
				// TODO: handle exception
				Messages.addFlashGlobalError(e.getMessage());

			} catch (Exception e) {
				e.printStackTrace();
			}

			Messages.addFlashGlobalError("El producto se ha registrado correctamente");
			limpiarCampos();
			listarProductos();

		} else {
			Messages.addFlashGlobalInfo("Debe ingresar todos los campos");
		}

	}
	
	/**
	 * Elimina un producto
	 * 
	 * @param p
	 *            producto que se desea eliminar
	 */
	public void eliminarProducto(Producto p) {
		productos.remove(p);
		productoEJB.eliminarProducto(p,sesion.getBd());

		try {

			//creando auditoria
			crearAuditoria("Producto",p.getId()+"","Eliminar", sesion.getBd());

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(ArrayList<Lote> lotes) {
		this.lotes = lotes;
	}

	public int getLoteSeleccionado() {
		return loteSeleccionado;
	}

	public void setLoteSeleccionado(int loteSeleccionado) {
		this.loteSeleccionado = loteSeleccionado;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<TipoProducto> getTiposProducto() {
		return tiposProducto;
	}

	public void setTiposProducto(List<TipoProducto> tiposProducto) {
		this.tiposProducto = tiposProducto;
	}

	public int getTipoProductoSeleccionado() {
		return tipoProductoSeleccionado;
	}

	public void setTipoProductoSeleccionado(int tipoProductoSeleccionado) {
		this.tipoProductoSeleccionado = tipoProductoSeleccionado;
	}

	public int getInventarioSeleccionado() {
		return inventarioSeleccionado;
	}

	public void setInventarioSeleccionado(int inventarioSeleccionado) {
		this.inventarioSeleccionado = inventarioSeleccionado;
	}

	public SessionController getSesion() {
		return sesion;
	}

	public void setSesion(SessionController sesion) {
		this.sesion = sesion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProductoBuscado() {
		return productoBuscado;
	}

	public void setProductoBuscado(Producto productoBuscado) {
		this.productoBuscado = productoBuscado;
	}

	public AuditoriaEJB getAuditoriaEJB() {
		return auditoriaEJB;
	}

	public void setAuditoriaEJB(AuditoriaEJB auditoriaEJB) {
		this.auditoriaEJB = auditoriaEJB;
	}

	public ProductoEJB getProductoEJB() {
		return productoEJB;
	}

	public void setProductoEJB(ProductoEJB productoEJB) {
		this.productoEJB = productoEJB;
	}

	public TipoProductoEJB getTipoProductoEJB() {
		return tipoProductoEJB;
	}

	public void setTipoProductoEJB(TipoProductoEJB tipoProductoEJB) {
		this.tipoProductoEJB = tipoProductoEJB;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}

	
	
}
