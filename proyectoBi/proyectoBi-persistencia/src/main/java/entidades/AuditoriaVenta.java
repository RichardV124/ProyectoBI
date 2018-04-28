package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AUDITORIA_VENTA")
public class AuditoriaVenta implements Serializable{

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
	
	@JoinColumns({ @JoinColumn(name = "DETALLE_VENTA_PRODUCTO_ID", referencedColumnName = "ID_PRODUCTO"),
		@JoinColumn(name = "DETALLE_VENTA_VENTA_ID", referencedColumnName = "ID_VENTA") })
	@ManyToOne
	private DetalleVenta detalleVenta;
	
	public AuditoriaVenta() {
		// TODO Auto-generated constructor stub
	}
	
}
