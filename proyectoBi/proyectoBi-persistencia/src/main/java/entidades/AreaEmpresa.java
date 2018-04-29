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
@Table(name="AREA_EMPRESA")
@NamedQueries({ 
	@NamedQuery(name = AreaEmpresa.CONSULTA_LISTAR_AREAS_EMPRESA, query = "SELECT ae FROM AreaEmpresa ae") 
	})
public class AreaEmpresa implements Serializable{

	public static final String CONSULTA_LISTAR_AREAS_EMPRESA = "AreaEmpresa.ListarAreasEmpresa";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_EMPRESA_SEQ")
    @SequenceGenerator(sequenceName = "area_empresa_seq", allocationSize = 1, name = "AREA_EMPRESA_SEQ")
	private int id;
	
	@Column(name="NOMBRE", nullable=false, length=50)
	private String nombre;
	
	@Column(name="DESCRIPCION", nullable=false, length=200)
	private String descripcion;

	public AreaEmpresa() {
		super();
		// TODO Auto-generated constructor stub
	}	
	

	public AreaEmpresa(int id, String nombre) {
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