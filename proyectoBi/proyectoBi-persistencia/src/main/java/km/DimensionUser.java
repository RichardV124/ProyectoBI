package km;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import entidades.Departamento;

@Entity
@Table(name="DIMENSION_USER")
@NamedQueries({ 
	@NamedQuery(name = DimensionUser.CONSULTA_LISTAR_DIMENSION_USER, query = "SELECT du FROM DimensionUser du") 
	})
public class DimensionUser implements Serializable{

	public static final String CONSULTA_LISTAR_DIMENSION_USER = "DimensionUser.ListarDimensionUser";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="REALNAME")
	private String realname;

	public DimensionUser() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
}
