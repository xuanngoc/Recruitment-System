package ezjob.model.dto;

public class CandidateDTO {
	
	private long candidateId;
	private long userId;
	private String fullname;
	private String path_file_cv;
	
	
	

	public long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(long id) {
		this.candidateId=id;
	}
	
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long id) {
		this.userId=id;
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
	



}
