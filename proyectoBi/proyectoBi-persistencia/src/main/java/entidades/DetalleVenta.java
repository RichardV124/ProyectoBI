package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="DETALLE_VENTA")
@IdClass(DetalleVentaPK.class)
public class DetalleVenta implements Serializable{
	
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
