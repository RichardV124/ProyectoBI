package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Acceso;
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
public class TipoUsuarioEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar un tipo de usuario en la base de datos
	 * @param tipo el tipo de usuario a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(TipoUsuario tipo, int bd){
		conexion.setBd(bd);
		// Buscamos si existe un tipo con el mismo nombre
		List<Object> lista = conexion.listarConParametroString(TipoUsuario.buscarPorNombre, tipo.getNombre());
		if(lista.size() == 0){
			conexion.crear(tipo);
		}else{
			throw new excepciones.ExcepcionNegocio("Ya existe un tipo de usuario con el nombre: "+tipo.getNombre());
		}
	}
	
	/**
	 * Editar el tipo de usuario
	 * @param tipo, el tipo de usuario a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void editar(TipoUsuario tipo, int bd){
		conexion.setBd(bd);
		TipoUsuario tu = buscarPorNombre(tipo.getNombre(), bd);
		if(tu.getId() == tu.getId()){
			conexion.editar(tipo);
		}else{
			throw new ExcepcionNegocio("Ya existe un tipo de usuario con el nombre "+tipo.getNombre());
		}
	}
	
	/**
	 * Eliminar
	 * @param tipo el tipo de usuario a eliminar
	 * @param bd base de datos en la que sera eliminada la informacion
	 */
	public void eliminar(TipoUsuario tipo, int bd){
		conexion.setBd(bd);
		TipoUsuario tu = buscar(tipo.getId(), bd);
		if(tu != null){
			conexion.eliminar(tipo);
		}else{
			throw new ExcepcionNegocio("No existe un tipo de usuario con el codigo "+tipo.getId());
		}
	}
	
	/**
	 * Buscar tipo de usuario por id
	 * @param id, id del tipo de usuario
	 * @param bd base de datos en la que buscara
	 * @return
	 */
	public TipoUsuario buscar(int id, int bd){
		conexion.setBd(bd);
		return (TipoUsuario) conexion.buscar(TipoUsuario.class, id);
	}
	
	/**
	 * Listar todos lso tipos de usuario
	 * @param bd base de datos en la que obtendra los tipos deusuario
	 * @return lista de tipos de usuario
	 */
	public List<TipoUsuario> listar(int bd){
		conexion.setBd(bd);
		return (List<TipoUsuario>)(Object)conexion.listar(TipoUsuario.listarTipos);
	}
	
	/**
	 * Buscar tipo de usuario por nombre
	 */
	public TipoUsuario buscarPorNombre(String nombre, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroString(TipoUsuario.buscarPorNombre,nombre);
		if(lista.size() > 0){
			return (TipoUsuario)lista.get(0);
		}else{
			return null;
		}
	}


}