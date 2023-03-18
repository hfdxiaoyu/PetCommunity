package wukon.top.PetCommunity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13069
* @description 针对表【role_menu】的数据库操作Mapper
* @createDate 2023-03-15 10:16:43
* @Entity wukon.top.PetCommunity.domain.RoleMenu
*/
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}




