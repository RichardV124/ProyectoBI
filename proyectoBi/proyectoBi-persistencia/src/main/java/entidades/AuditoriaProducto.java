package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AUDITORIA_PRODUCTO")
public class AuditoriaProducto implements Serializable{

	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="ACCION",length=40,nullable=false)
	private String accion;
	
	@Column(name="NAVEGADOR",length=40, nullable=false)
	private String navegador;

	@Column(name="DISPOSITIVO",length=40,nullable=false)
	private String dispositivo;
	
	@Column(name="FECHA",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(name="HORA",nullable=false)
	@Temporal(TemporalType.TIME)
	private Date hora;
	
	@JoinColumn(name="PRODUCTO_ID")
	@ManyToOne(cascade={})
	private Producto producto;
	
	public AuditoriaProducto() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getNavegador() {
		return navegador;
	}

	public void setNavegador(String navegador) {
		this.navegador = navegador;
	}

	public String getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	
}
