package beansETL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Auditoria;
import entidades.DetalleVenta;
import etl.DimensionCliente;
import etl.DimensionProducto;
import etl.DimensionUsuario;
import etl.HechoAuditoria;
import etl.HechoVenta;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class TransformacionEJB {
	
	/**
	 * Instancia a MySQL
	 */
	@PersistenceContext(unitName = "mysql")
	private EntityManager emM;
	
	/**
	 * Metodo para la transformacion de las auditoias
	 * @param auds
	 * @return
	 */
	public List<HechoAuditoria> transformarAuditorias(List<Auditoria> auds){
		List<HechoAuditoria> auditorias = new ArrayList<HechoAuditoria>();
		for(Auditoria a : auds){
			HechoAuditoria ha = new HechoAuditoria();
			ha.setAccion(a.getAccion());
			ha.setDispositivo(a.getDispositivo());
			ha.setEntidad(a.getEntidad());
			ha.setFecha(a.getFecha());
			ha.setHora(a.getHora());
			ha.setId(a.getId());
			ha.setNavegador(a.getNavegador());
			ha.setObjetoAuditado(a.getObjetoAuditado());
			auditorias.add(ha);
		}
		return auditorias;
	}
	
	/**
	 * Reglas de negocio
	 * -> Se debe calcular el valor total de la venta para que se tenga en cuenta en el DWH
	 * -> Se debe Ignorar el nombre y el apellido de los usuarios ya qu eno será utiles para 
	 * 		los reportes en el DWH
	 * -> Se debe calcular el valor total de la venta para que se tenga en cuenta en el DWH
	 * -> Se debe calcular la edad del usuario a partir de su fecha de nacimiento
	 * -> Se debe calcular la edad del Cliente a partir de su fecha de nacimiento
	 * -> Se debe Agrupar la informacion de Venta y DetalleVenta en una sola tabla de hecho
	 * @param listaVentas
	 * @return
	 */
	public List<HechoVenta> transformarVentas(List<DetalleVenta> listaVentas){
		List<HechoVenta> ventas = new ArrayList<HechoVenta>();
		for(DetalleVenta dv : listaVentas){
			HechoVenta hv = new HechoVenta();
			hv.setFecha(dv.getVenta().getFecha());
			hv.setCantidad(dv.getCantidad());
			
			//calculamos el valor total de la venta
			double valorTotal = dv.getCantidad()*dv.getProducto().getValor();
			hv.setValorTotal(valorTotal);
			
			//creamos la dimension Usuario
			DimensionUsuario du = new DimensionUsuario();
			du.setAreaEmpresa(dv.getVenta().getUsuario().getAreaEmpresa().getNombre());
			du.setCargo(dv.getVenta().getUsuario().getCargo().getNombre());
			du.setDepartamento(dv.getVenta().getUsuario().getMunicipio().getDepartamento().getNombre());
			du.setGenero(dv.getVenta().getUsuario().getGenero().getNombre());
			du.setMunicipio(dv.getVenta().getUsuario().getMunicipio().getNombre());
			du.setSalario(dv.getVenta().getUsuario().getSalario());
			du.setTipoUsuario(dv.getVenta().getUsuario().getTipoUsuario().getNombre());
			//Calculamos la edad del usuario
			int edad = calculaEdad(dv.getVenta().getUsuario().getfechaNacimiento());
			du.setEdad(edad);
			//asignamos la dimension
			hv.setUsuario(du);
			

			//creamos la dimension Cliente
			DimensionCliente dc = new DimensionCliente();
			dc.setDepartamento(dv.getVenta().getCliente().getMunicipio().getDepartamento().getNombre());
			dc.setGenero(dv.getVenta().getCliente().getGenero().getNombre());
			dc.setMunicipio(dv.getVenta().getCliente().getMunicipio().getNombre());
			//Calculamos la edad del usuario
			int edadc = calculaEdad(dv.getVenta().getCliente().getFechaNacimiento());
			dc.setEdad(edadc);
			//asignamos la dimension
			hv.setCliente(dc);
			
			//creamos la dimension Producto
			DimensionProducto dp = new DimensionProducto();
			dp.setCantidad(dv.getProducto().getCantidad());
			dp.setFechaIngreso(dv.getProducto().getFechaIngreso());
			dp.setLote(dv.getProducto().getLote().getDescripcion());
			dp.setNombre(dv.getProducto().getNombre());
			dp.setTipoProducto(dv.getProducto().getTipoProducto().getNombre());
			dp.setValor(dv.getProducto().getValor());
			//asignamos la dimension
			hv.setProducto(dp);
			
			ventas.add(hv);
		}
		return ventas;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cargar(List<HechoAuditoria> audis, List<HechoVenta> ventas){
		if(audis != null){
			emM.createNativeQuery("DELETE FROM hecho_auditoria;").executeUpdate();
			for(HechoAuditoria ha : audis){
				emM.createNativeQuery("INSERT INTO hecho_auditoria (ACCION, DISPOSITIVO, "
						+ "ENTIDAD, FECHA, HORA, NAVEGADOR, OBJETO_AUDITADO) "
						+ "VALUES ('"+ha.getAccion()+"', '"+ha.getDispositivo()+"', '"+ha.getEntidad()+
						"', STR_TO_DATE('"+ha.getFecha()+"', '%Y-%m-%d'), '"+ha.getHora()+"', '"+ha.getNavegador()+"', '"+ha.getObjetoAuditado()+
						"');").executeUpdate();
				
			}
		}
		
		if(ventas!= null){
			emM.createNativeQuery("DELETE FROM hecho_venta;").executeUpdate();
			emM.createNativeQuery("DELETE FROM dimension_producto;").executeUpdate();
			emM.createNativeQuery("DELETE FROM dimension_cliente;").executeUpdate();
			emM.createNativeQuery("DELETE FROM dimension_usuario;").executeUpdate();
			for(HechoVenta hv : ventas){
				emM.persist(hv);
			}
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cargarAcumulacionSimple(List<HechoAuditoria> audis, List<HechoVenta> ventas){
		if(audis != null){
			for(HechoAuditoria ha : audis){
				emM.createNativeQuery("INSERT INTO hecho_auditoria (ACCION, DISPOSITIVO, "
						+ "ENTIDAD, FECHA, HORA, NAVEGADOR, OBJETO_AUDITADO) "
						+ "VALUES ('"+ha.getAccion()+"', '"+ha.getDispositivo()+"', '"+ha.getEntidad()+
						"', STR_TO_DATE('"+ha.getFecha()+"', '%Y-%m-%d'), '"+ha.getHora()+"', '"+ha.getNavegador()+"', '"+ha.getObjetoAuditado()+
						"');").executeUpdate();
				
			}
		}
		
		if(ventas!= null){
			for(HechoVenta hv : ventas){
				emM.persist(hv);
			}
		}
		
	}
	
	/**
	 * metodo para calcular la edad a partir de un calendar
	 * @param fechaNac
	 * @return
	 */	
	private int calculaEdad(Date fechaNac) {
        Date today = new Date();

        int diff_year = today.getYear() -  fechaNac.getYear();
        int diff_month = today.getMonth() - fechaNac.getMonth();
        int diff_day = today.getDay() - fechaNac.getDay();

        //Si está en ese año pero todavía no los ha cumplido
        if (diff_month < 0 || (diff_month == 0 && diff_day < 0)) {
            diff_year = diff_year - 1; //no aparecían los dos guiones del postincremento :|
        }
        return diff_year;
    }
}
