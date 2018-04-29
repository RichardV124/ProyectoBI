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

import beans.DepartamentoEJB;
import beans.GeneroEJB;
import beans.MunicipioEJB;
import beans.UsuarioEJB;
import entidades.AreaEmpresa;
import entidades.Departamento;
import entidades.Genero;
import entidades.Login;
import entidades.Municipio;
import entidades.TipoUsuario;
import entidades.Usuario;

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
	
	public void registrar(){
		
		Usuario usu = usuarioEJB.buscar(cedula, 2);
		if(usu!=null){
			
			Usuario u =  new Usuario();
			u.setCedula(cedula);
			u.setNombre(nombre);
			u.setApellido(apellido);
			
			Genero gen = generoEJB.buscar(2, generoSeleccionado);
			u.setGenero(gen);
			
			AreaEmpresa area = new AreaEmpresa();
			u.setAreaEmpresa(area);
			
			u.setfechaNacimiento(fechaNacimiento);
			u.setSalario(0);
			
			TipoUsuario tipo = new TipoUsuario();
			u.setTipoUsuario(tipo);
			
			Municipio mun = municipioEJB.buscar(2, municipioSeleccionado);
			u.setMunicipio(mun);
			
//			Login login = 
			
			usuarioEJB.crear(u, 2);
		}
		
		limpiar();
	}
	
	public void llenarCombos(){
		listaDptos = departamentoEJB.listar(2);
		listaGeneros = generoEJB.listar(2);
		listaMunicipios = municipioEJB.listar(2, listaDptos.get(0));
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
	
	
		
}
