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
@Table(name= "CARGO")
@NamedQueries({ 
	@NamedQuery(name = Cargo.CONSULTA_LISTAR_CARGOS, query = "SELECT c FROM Cargo c") 
	})
public class Cargo implements Serializable{
	
	public static final String CONSULTA_LISTAR_CARGOS = "Cargo.ListarCargos";

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQ")
    @SequenceGenerator(sequenceName = "cargo_seq", allocationSize = 1, name = "CARGO_SEQ")
	private int id;
	
	@Column(name="NOMBRE", nullable=false, length=50)
	private String nombre;
	
	@Column(name="DESCRIPCION", nullable=false, length=200)
	private String descripcion;
	
	public Cargo() {
		// TODO Auto-generated constructor stub
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
