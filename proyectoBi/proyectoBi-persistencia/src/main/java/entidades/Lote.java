package entidades;

import java.io.Serializable;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name="LOTE")
@NamedQueries({
	@NamedQuery(name=Lote.LISTA_LOTES, query="SELECT l FROM Lote l")
})
public class Lote implements Serializable{
	
	/**
	 * Lista de lotes registrados en la BD
	 */
	public static final String LISTA_LOTES = "lista.lotes";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOTE_SEQ")
    @SequenceGenerator(sequenceName = "lote_seq", allocationSize = 1, name = "LOTE_SEQ")
	private int id;
	
	@Column(name="DESCRIPCION")
	private String descripcion;

	public Lote(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public Lote() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
