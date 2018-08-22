package km;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="HECHO_RECENT_CHANGE")
public class HechoRecentChange implements Serializable{	

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name="USER_ID")
	@ManyToOne(cascade = CascadeType.ALL)
	private DimensionUser user;
	
	@JoinColumn(name="PAGE_ID")
	@ManyToOne(cascade = CascadeType.ALL)
	private DimensionPage page;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="OLD_LENGTH")
	private int oldLengTH;
	
	@Column(name="NEW_LENGTH")
	private int newLength;
	
	@Column(name="DATE",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date date;

	public HechoRecentChange() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DimensionUser getUser() {
		return user;
	}

	public void setUser(DimensionUser user) {
		this.user = user;
	}

	public DimensionPage getPage() {
		return page;
	}

	public void setPage(DimensionPage page) {
		this.page = page;
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

	public int getOldLengTH() {
		return oldLengTH;
	}

	public void setOldLengTH(int oldLengTH) {
		this.oldLengTH = oldLengTH;
	}

	public int getNewLength() {
		return newLength;
	}

	public void setNewLength(int newLength) {
		this.newLength = newLength;
	}
	
	
}
