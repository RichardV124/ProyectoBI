package controladores;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import beans.AreaEmpresaEJB;
import beans.AuditoriaEJB;
import beans.CargoEJB;
import beans.DepartamentoEJB;
import beans.GeneroEJB;
import beans.LoginEJB;
import beans.MunicipioEJB;
import beans.TipoUsuarioEJB;
import beans.UsuarioEJB;
import entidades.AreaEmpresa;
import entidades.Auditoria;
import entidades.Cargo;
import entidades.Departamento;
import entidades.Genero;
import entidades.Login;
import entidades.Municipio;
import entidades.TipoUsuario;
import entidades.Usuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

@ViewScoped
@Named("gestionRecursosHumanosController")
public class GestionRecursosHumanosController implements Serializable {

	@Inject
	private SessionController sesion;
	
	@EJB
	private UsuarioEJB usuarioEJB;
	
	@EJB
	private CargoEJB cargoEJB;
	
	@EJB
	private DepartamentoEJB departamentoEJB;
	
	@EJB
	private MunicipioEJB municipioEJB;
	
	@EJB
	private GeneroEJB generoEJB;
	
	@EJB
	private LoginEJB loginEJB;
	
	@EJB
	private TipoUsuarioEJB tipoUsuarioEJB;
	
	@EJB
	private AreaEmpresaEJB areaEmpresaEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@NotNull(message = "Debe ingresar la cedula")
	private String cedula;
	
	private String cedulab;
	
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;
	
	@NotNull(message = "Debe ingresar el apellido")
	private String apellido;
	
	private String salarioo;
	
	private Date fechaNacimiento;
	
	private Date fechaIngreso;
	
	private int generoSeleccionado;
	
	private List<Genero> listaGeneros;
	
	private int municipioSeleccionado;
	
	private List<Municipio> listaMunicipios;
	
	private int dptoSeleccionado;
	
	private List<Departamento> listaDptos;

	private int areaSeleccionada;
	
	private List<AreaEmpresa> listaAreas; 
	
	private List<Cargo> listaCargos;
	
	private int cargoSeleccionado;
	
	private List<Usuario> usuarios;
	
	@PostConstruct
	public void inicializar(){
		
		llenarCombos();
	}
	
	public void registrar() {

		try {
			Usuario u = new Usuario();
			
			u.setNombre(nombre);
			u.setApellido(apellido);
			u.setCedula(cedula);
			u.setfechaNacimiento(fechaNacimiento);
			
			Municipio mun = municipioEJB.buscar(sesion.getBd(), municipioSeleccionado);
			u.setMunicipio(mun);
			
			AreaEmpresa area = areaEmpresaEJB.buscar(areaSeleccionada, sesion.getBd());
			u.setAreaEmpresa(area);
			
			Cargo cargo = cargoEJB.buscar(cargoSeleccionado, sesion.getBd());
			u.setCargo(cargo);
			
			u.setSalario(Double.parseDouble(salarioo));
			
			u.setFechaIngreso(fechaIngreso);

			Genero gen = generoEJB.buscar(sesion.getBd(), generoSeleccionado);
			u.setGenero(gen);

			//Creando recursos humanos
			usuarioEJB.crear(u, sesion.getBd());
			
			//creando auditoria
			crearAuditoria("RecursosHumanos",u.getCedula(),"Crear", sesion.getBd());
			
			//limpiamos campos
			limpiar();
			llenarCombos();
			Messages.addFlashGlobalInfo("Registro exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}

	}
	
	public void buscar(){
		try{
			Usuario u = usuarioEJB.buscar(cedulab, sesion.getBd());
			cedula=u.getCedula();
			nombre=u.getNombre();
			apellido=u.getApellido();
			fechaNacimiento=u.getfechaNacimiento();
			areaSeleccionada=areaEmpresaEJB.buscar(u.getAreaEmpresa().getId(), sesion.getBd()).getId();
			cargoSeleccionado=cargoEJB.buscar(u.getCargo().getId(), sesion.getBd()).getId();
			dptoSeleccionado=departamentoEJB.buscar(sesion.getBd(),u.getMunicipio().getDepartamento().getId()).getId();
			municipioSeleccionado=municipioEJB.buscar(sesion.getBd(),u.getMunicipio().getId()).getId();
			generoSeleccionado=generoEJB.buscar(sesion.getBd(), u.getGenero().getId()).getId();
			salarioo=String.valueOf(u.getSalario());
			fechaIngreso=u.getFechaIngreso();
			
			//creando auditoria
			crearAuditoria("RecursosHumanos",u.getCedula(),"Buscar", sesion.getBd());
		} catch (Exception e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void editar(){
		try {
			Usuario u = usuarioEJB.buscar(cedula, sesion.getBd());
			
			u.setNombre(nombre);
			u.setApellido(apellido);
			u.setCedula(cedula);
			u.setfechaNacimiento(fechaNacimiento);
			
			Municipio mun = municipioEJB.buscar(sesion.getBd(), municipioSeleccionado);
			u.setMunicipio(mun);
			
			AreaEmpresa area = areaEmpresaEJB.buscar(areaSeleccionada, sesion.getBd());
			u.setAreaEmpresa(area);
			
			Cargo cargo = cargoEJB.buscar(cargoSeleccionado, sesion.getBd());
			u.setCargo(cargo);
			
			u.setSalario(Double.parseDouble(salarioo));
			
			u.setFechaIngreso(fechaIngreso);

			Genero gen = generoEJB.buscar(sesion.getBd(), generoSeleccionado);
			u.setGenero(gen);

			usuarioEJB.editar(u, sesion.getBd());
			
			//creando auditoria
			crearAuditoria("RecursosHumanos",u.getCedula(),"Editar", sesion.getBd());
			
			//limpiamos campos
			limpiar();
			llenarCombos();
			Messages.addFlashGlobalInfo("Edicion exitoso");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void borrar(Usuario usu){
		try {
			usuarioEJB.eliminar(usu, sesion.getBd());
			Messages.addFlashGlobalInfo("Borrado exitoso");
			//creando auditoria
			crearAuditoria("RecursosHumanos",usu.getCedula(),"Eliminar", sesion.getBd());
		} catch (Exception e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	
	public void llenarCombos(){
		try{
			listaDptos = departamentoEJB.listar(sesion.getBd());
			listaGeneros = generoEJB.listar(sesion.getBd());
			listaMunicipios = municipioEJB.listar(sesion.getBd(), listaDptos.get(0));
			listaAreas = areaEmpresaEJB.listarUsuarios(sesion.getBd());
			listaCargos = cargoEJB.listar(sesion.getBd());
			usuarios = usuarioEJB.listarRecursosHumanos(sesion.getBd());
			
			
		}catch(ExcepcionNegocio e){
			Messages.addFlashGlobalInfo(e.getMessage());
		}
	}
	
	public void limpiar(){
		cedula ="";
		nombre ="";
		apellido ="";
		fechaNacimiento =null;
		fechaIngreso =null;
		listaMunicipios = municipioEJB.listar(sesion.getBd(), listaDptos.get(0));
		salarioo=null;
	}
	
	public void crearAuditoria(String entidad,String objeto, String accion, int bd){
		String browserDetails = Faces.getRequestHeader("User-Agent");
		Auditoria auditoria = new Auditoria();
		auditoria.setEntidad(entidad);
		auditoria.setObjetoAuditado(objeto);
		auditoriaEJB.crear(auditoria, bd, accion, browserDetails);
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCedulab() {
		return cedulab;
	}

	public void setCedulab(String cedulab) {
		this.cedulab = cedulab;
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

	public String getSalarioo() {
		return salarioo;
	}

	public void setSalarioo(String salarioo) {
		this.salarioo = salarioo;
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

	public int getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(int areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	public List<AreaEmpresa> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List<AreaEmpresa> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public List<Cargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<Cargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public int getCargoSeleccionado() {
		return cargoSeleccionado;
	}

	public void setCargoSeleccionado(int cargoSeleccionado) {
		this.cargoSeleccionado = cargoSeleccionado;
	}
	
	
	
	
}
