package entidades;

import java.io.Serializable;

public class AccesoTipoUsuarioPK implements Serializable{

	private int tipoUsuario;

	private int acceso;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acceso;
		result = prime * result + tipoUsuario;
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
		AccesoTipoUsuarioPK other = (AccesoTipoUsuarioPK) obj;
		if (acceso != other.acceso)
			return false;
		if (tipoUsuario != other.tipoUsuario)
			return false;
		return true;
	}
	
	

}
