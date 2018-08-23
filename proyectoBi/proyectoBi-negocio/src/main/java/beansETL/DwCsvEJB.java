package beansETL;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import etl.HechoAuditoria;
import etl.HechoVenta;
import persistencia.Persistencia;

@Stateless
@LocalBean
public class DwCsvEJB {
	
	@EJB
	private TransformacionEJB transformacionEJB;
	
	public static List<HechoAuditoria> list;
	
	
	public static List<HechoVenta> listVenta;

	
	public List<HechoVenta> listVentaDW() {
		//.setBd(3);
		listVenta = (List<HechoVenta>) (Object) transformacionEJB.listarHechoVenta(HechoVenta.TRAER_VENTA);
		return listVenta;
	}

}
