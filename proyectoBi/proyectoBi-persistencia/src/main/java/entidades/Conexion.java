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
@Table(name="CONEXION")
@NamedQueries({
	@NamedQuery(name=Conexion.listarTodas,query="SELECT c FROM Conexion c")		
})
public class Conexion implements Serializable{
	
	
	public static final String listarTodas = "Conexion.listar";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONEXION_SEQ")
    @SequenceGenerator(sequenceName = "conexion_seq", allocationSize = 1, name = "CONEXION_SEQ")
	private int id;
	
	@Column(name="NOMBRE", nullable=false, length=50)
	private String nombre;
	
	@Column(name="ACTIVO", nullable=true)
	private boolean activo;

	
	
	public Conexion() {
		super();
	}

	public Conexion(int id, String nombre) {
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

}
