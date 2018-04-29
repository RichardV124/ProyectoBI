package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import entidades.Departamento;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class DepartamentoEJB {

	@EJB
	private Persistencia em;
	
	/**
	 * metodo para listar los dptos registrados
	 */
	public List<Departamento> listar(int bd) {
		em.setBd(bd);	
		List<Departamento> dptos =  (List<Departamento>)(Object)em.listar(Departamento.CONSULTA_LISTAR_DEPARTAMENTOS);
		if (dptos.isEmpty()) {
			throw new ExcepcionNegocio("No hay departamentos registrados en la base de datos");
		} else {
			return dptos;
		}
	}
	
	/**
	 * metodo para buscar un departamento
	 * @param numId, numero de identificacion del departamento a buscar
	 * @return el departamento con el respectivo id, o null si no se encuentra el departamento
	 */
	public Departamento buscar(int bd, int pk){
		em.setBd(bd);
		return (Departamento) em.buscar(Departamento.class, pk);
	}
}