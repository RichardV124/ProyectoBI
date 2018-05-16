package etl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIMENSION_USUARIO")
public class DimensionUsuario implements Serializable{

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIMENSION_USUARIO_SEQ")
    @SequenceGenerator(sequenceName = "dimension_usuario_seq", allocationSize = 1, name = "DIMENSION_USUARIO_SEQ")
	private int id;
	
	@Column(name="FECHA_NACIMIENTO")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Column(name="MUNICIPIO")
	private String municipio;
	
	@Column(name="DEPARTAMENTO")
	private String departamento;
	
	@Column(name="GENERO")
	private String genero;
	
	@Column(name="AREA_EMPRESA", nullable=true)
	private String areaEmpresa;
	
	@Column(name="TIPO_USUARIO", nullable=true)
	private String tipoUsuario;
	
	@Column(name="CARGO", nullable=true)
	private String cargo;
	
	@Column(name="SALARIO")
	private double salario;
	
	public DimensionUsuario() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getAreaEmpresa() {
		return areaEmpresa;
	}

	public void setAreaEmpresa(String areaEmpresa) {
		this.areaEmpresa = areaEmpresa;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	
}
