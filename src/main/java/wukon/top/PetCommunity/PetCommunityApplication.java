package wukon.top.PetCommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = "wukon.top.PetCommunity.mapper")
@SpringBootApplication
public class PetCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetCommunityApplication.class, args);
	}

}
