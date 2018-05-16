package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PRODUCTO")
@NamedQueries({
	@NamedQuery(name=Producto.LISTAR, query="SELECT p FROM Producto p")
})
public class Producto implements Serializable{
	
	/**
	 * Lista los productos registrados
	 */
	public static final String LISTAR = "producto.listar";

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTO_SEQ")
    @SequenceGenerator(sequenceName = "producto_seq", allocationSize = 1, name = "PRODUCTO_SEQ")
	private int id;
	
	@Column(name="NOMBRE",length=40,nullable=false)
	private String nombre;
	
	@Column(name="DESCRIPCION", nullable=false, length=200)
	private String descripcion;
	
	@Column(name="FECHA_INGRESO",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;	
	
	@Column(name="CANTIDAD", nullable=false)
	private int cantidad;
	
	@JoinColumn(name="LOTE_ID")
	@ManyToOne(cascade = {})
	private Lote lote;
	
	@Column(name="PESO", nullable=false)
	private double peso;
	
	@Column(name="DIMENSIONES",length=40,nullable=false)
	private String dimensiones;
	
	@Column(name="VALOR", nullable=false)
	private double valor;
	
	@JoinColumn(name="TIPO_PRODUCTO_ID")
	@ManyToOne(cascade = {})
	private TipoProducto tipoProducto;
	
	@JoinColumn(name="USUARIO_CEDULA")
	@ManyToOne(cascade = {})
	private Usuario usuario;

	public Producto() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
	
	
}
