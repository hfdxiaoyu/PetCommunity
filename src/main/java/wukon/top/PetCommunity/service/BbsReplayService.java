package wukon.top.PetCommunity.service;

import wukon.top.PetCommunity.domain.BbsReplay;
import com.baomidou.mybatisplus.extension.service.IService;
import wukon.top.PetCommunity.util.ResponseResult;

/**
* @author 13069
* @description 针对表【bbs_replay】的数据库操作Service
* @createDate 2023-03-15 10:16:42
*/
public interface BbsReplayService extends IService<BbsReplay> {

    ResponseResult commentList(Long articleIdm, Integer pageNum, Integer pageSize);

    ResponseResult addComment(BbsReplay bbsReplay);
}
