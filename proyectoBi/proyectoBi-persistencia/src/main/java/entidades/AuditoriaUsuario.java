package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AUDITORIA_USUARIO")
@NamedQueries({ 
	@NamedQuery(name = AuditoriaUsuario.CONSULTA_LISTAR_AUDITORIAS, query = "SELECT au FROM AuditoriaUsuario au") 
	})
public class AuditoriaUsuario implements Serializable{

	public static final String CONSULTA_LISTAR_AUDITORIAS = "AuditoriaUsuario.ListarAuditorias";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIA_USUARIO_SEQ")
    @SequenceGenerator(sequenceName = "auditoria_usuario_seq", allocationSize = 1, name = "AUDITORIA_USUARIO_SEQ")
	private int id;
	
	@Column(name="ACCION",length=40,nullable=false)
	private String accion;
	
	@Column(name="NAVEGADOR",length=40, nullable=false)
	private String navegador;

	@Column(name="DISPOSITIVO",length=40,nullable=false)
	private String dispositivo;
	
	@Column(name="FECHA",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(name="HORA",nullable=false)
	@Temporal(TemporalType.TIME)
	private Date hora;
	
	@Column(name="ENTIDAD", nullable=false)
	private String entidad;
	
	@Column(name="OBJETO_AUDITADO", nullable=false)
	private String objetoAuditado;
	
	public AuditoriaUsuario() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getNavegador() {
		return navegador;
	}

	public void setNavegador(String navegador) {
		this.navegador = navegador;
	}

	public String getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getObjetoAuditado() {
		return objetoAuditado;
	}

	public void setObjetoAuditado(String objetoAuditado) {
		this.objetoAuditado = objetoAuditado;
	}
	
	
	
}
