package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AUDITORIA_ACCESO")
public class AuditoriaAcceso implements Serializable{

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIA_ACCESO_SEQ")
    @SequenceGenerator(sequenceName = "auditoria_acceso_seq", allocationSize = 1, name = "AUDITORIA_ACCESO_SEQ")
	private int id;
	
	@Column(name="INGRESO",nullable=false)
	private boolean ingreso;
	
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
	
	@JoinColumns({ @JoinColumn(name = "ATU_TIPO_USUARIO_ID", referencedColumnName = "ID_TIPO_USUARIO"),
		@JoinColumn(name = "ATU_ACCESO_ID", referencedColumnName = "ID_ACCESO") })
	@ManyToOne
	private AccesoTipoUsuario accessoTipoUsuario;
	
	public AuditoriaAcceso() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIngreso() {
		return ingreso;
	}

	public void setIngreso(boolean ingreso) {
		this.ingreso = ingreso;
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

	public AccesoTipoUsuario getAccessoTipoUsuario() {
		return accessoTipoUsuario;
	}

	public void setAccessoTipoUsuario(AccesoTipoUsuario accessoTipoUsuario) {
		this.accessoTipoUsuario = accessoTipoUsuario;
	}
	
	
}
