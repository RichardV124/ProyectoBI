package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_USUARIO")
public class TipoUsuario implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_USUARIO_SEQ")
    @SequenceGenerator(sequenceName = "tipo_usuario_seq", allocationSize = 1, name = "TIPO_USUARIO_SEQ")
	@Column(name="ID", length=12, nullable=false)
	private int id;
	
	@Column(name="NOMBRE" , length = 20 , nullable=false)
	private String nombre;
	
	@Column(name="DESCRIPCION" , length = 200 , nullable=false)
	private String descripcion;
	
	

	public TipoUsuario() {
		super();
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
