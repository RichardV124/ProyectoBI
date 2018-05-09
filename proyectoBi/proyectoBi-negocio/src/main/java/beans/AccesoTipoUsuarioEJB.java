package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Acceso;
import entidades.AccesoTipoUsuario;
import entidades.AccesoTipoUsuarioPK;
import entidades.Departamento;
import entidades.TipoUsuario;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

/**
 * 
 * @author Richard Vanegas Se encarga de todas las operaciones en la tabla de
 *         accesotipousuario
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
	 * 
	 * @param bd
	 *            base de datos en la que sera guardada la informacion
	 */
	public void crear(AccesoTipoUsuario tipoUsuarioAcceso, int bd) {
		conexion.setBd(bd);
		AccesoTipoUsuario ra = buscar(tipoUsuarioAcceso, bd);
		if (ra == null) {
			tipoUsuarioAcceso.setAcceso(tipoUsuarioAcceso.getAcceso());
			tipoUsuarioAcceso.setTipoUsuario(tipoUsuarioAcceso.getTipoUsuario());
			conexion.crearAccesoTipoUsuario(tipoUsuarioAcceso);
		} else {
			throw new ExcepcionNegocio(
					"Ya existe este acceso en el tipo usuario " + tipoUsuarioAcceso.getTipoUsuario().getNombre());
		}
	}

	/**
	 * Eliminar
	 */
	public void eliminar(AccesoTipoUsuario tipoUsuarioAcceso, int bd) {
		conexion.setBd(bd);

				AccesoTipoUsuario ra = buscar(tipoUsuarioAcceso, bd);
				if (ra != null) {
					conexion.eliminarAccesoTipoUsuario(tipoUsuarioAcceso);
				} else {
					throw new ExcepcionNegocio(
							"No existe este acceso en el rol " + tipoUsuarioAcceso.getTipoUsuario().getNombre());
				}
	}

	/**
	 * Buscar tipo usuario por id
	 * 
	 * @param id
	 *            id del tipo usuario
	 * @param bd
	 *            base de datos en la que buscara
	 * @return
	 */
	public AccesoTipoUsuario buscar(AccesoTipoUsuario rolAcceso, int bd) {
		conexion.setBd(bd);
		AccesoTipoUsuarioPK pk = new AccesoTipoUsuarioPK(rolAcceso.getTipoUsuario().getId(),
				rolAcceso.getAcceso().getId());
		return (AccesoTipoUsuario) conexion.buscar(AccesoTipoUsuario.class, pk);
	}

	/**
	 * Listar accsos tipo usuario por tipo usuario
	 */
	public List<AccesoTipoUsuario> listarAccesosTipoPorTipo(TipoUsuario rol, int bd) {
		conexion.setBd(bd);
		return (List<AccesoTipoUsuario>) (Object) conexion
				.listarConParametroInteger(AccesoTipoUsuario.listarAccesosPorRol, rol.getId());
	}

	/**
	 * Listar accesos por tipo de usuario
	 */
	public List<Acceso> listarAccesosPorTipo(TipoUsuario rol, int bd) {
		conexion.setBd(bd);
		return (List<Acceso>) (Object) conexion.listarConParametroInteger(AccesoTipoUsuario.listarAccesosPorRol,
				rol.getId());
	}

	/**
	 * metodo para listar los accesotipousuario registrados
	 */
	public List<AccesoTipoUsuario> listar(int bd) {
		conexion.setBd(bd);
		List<AccesoTipoUsuario> dptos = (List<AccesoTipoUsuario>) (Object) conexion
				.listar(AccesoTipoUsuario.listarTodosAccesosTipoUsuario);
		if (dptos.isEmpty()) {
			throw new ExcepcionNegocio("No hay accesos asignados a ningun rol registrados en la base de datos");
		} else {
			return dptos;
		}
	}

	/**
	 * Listar Accesos por Rol
	 * 
	 * @param bd
	 *            base de datos en la que obtendra los accesos por rol
	 * @return lista de accesos de un determinado rol
	 */
	public List<Acceso> listarByRol(TipoUsuario tipo, int bd) {
		conexion.setBd(bd);

		List<Acceso> listado = new ArrayList<Acceso>();
		List<Object> lista = conexion.listarConParametroInteger(AccesoTipoUsuario.listarAccesosPorRol, tipo.getId());
		for (Object object : lista) {
			AccesoTipoUsuario ra = (AccesoTipoUsuario) object;

			listado.add(ra.getAcceso());
		}
		return listado;
	}

}