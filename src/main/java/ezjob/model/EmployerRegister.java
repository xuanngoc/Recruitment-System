package ezjob.model;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class EmployerRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long employerRegisterId;
	
	@Column(name = "COMPANY_NAME", length = 120)
	private String companyName;
	
	@Column(name = "COMPANY_WEBSITE", length = 50)
	private String companyWebsite;
	
	@Column(name = "CITY", length = 50)
	private String city;
	
	@Column(name = "FULL_NAME", length = 50)
	private String fullname;
	
	@Column(name = "TITLE", length = 50)
	private String title;
	
	@Column(name = "EMAIL", length = 50)
	private String email;
	
	@Column(name = "PHONE", length = 15)
	private String phone;
	
	@Column(name = "PENDING")
	private boolean pending = true;

	@CreationTimestamp
	@Column(name = "SENT_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentAt;
	
	public EmployerRegister() {	}
	
	public EmployerRegister(long employerRegisterId, String companyName, String companyWebsite, String city,
			String fullname, String title, String email, String phone) {
		this.employerRegisterId = employerRegisterId;
		this.companyName = companyName;
		this.companyWebsite = companyWebsite;
		this.city = city;
		this.fullname = fullname;
		this.title = title;
		this.email = email;
		this.phone = phone;
	}

	public long getEmployerRegisterId() {
		return employerRegisterId;
	}

	public void setEmployerRegisterId(long employerRegisterId) {
		this.employerRegisterId = employerRegisterId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}
	
	public String getDateSentFormat() {
		return new SimpleDateFormat("dd-MM-YYYY HH:mm").format(sentAt);
	}
}
