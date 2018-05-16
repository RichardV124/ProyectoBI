package beansETL;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Auditoria;
import entidades.DetalleVenta;
import etl.DimensionCliente;
import etl.DimensionProducto;
import etl.DimensionUsuario;
import etl.HechoAuditoria;
import etl.HechoVenta;

@LocalBean
@Stateless
public class TransformacionEJB {

	
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
			du.setFechaNacimiento(dv.getVenta().getUsuario().getfechaNacimiento());
			du.setGenero(dv.getVenta().getUsuario().getGenero().getNombre());
			du.setMunicipio(dv.getVenta().getUsuario().getMunicipio().getNombre());
			du.setSalario(dv.getVenta().getUsuario().getSalario());
			du.setTipoUsuario(dv.getVenta().getUsuario().getTipoUsuario().getNombre());
			//asignamos la dimension
			hv.setUsuario(du);
			

			//creamos la dimension Cliente
			DimensionCliente dc = new DimensionCliente();
			dc.setDepartamento(dv.getVenta().getCliente().getMunicipio().getDepartamento().getNombre());
			dc.setFechaNacimiento(dv.getVenta().getCliente().getFechaNacimiento());
			dc.setGenero(dv.getVenta().getCliente().getGenero().getNombre());
			dc.setMunicipio(dv.getVenta().getCliente().getMunicipio().getNombre());
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
}
