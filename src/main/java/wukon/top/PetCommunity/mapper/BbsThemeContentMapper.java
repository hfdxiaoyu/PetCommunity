package wukon.top.PetCommunity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.BbsThemeContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13069
* @description 针对表【bbs_theme_content】的数据库操作Mapper
* @createDate 2023-03-15 10:16:42
* @Entity wukon.top.PetCommunity.domain.BbsThemeContent
*/
@Mapper
@Repository
public interface BbsThemeContentMapper extends BaseMapper<BbsThemeContent> {

}




