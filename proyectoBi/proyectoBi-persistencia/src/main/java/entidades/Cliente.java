package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTE")
@NamedQueries({
	@NamedQuery(name=Cliente.LISTA_PERSONA, query="SELECT p FROM Cliente p")
})
public class Cliente implements Serializable{
	
	public static final String LISTA_PERSONA = "lista.persona";
	
	@Id
	@Column(name = "CEDULA", nullable = false)
	private String cedula;

	@Column(name = "NOMBRE", nullable = false)
	protected String nombre;

	@Column(name = "APELLIDO", nullable = false)
	protected String apellido;

	@Column(name = "TELEFONO")
	protected String telefono;

	@Column(name = "CORREO")
	protected String correo;

	@Column(name = "FECHA_NACIMIENTO")
	protected String fechaNacimiento;

	@JoinColumn(name="MUNICIPIO_ID")
	@ManyToOne(cascade = {})
	private Municipio municipio;

	@JoinColumn(name="GENERO_ID")
	@ManyToOne(cascade = {})
	private Genero genero;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(String cedula, String nombre, String apellido, String telefono, String correo, String fechaNacimiento,
			Municipio municipio, Genero genero) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
		this.municipio = municipio;
		this.genero = genero;
	}

	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Genero getGenero() {
		return genero;
	}


	public void setGenero(Genero genero) {
		this.genero = genero;
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

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}


}
