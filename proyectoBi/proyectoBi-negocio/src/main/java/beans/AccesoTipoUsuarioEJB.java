package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Acceso;
import entidades.AccesoTipoUsuario;
import entidades.AccesoTipoUsuarioPK;
import entidades.TipoUsuario;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

/**
 * 
 * @author Richard Vanegas
 * Se encarga de todas las operaciones en la tabla de accesotipousuario
 */
@LocalBean
@Stateless
public class AccesoTipoUsuarioEJB {

	@EJB
	private Persistencia conexion;
	
	@EJB
	private TipoUsuarioEJB tipoUsuarioEJB;
	
	@EJB
	private AccesoEJB accesoEJB;
	
	/**
	 * Registrar un acceso a un tipo de usuario
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(AccesoTipoUsuario tipoUsuarioAcceso, int bd){
		conexion.setBd(bd);
		TipoUsuario rol = tipoUsuarioEJB.buscar(tipoUsuarioAcceso.getTipoUsuario().getId(), bd);
		if(rol != null){
			Acceso acceso = accesoEJB.buscar(tipoUsuarioAcceso.getAcceso().getId(), bd);
			if(acceso != null){
				AccesoTipoUsuario ra = buscar(tipoUsuarioAcceso, bd);
				if(ra == null){
					tipoUsuarioAcceso.setAcceso(acceso);
					tipoUsuarioAcceso.setTipoUsuario(rol);
					conexion.crear(tipoUsuarioAcceso);
				}else{
					throw new ExcepcionNegocio("Ya existe este acceso en el tipo usuario "+tipoUsuarioAcceso.getTipoUsuario().getNombre());
				}
			}else{
				throw new ExcepcionNegocio("No existe el acceso");
			}
		}else{
			throw new ExcepcionNegocio("No existe el rol");
		}
	}
	
	/**
	 * Eliminar 
	 */
	public void eliminar(AccesoTipoUsuario tipoUsuarioAcceso, int bd){
		conexion.setBd(bd);
		TipoUsuario rol = tipoUsuarioEJB.buscar(tipoUsuarioAcceso.getTipoUsuario().getId(), bd);
		if(rol != null){
			Acceso acceso = accesoEJB.buscar(tipoUsuarioAcceso.getAcceso().getId(), bd);
			if(acceso != null){
				AccesoTipoUsuario ra = buscar(tipoUsuarioAcceso, bd);
				if(ra != null){
					conexion.eliminar(ra);
				}else{
					throw new ExcepcionNegocio("No existe este acceso en el rol "+tipoUsuarioAcceso.getTipoUsuario().getNombre());
				}
			}else{
				throw new ExcepcionNegocio("No existe el acceso");
			}
		}else{
			throw new ExcepcionNegocio("No existe el rol");
		}
	}
	
	/**
	 * Buscar tipo usuario por id
	 * @param id id del tipo usuario
	 * @param bd base de datos en la que buscara
	 * @return
	 */
	public AccesoTipoUsuario buscar(AccesoTipoUsuario rolAcceso, int bd){
		conexion.setBd(bd);
		AccesoTipoUsuarioPK pk = new AccesoTipoUsuarioPK(rolAcceso.getTipoUsuario().getId(), rolAcceso.getAcceso().getId());
		return (AccesoTipoUsuario) conexion.buscar(AccesoTipoUsuario.class, pk);
	}
	
	/**
	 * Listar Accesos por Rol
	 * @param bd base de datos en la que obtendra los accesos por rol
	 * @return lista de accesos de un determinado rol
	 */
	public List<Acceso> listarByRol(TipoUsuario tipo, int bd){
		conexion.setBd(bd);
		// Lista de roles a retornar
		List<Acceso> listado = new ArrayList<Acceso>();
		// obtenemos la lista de objetos rol de la base de datos
		List<Object> lista = conexion.listarConParametroInteger(AccesoTipoUsuario.listarAccesosPorRol, tipo.getId());
		// recorremos la lista de objetos rol
		for (Object object : lista) {
			AccesoTipoUsuario ra = (AccesoTipoUsuario)object;
			// agregamos a la lista de roles, el objeto lo casteamos como objeto rol
			listado.add(ra.getAcceso());
		}
		return listado;
	}
	
	/**
	 * Listar accsos tipo usuario por tipo usuario
	 */
	public List<AccesoTipoUsuario> listarAccesosTipoPorTipo(TipoUsuario rol, int bd){
		conexion.setBd(bd);
		return (List<AccesoTipoUsuario>)(Object)conexion.listarConParametroInteger(AccesoTipoUsuario.listarAccesosPorRol, rol.getId());
	}
	
}