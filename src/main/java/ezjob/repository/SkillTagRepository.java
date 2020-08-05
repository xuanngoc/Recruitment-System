package ezjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ezjob.model.SkillTag;

@Repository
public interface SkillTagRepository extends JpaRepository<SkillTag, Long>{

}
