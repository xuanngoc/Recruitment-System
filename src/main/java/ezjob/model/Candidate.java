package ezjob.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "CANDIDATE")
public class Candidate  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private long userId;
	
	@Column(name = "PATH_FILE_CV", length = 500)
	private String path_file_cv;
	
	
	  @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	    private User user;
	
	public Candidate() {
		
	}
	
	public long getCandidateID() {
		return userId;
	}
	 

}
