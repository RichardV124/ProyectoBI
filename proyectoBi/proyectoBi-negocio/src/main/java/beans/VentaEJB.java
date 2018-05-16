package beans;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import entidades.TipoProducto;
import entidades.Venta;
import persistencia.Persistencia;


@LocalBean
@Stateless
public class VentaEJB {

	@EJB
	private Persistencia em;

	/**
	 * Se registra la venta en la base de datos
	 * 
	 * @param v
	 *            venta que se desea registrar
	 */
	public void crear(Venta v, int bd){
		em.setBd(bd);
		em.crear(v);
	}

//	/**
//	 * Obtiene el último código de la factura generada a un cliente
//	 * 
//	 * @param cedulaCliente
//	 *            cédula del clienteal cual se le generó la factura
//	 * @return el id de la última factura
//	 */
//	public int codigoUltimaFacturaCliente(String cedulaCliente) {
//		em.setBd(ConexionEJB.getBd());
//		return em.codigoUltimaFacturaCliente(cedulaCliente);
//	}

	public List<Venta> listarVentasPorFecha(String fecha,int bd) {
		em.setBd(bd);
		return (List<Venta>) (Object) em.listarConParametroString(Venta.LISTAR_POR_FECHA, fecha);
	}

	public List<DetalleVenta> listarDetallesVenta(Venta venta,int bd) {
		em.setBd(bd);
		return (List<DetalleVenta>) (Object) em.listarConParametroObjeto(DetalleVenta.LISTAR_DETALLES_FACTURA, venta);
	}

	public void eliminarDetalleVenta(DetalleVenta dv,int bd) {
		em.setBd(bd);
		em.eliminarDetalleVenta(dv);
	}

	public void eliminarVenta(Venta v, int bd) {
		em.setBd(bd);
		em.eliminar(v);
	}

	public String obtenerFechaActual() {

		Calendar fechaActual = new GregorianCalendar();
		int dia = fechaActual.get(Calendar.DAY_OF_MONTH);
		int mes = fechaActual.get(Calendar.MONTH) + 1;
		int anio = fechaActual.get(Calendar.YEAR);

		String diaActual = String.valueOf(dia);
		String mesActual = String.valueOf(mes);

		if (dia <= 9) {
			diaActual = "0" + dia;
		}

		if (mes <= 9) {
			mesActual = "0" + mes;
		}

		String nuevaFecha = diaActual + "/" + mesActual + "/" + anio;

		return nuevaFecha;
	}

}
