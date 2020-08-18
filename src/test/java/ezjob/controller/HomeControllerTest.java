package ezjob.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeControllerTest {
	
	@MockBean
	private HomeController homeController;
	
	@Test
	public void contextLoad() {
		assertNotNull(homeController);
	}

}
