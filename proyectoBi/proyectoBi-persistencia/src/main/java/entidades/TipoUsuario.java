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
@Table(name="TIPO_USUARIO")
@NamedQueries({
	@NamedQuery(name=TipoUsuario.listarTipos,query="SELECT tu FROM TipoUsuario tu"),
	@NamedQuery(name=TipoUsuario.buscarPorNombre,query="SELECT tu FROM TipoUsuario tu WHERE tu.nombre=?1")
})
public class TipoUsuario implements Serializable{
	
	

	public static final String listarTipos = "TipoUsuario.listarTipos";
	public static final String buscarPorNombre = "TipoUsuario.buscarPorNombre";
	
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

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoUsuario other = (TipoUsuario) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	

}
