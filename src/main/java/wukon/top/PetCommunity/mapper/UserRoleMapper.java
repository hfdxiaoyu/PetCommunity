package wukon.top.PetCommunity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13069
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2023-03-15 10:16:43
* @Entity wukon.top.PetCommunity.domain.UserRole
*/
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




