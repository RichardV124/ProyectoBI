package beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.AuditoriaConexion;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class AuditoriaConexionEJB {

	@EJB
	private Persistencia em;
	
	private String userAgent = "";
	private String os = "";
	private String browser = "";
	private String user2 = "";
	private String browserDetails = "";

	/**
	 * Identifico el nevegador y el os
	 */
	public void identificarNavegadorPeticion() {

		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}

		// ===============Browser===========================
		if (user2.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user2.contains("safari") && user2.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user2.contains("opr") || user2.contains("opera")) {
			if (user2.contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			else if (user2.contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
		} else if (user2.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user2.indexOf("mozilla/7.0") > -1) || (user2.indexOf("netscape6") != -1)
				|| (user2.indexOf("mozilla/4.7") != -1) || (user2.indexOf("mozilla/4.78") != -1)
				|| (user2.indexOf("mozilla/4.08") != -1) || (user2.indexOf("mozilla/3") != -1)) {
			// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split("
			// ")[0]).replace("/", "-");
			browser = "Netscape-?";

		} else if (user2.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user2.contains("rv")) {
			browser = "IE-" + user2.substring(user2.indexOf("rv") + 3, user2.indexOf(")"));
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}

	}

	/**
	 * Creo la auditoria
	 * @param accion tipo de accion realizada
	 * @param nombreReg nombre del modulo empleado
	 * @param browserDeta os y brows del que se conecta
	 * @param usuario usuario que emplea la accion sobre los registros
	 */
	public void crear(AuditoriaConexion auditoria, int bd, String accion, String browserDeta) {


		this.browserDetails = browserDeta;
		userAgent = browserDetails;
		user2 = userAgent.toLowerCase();

		identificarNavegadorPeticion();

		auditoria.setAccion(accion);
		auditoria.setDispositivo(os);
		auditoria.setNavegador(browser);
		
		Date fecha = new Date();
		auditoria.setFecha(fecha);
		auditoria.setHora(fecha);

		em.crear(auditoria);

	}
	

	/**
	 * metodo para listar las auditorias en una base de datos determinada
	 * @param bd, base de datos de la cual se desea listar
	 * @return una lista con los auditorias registrados
	 */
	public List<AuditoriaConexion> listar(int bd){
		em.setBd(bd);	
		List<AuditoriaConexion> auditorias= (List<AuditoriaConexion>)(Object)em.listar(AuditoriaConexion.CONSULTA_LISTAR_AUDITORIAS);
		if (auditorias.isEmpty()) {
			throw new ExcepcionNegocio("No hay auditorias de acceso registradas en la base de datos");
		} else {
			return auditorias;
		}
		
	}
}

