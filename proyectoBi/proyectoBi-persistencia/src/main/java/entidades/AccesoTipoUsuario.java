package entidades;

import java.io.Serializable;

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
	@NamedQuery(name=AccesoTipoUsuario.listarAccesosTipoPorRol,query="SELECT atu FROM AccesoTipoUsuario atu WHERE atu.tipoUsuario.id=?1")
})
public class AccesoTipoUsuario implements Serializable{
	

	public static final String listarAccesosTipoPorRol = "AccesoTipoUsuario.listarAccesosTipoPorRol";
	public static final String listarAccesosPorRol = "AccesoTipoUsuario.listarAccesosPorRol";
	
	@Id
	@ManyToOne(cascade={})
	@JoinColumn(name = "ID_TIPO_USUARIO", nullable = false)
	private TipoUsuario tipoUsuario;
	
	@Id
	@ManyToOne(cascade={})
	@JoinColumn(name = "ID_ACCESO", nullable = false)
	private Acceso acceso;

	public AccesoTipoUsuario() {
		super();
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
	
	

}
