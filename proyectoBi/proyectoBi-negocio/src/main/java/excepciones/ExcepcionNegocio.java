package excepciones;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ExcepcionNegocio extends RuntimeException {

	/**
	 * COnstructor
	 * @param message
	 */
	public ExcepcionNegocio(String message) {
		super(message);
	}

}
