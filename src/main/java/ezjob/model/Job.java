package ezjob.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long jobId;
	
	@Column(name = "TITLE", length = 100)
	private String title;
	
	@Column(name = "SALARY")
	private int salary;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "POSTED_TIME")
	private Date postedTime;
	
	@Column(name = "CLOSED")
	private boolean closed = false;
	
	@Column(name = "DESCRIPTION", length = 1000)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id", nullable =  false)
	private Employer employer;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "jobs_skill_tags",
			 joinColumns = @JoinColumn(name = "job_id"),
			 inverseJoinColumns = @JoinColumn(name = "skill_tag_id"))
	private Collection<SkillTag> skillTags;

	
	public Job() {}

	
	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Date getPostedTime() {
		return postedTime;
	}

	public void setPostedTime(Date postedTime) {
		this.postedTime = postedTime;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public Collection<SkillTag> getSkillTags() {
		return skillTags;
	}

	public void setSkillTags(Collection<SkillTag> skillTags) {
		this.skillTags = skillTags;
	}
	
}
