package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Acceso;
import entidades.TipoProducto;
import entidades.TipoUsuario;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

/**
 * 
 * @author Richard Vanegas
 * Se encarga de todas las operaciones a la tabla tipo de usuario
 */
@LocalBean
@Stateless
public class TipoProductoEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar un tipo de producto en la base de datos
	 * @param tipo el tipo de producto a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(TipoProducto tipo, int bd){
		conexion.setBd(bd);
		// Buscamos si existe un tipo con el mismo nombre
		List<Object> lista = conexion.listarConParametroString(TipoProducto.buscarPorNombre, tipo.getNombre());
		if(lista.size() == 0){
			conexion.crear(tipo);
		}else{
			throw new excepciones.ExcepcionNegocio("Ya existe un tipo de producto con el nombre: "+tipo.getNombre());
		}
	}
	
	/**
	 * Editar el tipo de producto
	 * @param tipo, el tipo de producto a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void editar(TipoProducto tipo, int bd){
		conexion.setBd(bd);
		TipoProducto tu = buscarPorNombre(tipo.getNombre(), bd);
		if(tu.getId() == tu.getId()){
			conexion.editar(tipo);
		}else{
			throw new ExcepcionNegocio("Ya existe un tipo de producto con el nombre "+tipo.getNombre());
		}
	}
	
	/**
	 * Eliminar
	 * @param tipo el tipo de producto a eliminar
	 * @param bd base de datos en la que sera eliminada la informacion
	 */
	public void eliminar(TipoProducto tipo, int bd){
		conexion.setBd(bd);
		TipoProducto tu = buscar(tipo.getId(), bd);
		if(tu != null){
			conexion.eliminar(tipo);
		}else{
			throw new ExcepcionNegocio("No existe un tipo de producto con el codigo "+tipo.getId());
		}
	}
	
	/**
	 * Buscar tipo de producto por id
	 * @param id, id del tipo de producto
	 * @param bd base de datos en la que buscara
	 * @return
	 */
	public TipoProducto buscar(int id, int bd){
		conexion.setBd(bd);
		return (TipoProducto) conexion.buscar(TipoProducto.class, id);
	}
	
	/**
	 * Listar todos lso tipos de producto
	 * @param bd base de datos en la que obtendra los tipos deusuario
	 * @return lista de tipos de usuario
	 */
	public List<TipoProducto> listar(int bd){
		conexion.setBd(bd);
		return (List<TipoProducto>)(Object)conexion.listar(TipoProducto.listarTipos);
	}
	
	/**
	 * Buscar tipo de producto por nombre
	 */
	public TipoProducto buscarPorNombre(String nombre, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroString(TipoUsuario.buscarPorNombre,nombre);
		if(lista.size() > 0){
			return (TipoProducto)lista.get(0);
		}else{
			return null;
		}
	}


}