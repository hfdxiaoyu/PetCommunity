package wukon.top.PetCommunity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13069
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-03-15 10:16:43
* @Entity wukon.top.PetCommunity.domain.User
*/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}




