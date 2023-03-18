package wukon.top.PetCommunity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.BbsTheme;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13069
* @description 针对表【bbs_theme】的数据库操作Mapper
* @createDate 2023-03-15 10:16:42
* @Entity wukon.top.PetCommunity.domain.BbsTheme
*/
@Mapper
@Repository
public interface BbsThemeMapper extends BaseMapper<BbsTheme> {

}




