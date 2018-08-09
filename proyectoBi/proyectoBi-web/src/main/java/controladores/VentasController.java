package controladores;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.AuditoriaEJB;
import beans.DepartamentoEJB;
import beans.DetalleVentaEJB;
import beans.GeneroEJB;
import beans.MunicipioEJB;
import beans.ProductoEJB;
import beans.UsuarioEJB;
import beans.VentaEJB;
import entidades.Auditoria;
import entidades.Cliente;
import entidades.Departamento;
import entidades.DetalleVenta;
import entidades.Genero;
import entidades.Municipio;
import entidades.Producto;
import entidades.Usuario;
import entidades.Venta;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("ventasController")
public class VentasController implements Serializable {

	@Inject
	private SessionController sesion;

	@EJB
	private ProductoEJB productoEJB;

	@EJB
	private AuditoriaEJB auditoriaEJB;

	@EJB
	private UsuarioEJB usuarioEJB;

	@EJB
	private VentaEJB ventaEJB;

	@EJB
	private DepartamentoEJB departamentoEJB;

	@EJB
	private MunicipioEJB municipioEJB;
	
	@EJB
	private GeneroEJB generoEJB;
	
	@EJB
	private DetalleVentaEJB detalleEJB;

	private List<Producto> productos;

	private DetalleVenta detalleAgregar;

	private List<Producto> inventariosEditar;

	// Cliente que va comprar
	private Cliente cliente;
	private boolean mostrarDatosCliente;

	// Datos venta
	private int cantidadAgregar =1;
	private List<DetalleVenta> productosCompra;
	private Producto inventarioProductoComprar;
	double totalVenta = 0;
	boolean agregado = false;
	Venta factura;

	// Datos cliente
	private String cedula;
	private String nombre;
	private String apellido;
	private String correo;
	private String telefono;
	private int tipoGenero;
	private int deptoSeleccionado;
	private int municipioSeleccionado;
	private List<Genero> generos;
	private String accion;
	private Date fechaNacimiento;
	private Usuario usuario;

	private List<Departamento> departamentos;
	private List<Municipio> municipios;

	@PostConstruct
	private void inicializar() {

		listarProductos();
		generos = generoEJB.listar(sesion.getBd());
		productosCompra = new ArrayList<DetalleVenta>();
		inventariosEditar = new ArrayList<Producto>();
		listarDepartamentos();
		listarMunicipios();
	}

	/**
	 * Lista los departamentos registrados
	 */
	private void listarDepartamentos() {
		departamentos = departamentoEJB.listar(sesion.getBd());
	}
	


	/**
	 * Lista los muncipios del departamento seleccionado
	 */
	public void listarMunicipios() {
		municipios = municipioEJB.listar(sesion.getBd(), departamentos.get(0));
	}

	/**
	 * Lista los productos registrados en la base de datos
	 */
	public void listarProductos() {
		productos = productoEJB.listarProductos(sesion.getBd());
	}

	/**
	 * Se crea un detalle venta que ser� agregado al carrito
	 * 
	 * @param p
	 *            InventarioProducto que se desea comprar
	 */
	public void crearDetalleVenta(Producto p) {
		detalleAgregar = new DetalleVenta();
		detalleAgregar.setProducto(p);
		if(detalleAgregar!=null){
			agregado = true;
		}
		inventarioProductoComprar = p;
		//reload();
	}

	/**
	 * Se agrega la cantidad del producto que se desea vender
	 */
	public void agregarCantidad(Producto p) {
		crearDetalleVenta(p);
		if (cantidadAgregar > 0) {
			if (cantidadAgregar <= inventarioProductoComprar.getCantidad()) {
				detalleAgregar.setCantidad(cantidadAgregar);
				inventarioProductoComprar.setCantidad(inventarioProductoComprar.getCantidad() - cantidadAgregar);
				sumarTotalVenta(cantidadAgregar, detalleAgregar.getProducto().getValor());
				System.out.println("CANTIDAD:"+ detalleAgregar.getCantidad());
				System.out.println("BOOLEAN:" + detalleAgregar==null);
				productosCompra.add(detalleAgregar);
				inventariosEditar.add(inventarioProductoComprar);
				detalleAgregar = null;
				reload();

			} else {
				Messages.addFlashGlobalInfo("No existe esta cantidad en el inventario");
			}
		} else {
			System.out.println(" DEBE SER MAYOR LA CANTIDAD");
			Messages.addFlashGlobalInfo("La cantidad debe ser mayor a 0");
		}
	}

	

	/**
	 * Se suma al total de la venta el nuevo producto que se desea agregar
	 * 
	 * @param cantidad
	 *            cantidad del mismo producto que se desea agregar
	 * @param precioProducto
	 *            precio del producto
	 */
	private void sumarTotalVenta(int cantidad, double precioProducto) {
		double valorProductos = cantidad * precioProducto;
		totalVenta += valorProductos;
	}

	/**
	 * Elimina un detalle venta de la lista
	 * 
	 * @param dv
	 *            detalle venta a eliminar
	 */
	public void eliminarDetalleVenta(DetalleVenta dv) {
		restarTotalVenta(dv);
		inventariosEditar.remove(inventarioProductoComprar);
		inventarioProductoComprar.setCantidad(inventarioProductoComprar.getCantidad() + dv.getCantidad());
		productosCompra.remove(dv);

	}

	public void crearAuditoria(String entidad, String objeto, String accion, int bd) {
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}

	/**
	 * Le resta al total de la venta el detalle venta que se desea eliminar
	 * 
	 * @param dv
	 *            detalle de venta que se desea eliminar
	 */
	private void restarTotalVenta(DetalleVenta dv) {
		int cantidad = dv.getCantidad();
		double precioProducto = dv.getProducto().getValor();
		totalVenta -= cantidad * precioProducto;
	}

	/**
	 * Busca un cliente
	 */
	public void buscarCliente() {

		cliente = usuarioEJB.buscarCliente(cedula, sesion.getBd());


		if (cliente != null) {

			try {

				// creando auditoria
				crearAuditoria("Cliente", cliente.getCedula(), "Buscar", sesion.getBd());

			} catch (Exception e) {
				e.printStackTrace();
			}

			nombre = cliente.getNombre();
			apellido = cliente.getApellido();
			correo = cliente.getCorreo();
			telefono = cliente.getTelefono();
			deptoSeleccionado = cliente.getMunicipio().getDepartamento().getId();

			municipioSeleccionado = cliente.getMunicipio().getId();
			tipoGenero=generoEJB.buscar(sesion.getBd(), cliente.getGenero().getId()).getId();
			fechaNacimiento = cliente.getFechaNacimiento();

		} else {

			limpiarCampos();

			Messages.addFlashGlobalInfo(
					"El cliente no se encuentra registrado, debe registrarlo para continuar con la venta");

		}
		System.out.println("BOOLEAN:"+mostrarDatosCliente);

	}
	
	private void reload() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void limpiarCampos() {

		nombre = "";
		apellido = "";
		correo = "";
		telefono = "";
		totalVenta = 0;
		cliente = null;

	}
	
	/**
	 * Valida que se hayan ingresado todos los campos
	 * 
	 * @return true si se llenaron los campos, de lo contrario false
	 */
	private boolean validarCampos() {
		if (cedula.equals("") || nombre.equals("") || apellido.equals("") || telefono.equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Registra un cliente
	 */
	public void registrarCliente() {

		if (validarCampos()) {

			cliente = new Cliente();
			cliente.setApellido(apellido);
			cliente.setNombre(nombre);
			cliente.setCedula(cedula);
			cliente.setCorreo(correo);
			cliente.setTelefono(telefono);
			
			Genero gen = generoEJB.buscar(sesion.getBd(), tipoGenero);
			cliente.setGenero(gen);
			cliente.setFechaNacimiento(fechaNacimiento);

			Municipio mun = municipioEJB.buscar(sesion.getBd(), municipioSeleccionado);
			cliente.setMunicipio(mun);

			try {

				usuarioEJB.crearCliente(cliente,sesion.getBd());
				
				// creando auditoria
				crearAuditoria("Cliente", cliente.getCedula(), "Crear", sesion.getBd());
				
				Messages.addFlashGlobalInfo(
						"Cliente registrado correctamente");

				limpiarCampos();
				mostrarDatosCliente = false;

			} catch (ExcepcionNegocio e) {

				Messages.addFlashGlobalInfo(e.getMessage());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			Messages.addFlashGlobalInfo(
					"Debe ingresar todos los datos");

		}

	}
	
	
	/**
	 * Crea la factura que se asiganar� a los detalle venta
	 */
	public void vender() {

		if (cliente != null) {

			if (productosCompra.size() == 0) {

				Messages.addFlashGlobalInfo(
						"No hay productos en el carrito de compras");

			} else {

				factura = new Venta();

				if (cliente != null) {
					factura.setCliente(cliente);

					//String nuevaFecha = ventaEJB.obtenerFechaActual();

					factura.setFecha(new Date());
					factura.setUsuario(sesion.getUsuario());
					ventaEJB.crear(factura,sesion.getBd());

					//creando auditoria
					crearAuditoria("Venta",factura.getId()+"","Crear", sesion.getBd());
					
					registrarDetallesVenta();
					actualizarInventarios();

					Messages.addFlashGlobalInfo(
							"La venta se ha registrado correctamente");

					limpiarCampos();
					mostrarDatosCliente = false;
					productosCompra = new ArrayList<DetalleVenta>();

				} else {

					Messages.addFlashGlobalInfo(
							"Debe buscar o registrar un cliente previamente");

				}
			}

		} else {
			Messages.addFlashGlobalInfo(
					"Debe buscar el cliente");
		}

	}
	
	/**
	 * Se asigna la fatura a cada uno de los detalles de venta
	 */
	private void registrarDetallesVenta() {
		detalleEJB.registrarDetalleVenta(productosCompra, factura,sesion.getBd());

		try {

			//creando auditoria
			crearAuditoria("DetalleVenta",factura.getId()+"","Editar", sesion.getBd());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * for (DetalleVenta detalleVenta : productosCompra) {
		 * detalleVenta.setFacturaVenta(factura); try { accion = "Registrar Detalle
		 * Venta"; String browserDetail = Faces.getRequest().getHeader("User-Agent");
		 * auditoriaDetalleVentasEJB.crearAuditoriaDetalleVenta(detalleVenta, accion,
		 * browserDetail);
		 * 
		 * }catch (Exception e) { e.printStackTrace(); } // Registramos cada uno de los
		 * detalles venta detalleEJB.registrarDetalleVenta(productosCompra, factura); }
		 **/
	}
	
	
	/**
	 * Actualiza las cantidades de los inventarios
	 */
	private void actualizarInventarios() {
		for (Producto inventarioProducto : inventariosEditar) {
			productoEJB.editarProducto(inventarioProducto,sesion.getBd());
			// creando auditoria
			crearAuditoria("Producto", inventarioProducto.getId()+"", "Editar", sesion.getBd());
		}
	}
	
	
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getTipoGenero() {
		return tipoGenero;
	}

	public void setTipoGenero(int tipoGenero) {
		this.tipoGenero = tipoGenero;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public List<DetalleVenta> getProductosCompra() {
		return productosCompra;
	}

	public void setProductosCompra(List<DetalleVenta> productosCompra) {
		this.productosCompra = productosCompra;
	}

	public int getDeptoSeleccionado() {
		return deptoSeleccionado;
	}

	public void setDeptoSeleccionado(int deptoSeleccionado) {
		this.deptoSeleccionado = deptoSeleccionado;
	}

	public int getMunicipioSeleccionado() {
		return municipioSeleccionado;
	}

	public void setMunicipioSeleccionado(int municipioSeleccionado) {
		this.municipioSeleccionado = municipioSeleccionado;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	public double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public boolean isMostrarDatosCliente() {
		return mostrarDatosCliente;
	}

	public void setMostrarDatosCliente(boolean mostrarDatosCliente) {
		this.mostrarDatosCliente = mostrarDatosCliente;
	}

	

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public DetalleVenta getDetalleAgregar() {
		return detalleAgregar;
	}

	public void setDetalleAgregar(DetalleVenta detalleAgregar) {
		this.detalleAgregar = detalleAgregar;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Para verificar si se seleccion� el bot�n agregar
	 * 
	 * @return true si se seleccion�, de lo contrario false
	 */
	public boolean isAgregado() {
		return agregado;
	}

	public void setAgregado(boolean agregado) {
		this.agregado = agregado;
	}

	public int getCantidadAgregar() {
		return cantidadAgregar;
	}

	public void setCantidadAgregar(int cantidadAgregar) {
		this.cantidadAgregar = cantidadAgregar;
	}

	public boolean isClienteExiste() {
		return cliente == null;
	}

	

}
