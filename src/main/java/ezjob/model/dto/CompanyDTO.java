package ezjob.model.dto;

public class CompanyDTO {
	
	private long employerId;
	private String companyName;
	private int totalJob;
	
	public CompanyDTO() {
		super();
	}

	public CompanyDTO(long employerId, String companyName, int totalJob) {
		super();
		this.employerId = employerId;
		this.companyName = companyName;
		this.totalJob = totalJob;
	}

	public long getEmployerId() {
		return employerId;
	}

	public void setEmployerId(long employerId) {
		this.employerId = employerId;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public int getTotalJob() {
		return totalJob;
	}
	
	public void setTotalJob(int totalJob) {
		this.totalJob = totalJob;
	}
	
}
