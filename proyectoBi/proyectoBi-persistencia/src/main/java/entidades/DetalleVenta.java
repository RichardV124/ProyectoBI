package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="DETALLE_VENTA")
@IdClass(DetalleVentaPK.class)
@NamedQueries({
	@NamedQuery(name = DetalleVenta.LISTAR_DETALLES_FACTURA, query = "SELECT dv FROM DetalleVenta dv"
			+ " WHERE dv.venta = ?1"),
	@NamedQuery(name = DetalleVenta.CONSULTA_LISTAR, query = "SELECT dv FROM DetalleVenta dv"),
	@NamedQuery(name = DetalleVenta.CONSULTA_LISTAR_DETALLEVENTA_FECHA, query = "SELECT dv "
			+ "FROM DetalleVenta dv WHERE dv.venta.fecha BETWEEN ?1 AND ?2")
	})
public class DetalleVenta implements Serializable{
	
	
	public static final String LISTAR_DETALLES_FACTURA = "detalle.listarDetalleFactura";
	
	public static final String CONSULTA_LISTAR = "DetalleVenta.Listar";
	public static final String CONSULTA_LISTAR_DETALLEVENTA_FECHA = "DetalleVenta.ListarDetalleVentaFecha";
	
	@Id
	@ManyToOne(cascade={})
	@JoinColumn(name = "ID_PRODUCTO", nullable = false)
	private Producto producto;
	
	@Id
	@ManyToOne(cascade={})
	@JoinColumn(name = "ID_VENTA", nullable = false)
	private Venta venta;
	
	@Column(name="CANTIDAD", nullable=false)
	private int cantidad;
	

	public DetalleVenta() {
		super();
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	


}
