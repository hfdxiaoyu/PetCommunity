package wukon.top.PetCommunity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wukon.top.PetCommunity.domain.User;
import wukon.top.PetCommunity.service.UserService;

import java.util.List;

@SpringBootTest
class PetCommunityApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void userTest() {
		List<User> list = userService.list();
		list.forEach(System.out::println);
	}

}
