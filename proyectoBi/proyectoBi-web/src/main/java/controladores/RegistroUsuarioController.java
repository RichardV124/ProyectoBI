package controladores;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Encoded;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import beans.DepartamentoEJB;
import beans.GeneroEJB;
import beans.LoginEJB;
import beans.MunicipioEJB;
import beans.UsuarioEJB;
import entidades.AreaEmpresa;
import entidades.Departamento;
import entidades.Genero;
import entidades.Login;
import entidades.Municipio;
import entidades.TipoUsuario;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;

@ViewScoped
@Named("registroUsuarioController")
public class RegistroUsuarioController implements Serializable{

	@EJB
	private UsuarioEJB usuarioEJB;
	
	@EJB
	private DepartamentoEJB departamentoEJB;
	
	@EJB
	private MunicipioEJB municipioEJB;
	
	@EJB
	private GeneroEJB generoEJB;
	
	@EJB
	private LoginEJB loginEJB;
	
	@NotNull(message = "Debe ingresar la cedula")
	private String cedula;
	
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe ingresar el apellido")
	private String apellido;
	
	@NotNull(message = "Debe ingresar la fecha")
	private Date fechaNacimiento;
	
	private int generoSeleccionado;
	
	private List<Genero> listaGeneros;
	
	private int municipioSeleccionado;
	
	private List<Municipio> listaMunicipios;
	
	private int dptoSeleccionado;
	
	private List<Departamento> listaDptos;
	
	@NotNull(message = "Debe ingresar su nickname")
	private String username;
	
	@NotNull(message = "Debe ingresar la contraseña")
	private String password;
	
	@NotNull(message = "Debe confirmar la contraseña")
	private String confirmarpassword;
	
	@PostConstruct
	public void inicializar(){
		// TODO Auto-generated constructor stub
		llenarCombos();
	}
	
	public void registrar() {

		try {
			Usuario u = new Usuario();
			u.setCedula(cedula);
			u.setNombre(nombre);
			u.setApellido(apellido);

			Genero gen = generoEJB.buscar(2, generoSeleccionado);
			u.setGenero(gen);
			u.setAreaEmpresa(null);

			u.setfechaNacimiento(fechaNacimiento);
			u.setSalario(0);
			u.setTipoUsuario(null);

			Municipio mun = municipioEJB.buscar(2, municipioSeleccionado);
			u.setMunicipio(mun);

			Login login = new Login();
			login.setActivo(false);
			login.setUsername(username);
			login.setPassword(password);
			loginEJB.crear(login, 2);
			u.setLogin(login);

			usuarioEJB.crear(u, 2);
			limpiar();
			Messages.addFlashGlobalInfo("Registro exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}

	}
	
	public void llenarCombos(){
		try{
			listaDptos = departamentoEJB.listar(2);
			listaGeneros = generoEJB.listar(2);
			listaMunicipios = municipioEJB.listar(2, listaDptos.get(0));
		}catch(ExcepcionNegocio e){
			
		}
	}
	
	public void listarMunicipios(){
		if(dptoSeleccionado!=0){
			Departamento dpto = departamentoEJB.buscar(2, dptoSeleccionado);
			listaMunicipios = municipioEJB.listar(2, dpto);	
		}
	}
	
	public void limpiar(){
		cedula ="";
		nombre ="";
		apellido ="";
		fechaNacimiento =null;
		username="";
		password="";
		confirmarpassword="";
		listaMunicipios = municipioEJB.listar(2, listaDptos.get(0));
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

	public int getGeneroSeleccionado() {
		return generoSeleccionado;
	}

	public void setGeneroSeleccionado(int generoSeleccionado) {
		this.generoSeleccionado = generoSeleccionado;
	}

	public List<Genero> getListaGeneros() {
		return listaGeneros;
	}

	public void setListaGeneros(List<Genero> listaGeneros) {
		this.listaGeneros = listaGeneros;
	}

	public int getMunicipioSeleccionado() {
		return municipioSeleccionado;
	}

	public void setMunicipioSeleccionado(int municipioSeleccionado) {
		this.municipioSeleccionado = municipioSeleccionado;
	}

	public List<Municipio> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<Municipio> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public int getDptoSeleccionado() {
		return dptoSeleccionado;
	}

	public void setDptoSeleccionado(int dptoSeleccionado) {
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
