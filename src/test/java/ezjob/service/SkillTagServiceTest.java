package ezjob.service;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ezjob.model.SkillTag;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillTagServiceTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private SkillTagService skillTagService;
	
	
	@Test
	public void testGetSkillTagById() {
		SkillTag skill = skillTagService.getSkillTagById(55);
		long skillId = skill.getSkillTagId();
		String name = skill.getSkillTagName();
		assertEquals(55, skillId);
		assertEquals("Deep Learning", name);
	}
	
	@Test
	public void testSaveSkillTag() {
		SkillTag skillExpect = new SkillTag(0L, "Deep Learning"); 
		SkillTag skillActual = skillTagService.saveOrUpdate(skillExpect);
		assertEquals(skillExpect.getSkillTagName(), skillActual.getSkillTagName());
	}

	@org.junit.Test(expected = NoSuchElementException.class)
	public void testDeleteSkillTag() {
		skillTagService.delete(58);
		skillTagService.getSkillTagById(58);
	}
}
