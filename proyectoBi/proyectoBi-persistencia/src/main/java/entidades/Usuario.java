package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="USUARIO")
@NamedQueries({ 
	@NamedQuery(name=Usuario.CONSULTA_LISTAR_RECURSOS_HUMANOS,query="SELECT u FROM Usuario u WHERE fechaIngreso is not null"),
	@NamedQuery(name=Usuario.CONSULTA_LISTAR_USUARIOS_INACTIVOS,query="SELECT u FROM Usuario u WHERE u.login.activo=?1"),
	@NamedQuery(name=Usuario.buscarPorUsername,query="SELECT u FROM Usuario u WHERE u.login.username=?1"),
	@NamedQuery(name=Usuario.buscarCliente,query="SELECT u FROM Usuario u WHERE u.cedula=?1 and u.cargo.id='"+3+"'"),
	@NamedQuery(name = Usuario.CONSULTA_LISTAR_USUARIOS, query = "SELECT usu FROM Usuario usu WHERE fechaIngreso is null"),
	@NamedQuery(name = Usuario.CONSULTA_LISTAR_TIPO_USUARIO, query = "SELECT u "
			+ "FROM Usuario u WHERE u.tipo=?1")
	})
public class Usuario implements Serializable{
	
	public static final String buscarPorUsername = "Usuario.buscarPorUsername";
	public static final String buscarCliente = "Usuario.buscarCliente";
	
	public static final String CONSULTA_LISTAR_USUARIOS = "Usuario.listarUsuarios";
	public static final String CONSULTA_LISTAR_USUARIOS_INACTIVOS = "Usuario.listarUsuariosInactivos";
	public static final String CONSULTA_LISTAR_TIPO_USUARIO = "Usuario.listarTipoUsuario";
	public static final String CONSULTA_LISTAR_RECURSOS_HUMANOS = "Usuario.listarRecurosHumanos";
	
	@Id
	@Column(name="CEDULA",length=40,nullable=false)
	private String cedula;
	
	@Column(name="NOMBRE",length=40,nullable=false)
	private String nombre;
	
	@Column(name="APELLIDO",length=40,nullable=false)
	private String apellido;
	
	
	@Column(name="FECHA_NACIMIENTO",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@JoinColumn(name="CARGO_ID")
	@ManyToOne
	private Cargo cargo;
	
	@JoinColumn(name="MUNICIPIO_ID")
	@ManyToOne(cascade = {})
	private Municipio municipio;
	
	@JoinColumn(name="DEPARTAMENTO_ID", nullable=true)
	@ManyToOne(cascade = {})
	private Departamento departamento;
	
	@Column(name="FECHA_INGRESO",nullable=true)
	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;
	
	@JoinColumn(name="AREA_EMPRESA_ID", nullable=true)
	@ManyToOne(cascade = {})
	private AreaEmpresa areaEmpresa;
	
	@JoinColumn(name="TIPO_USUARIO_ID", nullable=true)
	@ManyToOne(cascade = {})
	private TipoUsuario tipoUsuario;
	
	@Column(name="SALARIO",nullable=true)
	private double salario;
	
	@JoinColumn(name="GENERO_ID")
	@ManyToOne(cascade = {})
	private Genero genero;
	
	@Column(name="TIPO",nullable=true)
	private String tipo;
	
	@JoinColumn(name="LOGIN_USERNAME", nullable=true)
	@OneToOne(cascade=CascadeType.ALL)
	private Login login;
	
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}


	public String getCedula() {
		return cedula;
	}


	public void setCedula(String cedula) {
		this.cedula = cedula;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public Date getfechaNacimiento() {
		return fechaNacimiento;
	}


	public void setfechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Municipio getMunicipio() {
		return municipio;
	}


	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}


	public AreaEmpresa getAreaEmpresa() {
		return areaEmpresa;
	}


	public void setAreaEmpresa(AreaEmpresa areaEmpresa) {
		this.areaEmpresa = areaEmpresa;
	}


	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}


	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	public double getSalario() {
		return salario;
	}


	public void setSalario(double salario) {
		this.salario = salario;
	}


	public Genero getGenero() {
		return genero;
	}


	public void setGenero(Genero genero) {
		this.genero = genero;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Login getLogin() {
		return login;
	}


	public void setLogin(Login login) {
		this.login = login;
	}


	public Cargo getCargo() {
		return cargo;
	}


	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}


	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	

	
}
