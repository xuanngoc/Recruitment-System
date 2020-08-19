package ezjob.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Employer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long employerId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Column(name = "COMPANY_NAME", length = 100)
	private String companyName;
	
	@Column(name = "COUNTRY", length = 30)
	private String country;
	
	@Column(name = "CITY", length = 60)
	private String city;
	
	@Column(name = "ADDRESS", length = 300)
	private String address;
	
	@Column(name = "TYPE", length = 50)
	private String type;
	
	@Column(name = "SIZE")
	private int size;
	
	@Column(name = "DAY_STARTING_OF_WEEK", length = 15)
	private String dayStartingOfWeek;
	
	@Column(name = "DAY_ENDING_OF_WEEK", length = 15)
	private String dayEndingOfWeek;
	
	@Column(name = "DESCRIPTION", length = 2000)
	private String description;
	
	@Column(name = "PATH_FILE_IMAGE", length = 350)
	private String pathFileImage = "Assets\\image\\default.png";

	@OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
	private Collection<Job> jobs;
	
	public Employer() { }

	
	public long getEmployerId() {
		return employerId;
	}

	public void setEmployerId(long employerId) {
		this.employerId = employerId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getDayStartingOfWeek() {
		return dayStartingOfWeek;
	}

	public void setDayStartingOfWeek(String dayStartingOfWeek) {
		this.dayStartingOfWeek = dayStartingOfWeek;
	}

	public String getDayEndingOfWeek() {
		return dayEndingOfWeek;
	}

	public void setDayEndingOfWeek(String dayEndingOfWeek) {
		this.dayEndingOfWeek = dayEndingOfWeek;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPathFileImage() {
		return pathFileImage;
	}

	public void setPathFileImage(String pathFileImage) {
		this.pathFileImage = pathFileImage;
	}
	
}
