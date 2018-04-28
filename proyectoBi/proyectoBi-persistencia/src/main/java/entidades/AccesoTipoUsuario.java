package entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ACCESO_TIPO_USUARIO")
@IdClass(AccesoTipoUsuarioPK.class)
public class AccesoTipoUsuario implements Serializable{
	

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
