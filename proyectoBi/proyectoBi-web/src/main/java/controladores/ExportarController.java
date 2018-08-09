package controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omnifaces.util.Faces;

import beans.AuditoriaEJB;
import beansETL.DwCsvEJB;
import entidades.Auditoria;
import etl.GestionETLController;
import etl.HechoVenta;
import session.SessionController;



@Named("controladorVentasDWH")
@SessionScoped
public class ExportarController implements Serializable{
	
	private List<HechoVenta> hechosVenta;

	@EJB
	private DwCsvEJB hechoVentaEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@Inject
	private SessionController sesion;
	

	@PostConstruct
	private void cargarDatos() {
		
		hechosVenta = hechoVentaEJB.listVentaDW();
		
	}

	public void postProcessor(Object document) {

		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header;

		int index = 0;

		for (int j = 1; j < hechosVenta.size()+1; j++) {
			header = sheet.getRow(j);

			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				
				HSSFCell cell = header.getCell(i);

				if (i == 0) {
					cell.setCellValue(hechosVenta.get(index).getProducto().getId());
				}
				
				if (i == 2){
					cell.setCellValue(hechosVenta.get(index).getProducto().getValor());
				}
				
				
				if (i == 3){
					cell.setCellValue(hechosVenta.get(index).getProducto().getLote());
				}
				
				if (i == 5){
					cell.setCellValue(hechosVenta.get(index).getId());
				}
				if (i == 6){
					cell.setCellValue(hechosVenta.get(index).getCantidad());
				}
				if (i == 8){
					cell.setCellValue(hechosVenta.get(index).getUsuario().getId());
				}
				if (i == 10){
					cell.setCellValue(hechosVenta.get(index).getUsuario().getEdad());
				}
				if (i == 11){
					cell.setCellValue(hechosVenta.get(index).getUsuario().getSalario());
				}
				if (i == 12){
					cell.setCellValue(hechosVenta.get(index).getCliente().getId());
				}

			}
			
			index++;

		}

	}

	public List<HechoVenta> getHechosVenta() {
		return hechosVenta;
	}

	public void setHechosVenta(List<HechoVenta> hechosVenta) {
		this.hechosVenta = hechosVenta;
	}
	
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}
	
	public void crearAditoria(){
		crearAuditoria("HechoVenta","CSV","Exportacion", 2);
	}
	
	public void crearAditoriaEx(){
		crearAuditoria("HechoVenta","Excel","Exportacion", 2);
	}
	
	
}
