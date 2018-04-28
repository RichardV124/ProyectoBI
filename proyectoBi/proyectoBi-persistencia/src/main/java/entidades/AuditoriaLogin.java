package entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AUDITORIA_LOGIN")
public class AuditoriaLogin {

	@Id
	@Column(name="ID")
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
	
	@JoinColumn(name="LOGIN_USERNAME")
	@ManyToOne(cascade={})
	private Login login;
	
	public AuditoriaLogin() {
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

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	
	
}
