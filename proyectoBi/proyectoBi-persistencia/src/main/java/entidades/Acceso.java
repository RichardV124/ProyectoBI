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
@Table(name="ACCESO")
@NamedQueries({
	@NamedQuery(name=Acceso.listarAccesos,query="SELECT a FROM Acceso a")
})

public class Acceso implements Serializable{
	
	public static final String listarAccesos = "Acceso.listarAccesos";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCESO_SEQ")
    @SequenceGenerator(sequenceName = "acceso_seq", allocationSize = 1, name = "ACCESO_SEQ")
	private int id;
	
	@Column(name="url",length=100)
	private String url;
	
	@Column(name="nombre",length=20)
	private String nombre;

	public Acceso() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
