package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.DetalleVenta;
import entidades.DetalleVentaPK;
import entidades.Venta;
import persistencia.Persistencia;


@LocalBean
@Stateless
public class DetalleVentaEJB {

	@EJB
	private Persistencia em;
	
	public DetalleVenta buscar(DetalleVentaPK pk,int bd) {
		em.setBd(bd);
		return (DetalleVenta) em.buscar(DetalleVenta.class, pk);
	}

	/**
	 * Registra un detalle venta en la base de datos
	 * 
	 * @param dv
	 *            detalle venta que se desea registrar
	 */
	public void registrarDetalleVenta(List<DetalleVenta> detalles, Venta venta,int bd) {
		em.setBd(bd);
		em.registrarDetalleVenta(detalles, venta);
	}

}