package etl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entidades.TipoProducto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name="ANALISIS")
@NamedQueries({
	@NamedQuery(name=Analisis.LISTA_ANALISIS, query="SELECT a FROM Analisis a"),
	@NamedQuery(name=Analisis.buscarPorFecha,query="SELECT tu FROM Analisis tu WHERE tu.fecha=?1")
})
public class Analisis implements Serializable{
	
	/**
	 * Lista de analisis registrados en la BD
	 */
	public static final String LISTA_ANALISIS = "lista.analisis";
	
	public static final String buscarPorFecha = "Analisis.buscarPorFecha";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANALISIS_SEQ")
    @SequenceGenerator(sequenceName = "analisis_seq", allocationSize = 1, name = "ANALISIS_SEQ")
	private int id;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="FECHA")
	@Temporal(TemporalType.DATE)
	protected Date fecha;
	
	@Column(name="RESULTADO")
	@Lob()
	private byte[] resultado;
	

	public Analisis() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public byte[] getResultado() {
		return resultado;
	}

	public void setResultado(byte[] resultado) {
		this.resultado = resultado;
	}



}
