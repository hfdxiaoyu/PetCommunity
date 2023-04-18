package wukon.top.PetCommunity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wukon.top.PetCommunity.domain.User;
import wukon.top.PetCommunity.domain.po.IndexContent;
import wukon.top.PetCommunity.mapper.IndexContentMapper;
import wukon.top.PetCommunity.service.UserService;

import java.util.List;

@SpringBootTest
class PetCommunityApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private IndexContentMapper indexContentMapper;


	@Test
	void userTest() {
		List<User> list = userService.list();
		list.forEach(System.out::println);
	}

	/**
	  *功能描述： 测试分页
	  */
	@Test
	void testContentIndex(){
		List<IndexContent> indexContents = indexContentMapper.queryIndexContentListPaged(2, 5,"银渐层","宠物相亲");
		indexContents.forEach(System.out::println);
//		Page<IndexContent> indexContents = PageHelper.startPage(1, 5);


	}

	@Test
	void testContentIndexService(){
		System.out.println(indexContentMapper.countAllDate());
	}
}
