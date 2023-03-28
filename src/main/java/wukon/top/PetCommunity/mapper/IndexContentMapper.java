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
    List<IndexContent> queryIndexContentListPaged(Integer pageNum,Integer pageSize);

    //计算所有条数
    int countAllDate();
}
