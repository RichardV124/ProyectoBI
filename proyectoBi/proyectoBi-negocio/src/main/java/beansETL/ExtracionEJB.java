package beansETL;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Acceso;
import entidades.Auditoria;
import entidades.DetalleVenta;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class ExtracionEJB {
	
	@EJB
	private Persistencia em;

	/**
	 * Listar Auditorias de la base de datos oracle
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<Auditoria> listarAuditoriasOracle(){
		em.setBd(1);
		List<Auditoria> auditorias= (List<Auditoria>)(Object)em.listar(Auditoria.CONSULTA_LISTAR_AUDITORIAS);
		if (auditorias.isEmpty()) {
			throw new ExcepcionNegocio("No hay auditorias registradas en la BD Oracle");
		} else {
			return auditorias;
		}
	}
	
	/**
	 * Listar Auditorias de la base de datos oracle
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<Auditoria> listarAuditoriasPostgres(){
		em.setBd(2);
		List<Auditoria> auditorias= (List<Auditoria>)(Object)em.listar(Auditoria.CONSULTA_LISTAR_AUDITORIAS);
		if (auditorias.isEmpty()) {
			throw new ExcepcionNegocio("No hay auditorias registradas en la BD Oracle");
		} else {
			return auditorias;
		}
	}
	
	/**
	 * Listar Auditorias
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<Auditoria> listarAuditorias(){
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			List<Auditoria> listaP = listarAuditoriasPostgres();
			for(Auditoria a : listaP){
				lista.add(a);
			}
		} catch (ExcepcionNegocio e) {

		}
		try {
			List<Auditoria> listaO = listarAuditoriasOracle();
			for(Auditoria a : listaO){
				lista.add(a);
			}
		} catch (ExcepcionNegocio e) {

		}
		
		return lista;
	}
	
	
	/**
	 * Listar Auditorias de la base de datos oracle
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<Auditoria> listarAuditoriasFechaOracle(Date fechaI, Date fechaF){
		em.setBd(1);
		List<Auditoria> auditorias= (List<Auditoria>)(Object)em.listarConDosParametro(Auditoria.CONSULTA_LISTAR_AUDITORIAS_FECHA, fechaI, fechaF);
		if (auditorias.isEmpty()) {
			throw new ExcepcionNegocio("No hay auditorias registradas en la BD Oracle");
		} else {
			return auditorias;
		}
	}
	
	/**
	 * Listar Auditorias de la base de datos oracle
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<Auditoria> listarAuditoriasFechaPostgres(Date fechaI, Date fechaF){
		em.setBd(2);
		List<Auditoria> auditorias= (List<Auditoria>)(Object)em.listarConDosParametro(Auditoria.CONSULTA_LISTAR_AUDITORIAS_FECHA, fechaI, fechaF);
		if (auditorias.isEmpty()) {
			throw new ExcepcionNegocio("No hay auditorias registradas en la BD Oracle");
		} else {
			return auditorias;
		}
	}
	
	/**
	 * Listar Auditorias
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<Auditoria> listarAuditoriasFecha(Date fechaI, Date fechaF){
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			List<Auditoria> listaP = listarAuditoriasFechaPostgres(fechaI, fechaF);
			for(Auditoria a : listaP){
				lista.add(a);
			}
		} catch (ExcepcionNegocio e) {

		}
		try {
			List<Auditoria> listaO = listarAuditoriasFechaOracle(fechaI, fechaF);
			for(Auditoria a : listaO){
				lista.add(a);
			}
		} catch (ExcepcionNegocio e) {

		}
		
		return lista;
	}
	
	
	/**
	 * Listar Ventas de la base de datos oracle
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<DetalleVenta> listarVentasOracle(){
		em.setBd(1);
		List<DetalleVenta> ventas= (List<DetalleVenta>)(Object)em.listar(DetalleVenta.CONSULTA_LISTAR);
		if (ventas.isEmpty()) {
			throw new ExcepcionNegocio("No hay ventas registradas en la BD Oracle");
		} else {
			return ventas;
		}
	}
	
	/**
	 * Listar Auditorias de la base de datos oracle
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<DetalleVenta> listarVentasPostgres(){
		em.setBd(2);
		List<DetalleVenta> ventas= (List<DetalleVenta>)(Object)em.listar(DetalleVenta.CONSULTA_LISTAR);
		if (ventas.isEmpty()) {
			throw new ExcepcionNegocio("No hay ventas registradas en la BD Oracle");
		} else {
			return ventas;
		}
	}
	
	/**
	 * Listar ventas
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de auditorias
	 */
	public List<DetalleVenta> listarVentas(){
		List<DetalleVenta> lista = new ArrayList<DetalleVenta>();
		try {
			List<DetalleVenta> listaP = listarVentasPostgres();
			for(DetalleVenta a : listaP){
				lista.add(a);
			}
		} catch (ExcepcionNegocio e) {

		}
		try {
			List<DetalleVenta> listaO = listarVentasOracle();
			for(DetalleVenta a : listaO){
				lista.add(a);
			}
		} catch (ExcepcionNegocio e) {

		}
		
		return lista;
	}
}
