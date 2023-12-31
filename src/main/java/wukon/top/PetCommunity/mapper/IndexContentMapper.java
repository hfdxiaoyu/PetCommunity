package wukon.top.PetCommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.po.IndexContent;

import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/28
 * @Description:wukon.top.PetCommunity.mapper
 * @version:1.0
 */
@Mapper
@Repository
public interface IndexContentMapper{

    /**
      *功能描述：分页查询首页信息
      */
    List<IndexContent> queryIndexContentListPaged(Integer pageNum,Integer pageSize,String theme,String pet);

    //计算所有条数
    int countAllDate();
    /**
      *功能描述：根据文章id查询文章信息
      *@param: contentId
      *@return: IndexContent
      */
    IndexContent queryOneIndexContent(Integer contentId);
}
