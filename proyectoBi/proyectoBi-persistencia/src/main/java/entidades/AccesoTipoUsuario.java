package entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ACCESO_TIPO_USUARIO")
@IdClass(AccesoTipoUsuarioPK.class)
@NamedQueries({
	@NamedQuery(name=AccesoTipoUsuario.listarAccesosPorRol,query="SELECT atu.acceso FROM AccesoTipoUsuario atu WHERE atu.tipoUsuario.id=?1"),
	@NamedQuery(name=AccesoTipoUsuario.listarTodosAccesosTipoUsuario,query="SELECT atu FROM AccesoTipoUsuario atu"),
	@NamedQuery(name=AccesoTipoUsuario.listarAccesosTipoPorRol,query="SELECT atu FROM AccesoTipoUsuario atu WHERE atu.tipoUsuario.id=?1")
})
public class AccesoTipoUsuario implements Serializable{
	

	public static final String listarTodosAccesosTipoUsuario = "AccesoTipoUsuario.listarTodosAccesosTipoUsuario";
	public static final String listarAccesosTipoPorRol = "AccesoTipoUsuario.listarAccesosTipoPorRol";
	public static final String listarAccesosPorRol = "AccesoTipoUsuario.listarAccesosPorRol";
	
	@Id
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_USUARIO", nullable = false)
	private TipoUsuario tipoUsuario;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "ID_ACCESO", nullable = false)
	private Acceso acceso;

	public AccesoTipoUsuario() {
		super();
	}
	
	

	public AccesoTipoUsuario(TipoUsuario tipoUsuario, Acceso acceso) {
		super();
		this.tipoUsuario = tipoUsuario;
		this.acceso = acceso;
	}



	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Acceso getAcceso() {
		return acceso;
	}

	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceso == null) ? 0 : acceso.hashCode());
		result = prime * result + ((tipoUsuario == null) ? 0 : tipoUsuario.hashCode());
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
		AccesoTipoUsuario other = (AccesoTipoUsuario) obj;
		if (acceso == null) {
			if (other.acceso != null)
				return false;
		} else if (!acceso.equals(other.acceso))
			return false;
		if (tipoUsuario == null) {
			if (other.tipoUsuario != null)
				return false;
		} else if (!tipoUsuario.equals(other.tipoUsuario))
			return false;
		return true;
	}
	
	
	
	

}
