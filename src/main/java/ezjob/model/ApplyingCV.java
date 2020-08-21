package ezjob.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "APPLYING_CV")
public class ApplyingCV {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CV_ID")
	private long cvId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID",nullable =  false)
    private Job job;
	
	private String  path_file_cv;
	private Date datetime;
	
	public long getCvId() {
		return cvId;
	}
	public void setCvId(long cvId) {
		this.cvId = cvId;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getPath_file_cv() {
		return path_file_cv;
	}
	public void setPath_file_cv(String path_file_cv) {
		this.path_file_cv = path_file_cv;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	
}
