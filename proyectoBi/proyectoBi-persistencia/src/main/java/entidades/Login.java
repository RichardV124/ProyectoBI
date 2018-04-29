package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="LOGIN")
public class Login implements Serializable{
	
	@Id
	@Column(name="USERNAME",length=40,nullable=false)
	private String username;
	
	@Column(name="PASSWORD",length=40,nullable=false)
	private String password;
	
	@Column(name="ACTIVO")
	private boolean activo;
	

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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
