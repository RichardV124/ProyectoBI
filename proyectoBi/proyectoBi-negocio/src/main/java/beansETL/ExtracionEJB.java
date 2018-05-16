package beansETL;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Acceso;
import entidades.Auditoria;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class ExtracionEJB {
	
	@EJB
	private Persistencia em;
	
	@EJB
	private Persistencia em2;

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
		em2.setBd(2);
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
}
