package persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.AccesoTipoUsuario;
import entidades.AccesoTipoUsuarioPK;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;

@LocalBean
@Stateless
public class Persistencia  implements Serializable{
	
	/**
	 * Instancia a Oracle (1)
	 */
	@PersistenceContext(unitName = "oracle")
	private EntityManager emO;
	
	/**
	 * Instancia a postgress (2)
	 */
	@PersistenceContext(unitName = "postgres")
	private EntityManager emP;
	
	
	/**
	 * Es la base de dato en la cual esta funcionando el sistema actualmente
	 */
	private int bd;
	
	/**
	 * Guarda en la base de datos
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crear(Object objeto){
		switch (this.bd) {
		case 1:
			emO.persist(objeto);
			break;
		case 2:
			emP.persist(objeto);
			break;
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crearAccesoTipoUsuario(AccesoTipoUsuario accesoTipo){
		// TODO Auto-generated method stub
		AccesoTipoUsuarioPK atuPK = new AccesoTipoUsuarioPK();
//		System.out.println("ROL: " +accesoTipo.getTipoUsuario().getId());
//		System.out.println("ACCESO: " +accesoTipo.getAcceso().getId());
		
		
		atuPK.setAcceso(accesoTipo.getAcceso().getId());
		atuPK.setTipoUsuario(accesoTipo.getTipoUsuario().getId());
		AccesoTipoUsuario atu = (AccesoTipoUsuario) buscar(AccesoTipoUsuario.class,atuPK);
		if (atu == null) {
			emP.persist(accesoTipo);;
		}
	}
	
	/**
	 * Edita en la base de datos
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void editar(Object objeto){
		switch (this.bd) {
		case 1:
			emO.merge(objeto);
			break;
		case 2:
			emP.merge(objeto);
			break;
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	
	/**
	 * Elimina de la base de datos
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Object objeto){
		switch (this.bd) {
		case 1:
			emO.remove(emO.merge(objeto));
			break;
		case 2:
			emP.remove(emP.merge(objeto));
			break;
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Busca en una base de datos determinada
	 * @param objeto el tipo de objeto a buscar
	 * @param pk el identificador del registro a buscar
	 * @return retorna el registro encontrado, de lo contrario null
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object buscar(Class type, Object pk){
		switch (this.bd) {
		case 1:
			return emO.find(type, pk);
		case 2:
			return emP.find(type, pk);
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	
	/**
	 * Listar objetos
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listar(String sql){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			return p.getResultList();
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarConUnParametro(String sql, Object o){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			q.setParameter(1, o);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			p.setParameter(1, o);
			return p.getResultList();
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos usando un parametro
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @parametro el parametro necesario para la consulta
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarConParametroInteger(String sql, int parametro){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			q.setParameter(1, parametro);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			p.setParameter(1, parametro);
			return p.getResultList();
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos usando un parametro String
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @parametro el parametro necesario para la consulta
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarConParametroString(String sql, String parametro){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			q.setParameter(1, parametro);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			p.setParameter(1, parametro);
			return p.getResultList();
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos de una tabla usando las 2 bases de datos
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarCon2BasesDatos(String sql){
		try{
			Query q = emO.createNamedQuery(sql);
			Query p = emP.createNamedQuery(sql);
			List<Object> lista = new ArrayList<Object>(q.getResultList());
			if(lista.addAll(p.getResultList())){
				return lista;
			}else{
				throw new ExcepcionNegocio("No se pudo unir los dos listados de las bases de datos");
			}
		}catch (ExcepcionNegocio ex){
			throw new ExcepcionNegocio(ex.getMessage());
		}
	}
	

	/**
	 * Accesores Y Modificadores
	 */
	
	public int getBd() {
		return bd;
	}

	public void setBd(int bd) {
		this.bd = bd;
	}	

}

