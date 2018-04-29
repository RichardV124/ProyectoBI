package controladores;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Encoded;

import org.omnifaces.cdi.ViewScoped;

import beans.UsuarioEJB;
import entidades.AreaEmpresa;
import entidades.Departamento;
import entidades.Genero;
import entidades.Municipio;

@ViewScoped
@Named("registroUsuarioController")
public class RegistroUsuarioController implements Serializable{

	@EJB
	private UsuarioEJB usuarioEJB;
	
	@NotNull(message = "Debe ingresar la cedula")
	private String cedula;
	
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe ingresar el apellido")
	private String apellido;
	
	@NotNull(message = "Debe ingresar la fecha")
	private Date fechaNacimiento;
	
	private Genero generoSeleccionado;
	
	private List<Genero> listaGeneros;
	
	private Municipio municipioSeleccionado;
	
	private List<Municipio> listaMunicipios;
	
	private Departamento dptoSeleccionado;
	
	private List<Departamento> listaDptos;
	
	@NotNull(message = "Debe ingresar su nickname")
	private String username;
	
	@NotNull(message = "Debe ingresar la contraseña")
	private String password;
	
	@NotNull(message = "Debe confirmar la contraseña")
	private String confirmarpassword;
	
	
	public void registrar(){
		
		limpiar();
	}
	
	public void limpiar(){
		cedula ="";
		nombre ="";
		apellido ="";
		fechaNacimiento =null;
		generoSeleccionado= null;
		municipioSeleccionado=null;
		dptoSeleccionado=null;
		username="";
		password="";
		confirmarpassword="";
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Genero getGeneroSeleccionado() {
		return generoSeleccionado;
	}

	public void setGeneroSeleccionado(Genero generoSeleccionado) {
		this.generoSeleccionado = generoSeleccionado;
	}

	public List<Genero> getListaGeneros() {
		return listaGeneros;
	}

	public void setListaGeneros(List<Genero> listaGeneros) {
		this.listaGeneros = listaGeneros;
	}

	public Municipio getMunicipioSeleccionado() {
		return municipioSeleccionado;
	}

	public void setMunicipioSeleccionado(Municipio municipioSeleccionado) {
		this.municipioSeleccionado = municipioSeleccionado;
	}

	public List<Municipio> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<Municipio> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public Departamento getDptoSeleccionado() {
		return dptoSeleccionado;
	}

	public void setDptoSeleccionado(Departamento dptoSeleccionado) {
		this.dptoSeleccionado = dptoSeleccionado;
	}

	public List<Departamento> getListaDptos() {
		return listaDptos;
	}

	public void setListaDptos(List<Departamento> listaDptos) {
		this.listaDptos = listaDptos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmarpassword() {
		return confirmarpassword;
	}

	public void setConfirmarpassword(String confirmarpassword) {
		this.confirmarpassword = confirmarpassword;
	}
		
}
