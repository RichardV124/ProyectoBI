package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Acceso;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class AccesoEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Buscar Acceso por id
	 * @param id id del Acceso
	 * @param bd base de datos en la que buscara
	 * @return
	 */
	public Acceso buscar(int id, int bd){
		conexion.setBd(bd);
		return (Acceso) conexion.buscar(Acceso.class, id);
	}
	
	/**
	 * Listar Accesos
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de accesos
	 */
	public List<Acceso> listar(int bd){
		conexion.setBd(bd);
		return (List<Acceso>)(Object)conexion.listar(Acceso.listarAccesos);
	}

}