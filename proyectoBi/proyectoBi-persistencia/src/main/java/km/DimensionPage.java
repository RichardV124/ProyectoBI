package km;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIMENSION_PAGE")
@NamedQueries({ 
	@NamedQuery(name = DimensionPage.CONSULTA_LISTAR_DIMENSION_PAGE, query = "SELECT dp FROM DimensionPage dp") 
	})
public class DimensionPage {

	public static final String CONSULTA_LISTAR_DIMENSION_PAGE = "DimensionPage.ListarDimensionPage";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="TEXT", nullable=true, length=10000)
	private String text;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="DATE",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date date;

	public DimensionPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
