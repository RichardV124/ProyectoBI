package beansETL;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.TipoUsuario;
import etl.Analisis;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

/**
 * 
 * @author Richard Vanegas
 * Se encarga de todas las operaciones a la tabla analisis
 */
@LocalBean
@Stateless
public class AnalisisEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar un analisis en la base de datos
	 * @param analisis el analisis a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(Analisis analisis, int bd){
		conexion.setBd(bd);
		// Buscamos si existe un tipo con el mismo nombre
		List<Object> lista = conexion.listarConParametroDate(Analisis.buscarPorFecha, analisis.getFecha());
		if(lista.size() == 0){
			conexion.crear(analisis);
		}else{
			throw new excepciones.ExcepcionNegocio("Ya existe un analisis realizado en la fecha: "+analisis.getFecha());
		}
	}
	
	/**
	 * Editar el analisis
	 * @param tipo, el tipo de producto a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void editar(Analisis tipo, int bd){
		conexion.setBd(bd);
		Analisis tu = buscarPorFecha(tipo.getFecha(), bd);
		if(tu.getId() == tu.getId()){
			conexion.editar(tipo);
		}else{
			throw new ExcepcionNegocio("No se ha realizado ningun analisis en la fecha "+tipo.getFecha());
		}
	}
	
	/**
	 * Eliminar
	 * @param analisis, el analisis a eliminar
	 * @param bd base de datos en la que sera eliminada la informacion
	 */
	public void eliminar(Analisis analisis, int bd){
		conexion.setBd(bd);
		Analisis tu = buscarPorFecha(analisis.getFecha(), bd);
		if(tu != null){
			conexion.eliminar(analisis);
		}else{
			throw new ExcepcionNegocio("No se ha realizado ningun analisis en la fecha "+analisis.getFecha());
		}
	}
	
	/**
	 * Listar todos lso analisis
	 * @param bd base de datos en la que obtendra los analisis
	 * @return lista de analisis
	 */
	public List<Analisis> listar(int bd){
		conexion.setBd(bd);
		return (List<Analisis>)(Object)conexion.listar(Analisis.LISTA_ANALISIS);
	}
	
	/**
	 * Buscar analisis por fecha
	 */
	public Analisis buscarPorFecha(Date fecha, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroDate(TipoUsuario.buscarPorNombre,fecha);
		if(lista.size() > 0){
			return (Analisis)lista.get(0);
		}else{
			return null;
		}
	}



}