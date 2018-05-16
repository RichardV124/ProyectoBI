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
@Table(name="VENTA")
@NamedQueries({
	@NamedQuery(name=Venta.LISTAR_POR_FECHA, query="SELECT fv FROM FacturaVenta fv WHERE fv.fechaVenta = ?1")})
public class Venta implements Serializable{
	
	/**
	 * Lista las facturas por fecha
	 * ?1 fecha
	 */
	public static final String LISTAR_POR_FECHA = "facturaVenta.listarPorFecha";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VENTA_SEQ")
    @SequenceGenerator(sequenceName = "venta_seq", allocationSize = 1, name = "VENTA_SEQ")
	private int id;
	
	@Column(name="FECHA",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@JoinColumn(name="CLIENTE_ID")
	@ManyToOne(cascade = {})
	private Cliente cliente;
	
	@JoinColumn(name="USUARIO_ID")
	@ManyToOne(cascade = {})
	private Usuario usuario;
	

	public Venta() {
		super();
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

	
	
	

}
