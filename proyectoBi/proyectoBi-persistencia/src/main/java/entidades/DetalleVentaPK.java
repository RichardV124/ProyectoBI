package entidades;

import java.io.Serializable;

public class DetalleVentaPK implements Serializable{

	private int producto;

	private int venta;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + producto;
		result = prime * result + venta;
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
		DetalleVentaPK other = (DetalleVentaPK) obj;
		if (producto != other.producto)
			return false;
		if (venta != other.venta)
			return false;
		return true;
	}
	
	
	
}
