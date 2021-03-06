package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="TIPO_PRODUCTO")
@NamedQueries({
	@NamedQuery(name=TipoProducto.listarTipos,query="SELECT tu FROM TipoProducto tu"),
	@NamedQuery(name=TipoProducto.buscarPorNombre,query="SELECT tu FROM TipoProducto tu WHERE tu.nombre=?1")
})
public class TipoProducto implements Serializable{

	public static final String listarTipos = "TipoProducto.listarTipos";
	public static final String buscarPorNombre = "TipoProducto.buscarPorNombre";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_PRODUCTO_SEQ")
    @SequenceGenerator(sequenceName = "tipo_producto_seq", allocationSize = 1, name = "TIPO_PRODUCTO_SEQ")
	private int id;
	
	@Column(name="NOMBRE", nullable=false, length=50)
	private String nombre;
	
	@Column(name="DESCRIPCION" , length = 200 , nullable=false)
	private String descripcion;

	public TipoProducto() {
		super();
		// TODO Auto-generated constructor stub
	}	
	

	public TipoProducto(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	
	
	
	
}