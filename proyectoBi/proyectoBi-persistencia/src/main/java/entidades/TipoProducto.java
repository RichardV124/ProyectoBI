package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="TIPO_PRODUCTO")
@NamedQueries({ 
	@NamedQuery(name = TipoProducto.CONSULTA_LISTAR_TIPOS_PRODUCTO, query = "SELECT tp FROM TipoProducto tp") 
	})
public class TipoProducto implements Serializable{

	public static final String CONSULTA_LISTAR_TIPOS_PRODUCTO = "TipoProducto.ListarTiposProducto";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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