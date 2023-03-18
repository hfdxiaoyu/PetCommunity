package wukon.top.PetCommunity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.ChattingRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13069
* @description 针对表【chatting_records】的数据库操作Mapper
* @createDate 2023-03-15 10:16:42
* @Entity wukon.top.PetCommunity.domain.ChattingRecords
*/
@Mapper
@Repository
public interface ChattingRecordsMapper extends BaseMapper<ChattingRecords> {

}




