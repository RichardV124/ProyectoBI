package etl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import beans.AuditoriaEJB;
import beansETL.AnalisisEJB;
import entidades.Auditoria;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionAnalisisController")
public class GestionAnalisisController implements Serializable {

	private String imagenAnalisis;

	private Date fechaResultado;

	@EJB
	private AuditoriaEJB auditoriaEJB;

	@EJB
	private AnalisisEJB analisisEJB;

	@Inject
	private SessionController sesion;

	private Analisis analisis;
	
	private UploadedFile file;
	
	public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	
	public void aaa(){
		String a = "a";
		int b = 0;
	}

	@PostConstruct
	public void inicializar() {
		// TODO Auto-generated constructor stub
		analisis = new Analisis();
	}


	public void crearAuditoria(String entidad, String objeto, String accion, int bd) {
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}

	public String guardaBlobEnFicheroTemporal(byte[] bytes, String nombreArchivo) {
		String ubicacionImagen = null;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String path = servletContext.getRealPath("") + File.separatorChar + "resources" + File.separatorChar + "img"
				+ File.separatorChar + "tmp" + File.separatorChar + nombreArchivo;

		File f = null;
		InputStream in = null;

		try {

			f = new File(path);
			in = new ByteArrayInputStream(bytes);
			FileOutputStream out = new FileOutputStream(f.getAbsolutePath());

			int c = 0;
			while ((c = in.read()) >= 0) {
				out.write(c);
			}
			out.flush();
			out.close();
			ubicacionImagen = "../../resources/img/tmp/" + nombreArchivo;

		} catch (Exception e) {

			// TODO: handle exception
			e.printStackTrace();
			Messages.addFlashGlobalInfo("No se pudo cargar la imagen");

		}
		return ubicacionImagen;
	}

	public void subirImagen(FileUploadEvent event) {

		try {
			analisis.setResultado(event.getFile().getContents());
			imagenAnalisis = guardaBlobEnFicheroTemporal(analisis.getResultado(), event.getFile().getFileName());
			Messages.addFlashGlobalInfo("Se subio la imagen correctamente");
		} catch (Exception e) {
			Messages.addFlashGlobalInfo("Problemas al subir la imagen");
		}
	}


	public void crearAnalisis() {
		try {

			analisis.setFecha(fechaResultado);

			analisisEJB.crear(analisis, sesion.getBd());

			// creando la auditoria
			crearAuditoria("Analisis", "Objeto", "Crear", sesion.getBd());
			analisis = new Analisis();

			Messages.addFlashGlobalInfo("Registro exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void buscarAnalisis(){
		try{
			analisis = analisisEJB.buscarPorFecha(fechaResultado, sesion.getBd());
		
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
		
		catch(Exception e){
			Messages.addFlashGlobalInfo("Problemas al buscar el analisis");
		}
	}

	public String getImagenAnalisis() {
		return imagenAnalisis;
	}

	public void setImagenAnalisis(String imagenAnalisis) {
		this.imagenAnalisis = imagenAnalisis;
	}

	public Date getFechaResultado() {
		return fechaResultado;
	}

	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}

	public Analisis getAnalisis() {
		return analisis;
	}

	public void setAnalisis(Analisis analisis) {
		this.analisis = analisis;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	
	

}
