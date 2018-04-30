package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Conexion;
import persistencia.Persistencia;

/**
 * 
 * @author Richard Vanegas
 * Se encarga de todas las operaciones a la tabla conexion
 */
@LocalBean
@Stateless
public class ConexionEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar conexion en la base de datos
	 * @param con, la conexion a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(Conexion con, int bd){
		conexion.setBd(bd);
		conexion.crear(con);
	}
	
	/**
	 * Editar conexion 
	 * @param con, la conexion a editar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void editar(Conexion con, int bd){
		conexion.setBd(bd);
		conexion.editar(con);
	}
	
	/**
	 * Eliminar conexion 
	 * @param empresa la empresa a eliminar
	 * @param bd base de datos en la que sera eliminada la informacion
	 */
	public void eliminar(Conexion con, int bd){
		conexion.setBd(bd);
		conexion.eliminar(con);
	}
	
	/**
	 * Buscar conexion por id
	 * @param id, el id de la conexion a buscar
	 * @param bd base de datos en la que buscara la conexion
	 */
	public Conexion buscar(int id, int bd){
		conexion.setBd(bd);
		return (Conexion)conexion.buscar(Conexion.class, id);
	}
	
	/**
	 * Listar 
	 * @param bd base de datos en la que obtendra las conexiones
	 * @return lista de conexiones
	 */
	public List<Conexion> listar(int bd){
		conexion.setBd(bd);
		return (List<Conexion>)(Object)conexion.listar(Conexion.listarTodas);
	}

}
