package etl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="HECHO_VENTA")
@NamedQuery(name = HechoVenta.TRAER_VENTA, query = "SELECT A FROM HechoVenta A")
public class HechoVenta implements Serializable{
	
	public static final String TRAER_VENTA = "HechoVenta.listdwVENTA";

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="FECHA")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@JoinColumn(name="USUARIO_CEDULA")
	@ManyToOne(cascade = CascadeType.ALL)
	private DimensionUsuario usuario;
	
	@JoinColumn(name="PRODUCTO_ID")
	@ManyToOne(cascade = CascadeType.ALL)
	private DimensionProducto producto;
	
	@JoinColumn(name="CLIENTE_CEDULA")
	@ManyToOne(cascade = CascadeType.ALL)
	private DimensionCliente cliente;
	
	@Column(name="CANTIDAD")
	private int cantidad;
	
	@Column(name="VALOR_TOTAL")
	private double valorTotal;
	
	public HechoVenta() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public DimensionUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(DimensionUsuario usuario) {
		this.usuario = usuario;
	}

	public DimensionProducto getProducto() {
		return producto;
	}

	public void setProducto(DimensionProducto producto) {
		this.producto = producto;
	}

	public DimensionCliente getCliente() {
		return cliente;
	}

	public void setCliente(DimensionCliente cliente) {
		this.cliente = cliente;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
}
