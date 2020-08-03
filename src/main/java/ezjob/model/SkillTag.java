package ezjob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SkillTag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SKILL_TAG_ID")
	private Long skillTagId;
	
	@Column(name = "SKILL_TAG_NAME", length = 40)
	private String skillTagName;

	public SkillTag() {	}

	public Long getSkillTagId() {
		return skillTagId;
	}

	public void setSkillTagId(Long skillTagId) {
		this.skillTagId = skillTagId;
	}

	public String getSkillTagName() {
		return skillTagName;
	}

	public void setSkillTagName(String skillTagName) {
		this.skillTagName = skillTagName;
	}
	
}
