package persistencia;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import entidades.DetalleVenta;
import entidades.Producto;
import entidades.Usuario;
import entidades.Venta;
import etl.Analisis;
import excepciones.ExcepcionNegocio;
import km.DimensionPage;
import km.DimensionUser;
import km.HechoRecentChange;

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
	 * Instancia a MySQL (3)
	 */
	@PersistenceContext(unitName = "mysql")
	private EntityManager emM;
	
	/**
	 * Instancia a MySQL (3)
	 */
	@PersistenceContext(unitName = "mediawiki")
	private EntityManager emMw;
	
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
	
	/**
	 * Registra un detalle de venta
	 * 
	 * @param detalles
	 *            detalles de venta que se desea registar
	 * @param factura
	 *            factura que se desea registrar
	 */
	public void registrarDetalleVenta(List<DetalleVenta> detalles, Venta venta) {

		String sql = "INSERT INTO DETALLE_VENTA (ID_VENTA, "
				+ "ID_PRODUCTO, CANTIDAD) VALUES (?1,?2,?3)";

		switch (this.bd) {
		case 1:
			for (DetalleVenta dv : detalles) {

				Query q = emO.createNativeQuery(sql);
				q.setParameter(1, venta.getId());
				q.setParameter(2, dv.getProducto().getId());
				q.setParameter(3, dv.getCantidad());
				q.executeUpdate();

			}
			break;

		case 2:
			for (DetalleVenta dv : detalles) {

				Query q = emP.createNativeQuery(sql);
				q.setParameter(1, venta.getId());
				q.setParameter(2, dv.getProducto().getId());
				q.setParameter(3, dv.getCantidad());
				q.executeUpdate();

			}
			break;

		default:
			throw new ExcepcionNegocio("La base de datos a la cual intenta acceder no existe");
		}

	}
	
	public void eliminarDetalleVenta(DetalleVenta dv) {

		String sql = "DELETE FROM DETALLE_VENTA WHERE factura_venta_id = ?1 AND producto_id = ?2";
		
		Query q;

		switch (this.bd) {
		case 1:
			q = emO.createNativeQuery(sql);
			break;

		case 2:
			q = emP.createNativeQuery(sql);
			break;

		default:
			throw new ExcepcionNegocio("La base de datos a la cual intenta acceder no existe");
		}
		
		q.setParameter(1, dv.getVenta().getId());
		q.setParameter(2, dv.getProducto().getId());
		q.executeUpdate();

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crearAccesoTipoUsuario(AccesoTipoUsuario accesoTipo){
		// TODO Auto-generated method stub
		AccesoTipoUsuarioPK atuPK = new AccesoTipoUsuarioPK();
//		System.out.println("ROL: " +accesoTipo.getTipoUsuario().getId());
//		System.out.println("ACCESO: " +accesoTipo.getAcceso().getId());
//		
//		
//		atuPK.setAcceso(accesoTipo.getAcceso().getId());
//		atuPK.setTipoUsuario(accesoTipo.getTipoUsuario().getId());
//		AccesoTipoUsuario atu = (AccesoTipoUsuario) buscar(AccesoTipoUsuario.class,atuPK);
//		if (atu == null) {
//			emP.persist(accesoTipo);;
//		}
		
		
		Query q = emP.createNativeQuery("INSERT INTO ACCESO_TIPO_USUARIO (ID_TIPO_USUARIO,ID_ACCESO) VALUES (?1,?2)");
		
		q.setParameter(1, accesoTipo.getTipoUsuario().getId());
		q.setParameter(2, accesoTipo.getAcceso().getId());
		
		q.executeUpdate();
		
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarAccesoTipoUsuario(AccesoTipoUsuario accesoTipo){		
		
		Query q = emP.createNativeQuery("DELETE FROM ACCESO_TIPO_USUARIO ac WHERE ac.ID_TIPO_USUARIO=?1 AND ac.ID_ACCESO=?2");
		
		q.setParameter(1, accesoTipo.getTipoUsuario().getId());
		q.setParameter(2, accesoTipo.getAcceso().getId());
		
		q.executeUpdate();
		
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
	 * Listar objetos
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarConDosParametro(String sql, Object o, Object o2){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			q.setParameter(1, o);
			q.setParameter(2, o2);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			p.setParameter(1, o);
			p.setParameter(2, o2);
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
	 * Listar objetos usando un parametro String
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @parametro el parametro necesario para la consulta
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarConParametroDate(String sql, Date parametro){
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
	 * Listar objetos usando un parametro de tipo objeto y la sql
	 * 
	 * @param sql
	 *            consulta que se desea ejectar
	 * @param objeto
	 *            El objeto parámetro
	 * @return lista de los objetos encontrados
	 */
	public List<Object> listarConParametroObjeto(String sql, Object objeto) {
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			q.setParameter(1, objeto);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			p.setParameter(1, objeto);
			return p.getResultList();
		default:
			throw new ExcepcionNegocio("La base de datos a la cual intenta acceder no existe");
		}
	}
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crearAnalisis(Analisis analisis){
		// TODO Auto-generated method stub
		
		
		Query q = emP.createNativeQuery("INSERT INTO ANALISIS (FECHA,RESULTADO,DESCRIPCION) VALUES (?1,?2,?3)");
		
		q.setParameter(1, analisis.getFecha());
		q.setParameter(2, analisis.getResultado());
		q.setParameter(3, analisis.getDescripcion());
		
		q.executeUpdate();
		
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
	
	
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarDW(String sql){
		switch (this.bd) {
		case 1:
			Query q = emM.createNamedQuery(sql);
			return q.getResultList();
		default:
			throw new ExcepcionNegocio("La base de datos #"+this.bd+" no existe.");
		}
	}
	

	// ------------------>Gestion de la Wiki<----------------------
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DimensionUser> listaUsuarios(){
		List<DimensionUser> users = new ArrayList<DimensionUser>();
		List<Object[]> usersBd = listarWiki("SELECT CAST(u.user_name AS CHAR) username, CAST(u.user_real_name AS CHAR) NAME FROM USER u;");
		for (Object[] a : usersBd) {
		    System.out.println("INFOOOOOOOOOOOOOOOOOO Usuario " + a[0] + " " + a[1]);
		    DimensionUser user = new DimensionUser();
		    user.setUsername(String.valueOf(a[0]));
		    user.setRealname(String.valueOf(a[1]));		
		    users.add(user);
		}
		return users;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarUsers(List<DimensionUser> users){		
		if(users!=null){		
			for(DimensionUser du : users){
				emM.persist(du);
			}
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DimensionPage> listaPaginas() throws Exception{
		List<DimensionPage> pages = new ArrayList<DimensionPage>();
		List<Object[]> pagesBd = listarWiki("SELECT (SELECT DATE_FORMAT(CAST(rc.rc_timestamp AS CHAR), '%d/%m/%Y')) AS fecha,"
				+ " CAST(rc.rc_user_text AS CHAR) USER, CAST(rc.rc_title AS CHAR) title, "
				+ "CAST(rc.rc_comment AS CHAR) COMMENT FROM recentchanges rc  WHERE rc.rc_new=1;");
		
		List<Object[]> texts = listaTextos();
		
		for (Object[] a : pagesBd) {
			for(Object[] t : texts){
				if(String.valueOf(a[2]).equalsIgnoreCase(String.valueOf(t[0]))){
				    System.out.println("INFOOOOOOOOOOOOOOOOOO Pages " + a[0] + " " + a[1] + a[2]);
				    DimensionPage page = new DimensionPage();	
				    page.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(a[0])));
				    page.setTitle(String.valueOf(a[2]));
				    page.setComment(String.valueOf(a[3]));
				    page.setText(String.valueOf(t[1]));
				    pages.add(page);
				}
			}
		}
		return pages;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarPages(List<DimensionPage> pages){		
		if(pages!=null){
			for(DimensionPage dp : pages){
				emM.persist(dp);
			}
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> listaTextos() {
		List<Object[]> textsBd = listarWiki("SELECT CAST(si_title AS CHAR) title, CAST(si_text AS CHAR) TEXT FROM searchindex;");
		for (Object[] a : textsBd) {
		    System.out.println("INFOOOOOOOOOOOOOOOOOO Texts " + a[0] + " " + a[1]);
		    
		}
		return textsBd;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<HechoRecentChange> listaCambios() throws Exception{
		List<HechoRecentChange> changes = new ArrayList<HechoRecentChange>();
		List<Object[]> changesBd = listarWiki("SELECT (SELECT DATE_FORMAT(CAST(rc.rc_timestamp AS CHAR), '%d/%m/%Y')) AS fecha"
				+ ", CAST(rc.rc_user_text AS CHAR) USER, CAST(rc.rc_title AS CHAR) title"
				+ ", CAST(rc.rc_comment AS CHAR) COMMENT, rc.rc_old_len OLD, rc.rc_new_len NEW "
				+ "FROM recentchanges rc WHERE rc.rc_new=0;");
		
		List<DimensionUser> users = listaUsuarios();
		List<DimensionPage> pages = listaPaginas();
		
		for (Object[] c : changesBd) {
			if(c[4] != null && c[5]!=null){
				HechoRecentChange change = new HechoRecentChange();
				change.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(c[0])));
				change.setComment(String.valueOf(c[3]));
				change.setOldLengTH(Integer.parseInt(String.valueOf(c[4])));
				change.setNewLength(Integer.parseInt(String.valueOf(c[5])));
				for(DimensionUser u : users){
					if(u.getUsername().equalsIgnoreCase(String.valueOf(c[1]))){
						change.setUser(u);
						for(DimensionPage p : pages){
							if(p.getTitle().equalsIgnoreCase(String.valueOf(c[2]))){	
								change.setPage(p);
							    changes.add(change);
							}
						}
					}
				}
			}
		}
		return changes;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarChanges(List<HechoRecentChange> changes){		
		if(changes!=null){
			for(HechoRecentChange hc : changes){
				emM.persist(hc);
			}
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void limpiarKmDwh(){		
		emM.createNativeQuery("DELETE FROM hecho_recent_change;").executeUpdate();
		emM.createNativeQuery("DELETE FROM dimension_user;").executeUpdate();
		emM.createNativeQuery("DELETE FROM dimension_page;").executeUpdate();
			
	}
	
	/**
	 * Listar objetos
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> listarWiki(String sql){
		Query q = emMw.createNativeQuery(sql);
		return q.getResultList();
	}
	
}

