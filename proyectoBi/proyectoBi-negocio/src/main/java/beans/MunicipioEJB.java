package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Departamento;
import entidades.Municipio;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class MunicipioEJB {

	@EJB
	private Persistencia em;
	
	/**
	 * metodo para listar los municipio registrados
	 */
	public List<Municipio> listar(int bd, Departamento dpto) {
		em.setBd(bd);	
		
		List<Municipio> muns =  (List<Municipio>)(Object)em.listarConUnParametro(Municipio.CONSULTA_LISTAR_MUNICIPIOS, dpto);
		if (muns.isEmpty()) {
			throw new ExcepcionNegocio("No hay municipios registrados en la base de datos");
		} else {
			return muns;
		}
	}
	
	/**
	 * metodo para buscar un municipio
	 * @param numId, numero de identificacion del municipio a buscar
	 * @return el municipio con el respectivo id, o null si no se encuentra el municipio
	 */
	public Municipio buscar(int bd, int pk){
		em.setBd(bd);
		return (Municipio) em.buscar(Municipio.class, pk);
	}
}
