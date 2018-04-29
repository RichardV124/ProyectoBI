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
@Table(name="DEPARTAMENTO")
@NamedQueries({ 
	@NamedQuery(name = Departamento.CONSULTA_LISTAR_DEPARTAMENTOS, query = "SELECT d FROM Departamento d") 
	})
public class Departamento implements Serializable{

	public static final String CONSULTA_LISTAR_DEPARTAMENTOS = "Departamento.ListarDepartamentos";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTO_SEQ")
    @SequenceGenerator(sequenceName = "departamento_seq", allocationSize = 1, name = "DEPARTAMENTO_SEQ")
	private int id;
	
	@Column(name="NOMBRE", nullable=false, length=50)
	private String nombre;

	public Departamento() {
		super();
		// TODO Auto-generated constructor stub
	}	
	

	public Departamento(int id, String nombre) {
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