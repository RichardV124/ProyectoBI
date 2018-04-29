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
@Table(name="GENERO")
@NamedQueries({ 
	@NamedQuery(name = Genero.CONSULTA_LISTAR_GENEROS, query = "SELECT g FROM Genero g") 
	})
public class Genero implements Serializable{

	public static final String CONSULTA_LISTAR_GENEROS = "Genero.ListarGeneros";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENERO_SEQ")
    @SequenceGenerator(sequenceName = "genero_seq", allocationSize = 1, name = "GENERO_SEQ")
	private int id;
	
	@Column(name="NOMBRE", nullable=false, length=50)
	private String nombre;

	public Genero() {
		super();
		// TODO Auto-generated constructor stub
	}	
	

	public Genero(int id, String nombre) {
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
	
	
}