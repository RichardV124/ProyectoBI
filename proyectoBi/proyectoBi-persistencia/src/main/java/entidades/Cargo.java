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

@NamedQueries({
	@NamedQuery(name=Cargo.CONSULTA_LISTAR_CARGOS,query="SELECT c FROM Cargo c")
})
@Entity
@Table(name="CARGO")
public class Cargo implements Serializable {
	
	/**
	 * Lista los cargos registrados
	 */
	public static final String CONSULTA_LISTAR_CARGOS = "Cargo.listar";
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="DESCRIPCION", length=30)
	private String descripcion;
	
	@Column(name="NOMBRE", length=30)
	private String nombre;
	
	
	public Cargo() {
		// TODO Auto-generated constructor stub
	}
	
	public Cargo(int id, String descripcion,String nombre) {
		super();
		this.id = id;
		this.descripcion = descripcion;
				this.nombre=nombre;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	

}
