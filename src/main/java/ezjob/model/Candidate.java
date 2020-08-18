package ezjob.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity(name = "CANDIDATE")

public class Candidate  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CANDIDATE_ID")
	private long candidateId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;

	
	@Column(name = "FULL_NAME", length = 50)
	private String fullname;
	
	@Column(name = "PATH_FILE_CV", length = 500)
	private String path_file_cv;
	
	
	  
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getPath_file_cv() {
		return path_file_cv;
	}


	public void setPath_file_cv(String path_file_cv) {
		this.path_file_cv = path_file_cv;
	}


	public Candidate() {
		
	}
	
	
	public long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(long id) {
		this.candidateId=id;
	}
	
	

	
	
	public String getFullName() {
		return fullname;
	}
	
	public void setFullName(String name ) {
		this.fullname=name;
	} 
	
	public String getPathFile() {
		return path_file_cv;
	}
	
	public void setPathFile(String path) {
		this.path_file_cv=path;
	}

   
	public Candidate get() {
		// TODO Auto-generated method stub
		return null;
	}


	
	 

}
