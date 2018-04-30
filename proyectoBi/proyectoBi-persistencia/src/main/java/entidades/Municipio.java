package entidades;

import java.io.Serializable;

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

@Entity
@Table(name="MUNICIPIO")
@NamedQueries({ 
	@NamedQuery(name = Municipio.CONSULTA_LISTAR_MUNICIPIOS, query = "SELECT m FROM Municipio m WHERE m.departamento=?1") 
	})
public class Municipio implements Serializable{

	public static final String CONSULTA_LISTAR_MUNICIPIOS = "Municipio.ListarMunicipios";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUNICIPIO_SEQ")
    @SequenceGenerator(sequenceName = "municipio_seq", allocationSize = 1, name = "MUNICIPIO_SEQ")
	private int id;
	
	@Column(name="NOMBRE", nullable=false, length=50)
	private String nombre;
	

	@JoinColumn(name="DEPARTAMENTO_ID")
	@ManyToOne(cascade = {})
	private Departamento departamento;

	public Municipio() {
		super();
		// TODO Auto-generated constructor stub
	}	
	

	public Municipio(int id, String nombre) {
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


	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	
	
	
}
