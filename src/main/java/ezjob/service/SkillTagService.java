package ezjob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezjob.model.SkillTag;
import ezjob.repository.SkillTagRepository;

@Service
public class SkillTagService {
	
	@Autowired
	private SkillTagRepository skillTagRepository;
	
	public List<SkillTag> getListSkillTags() {
		return skillTagRepository.findAll();
	}
	
	public SkillTag getSkillTagById(long id) {
		return skillTagRepository.findById(id).get();	
	}
	
	public void saveOrUpdate(SkillTag skillTag) {
		skillTagRepository.save(skillTag);
	}
	
	public void delete(long id) {
		skillTagRepository.deleteById(id);
	}
}
