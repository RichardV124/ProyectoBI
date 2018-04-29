package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Departamento;
import entidades.Genero;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class GeneroEJB {
	
	@EJB
	private Persistencia em;
	
	/**
	 * metodo para listar los generos registrados
	 */
	public List<Genero> listar(int bd) {
		em.setBd(bd);	
		List<Genero> generos =  (List<Genero>)(Object)em.listar(Genero.CONSULTA_LISTAR_GENEROS);
		if (generos.isEmpty()) {
			throw new ExcepcionNegocio("No hay generos registrados en la base de datos");
		} else {
			return generos;
		}
	}
	
	/**
	 * metodo para buscar un genero
	 * @param numId, numero de identificacion del genero a buscar
	 * @return el genero con el respectivo id, o null si no se encuentra el genero
	 */
	public Genero buscar(int bd, int pk){
		em.setBd(bd);
		return (Genero) em.buscar(Genero.class, pk);
	}
}
