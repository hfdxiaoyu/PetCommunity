package wukon.top.PetCommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.omg.CORBA.SystemException;
import wukon.top.PetCommunity.domain.BbsReplay;
import wukon.top.PetCommunity.domain.po.CommentPo;
import wukon.top.PetCommunity.domain.po.PagePo;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.BbsReplayService;
import wukon.top.PetCommunity.mapper.BbsReplayMapper;
import org.springframework.stereotype.Service;
import wukon.top.PetCommunity.service.UserService;
import wukon.top.PetCommunity.util.BeanCopyUtils;
import wukon.top.PetCommunity.util.ResponseResult;

import java.util.List;

/**
* @author 13069
* @description 针对表【bbs_replay】的数据库操作Service实现
* @createDate 2023-03-15 10:16:42
*/
@Service
@RequiredArgsConstructor //使用自动生成构造器注入
public class BbsReplayServiceImpl extends ServiceImpl<BbsReplayMapper, BbsReplay>
    implements BbsReplayService{

    private final UserService userService;

    @Override
    public ResponseResult commentList(Long articleIdm, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<BbsReplay> wrapper = new LambdaQueryWrapper<>();
        //根评论
        wrapper.eq(BbsReplay::getRootId,-1);
        //分页查询
        Page page = new Page(pageNum,pageSize);
        //分页
        page(page,wrapper);

        List<CommentPo> commentPoList = tocomments(page.getRecords());
        for (CommentPo commentPo : commentPoList) {
            List<CommentPo> children = getChildren(commentPo.getId());
            commentPo.setChildren(children);
        }

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), new PagePo(commentPoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(BbsReplay bbsReplay) throws Exception {
        if (!Strings.hasText(bbsReplay.getContent()))
            throw new Exception("评论内容为空！！！");
        boolean save = save(bbsReplay);
        if (save)
            return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "成功");
        else
            return new ResponseResult(StatusCodeEnum.ERROR.getCode(), "失败");
    }

    /**
     * 根据根评论查询所对应的子评论
     * @param id
     * @return
     */
    private List<CommentPo> getChildren(Long id) {
        LambdaQueryWrapper<BbsReplay> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BbsReplay::getRootId,id);
        wrapper.orderByAsc(BbsReplay::getCreateTime);
        List<BbsReplay> commentList = list(wrapper);

        List<CommentPo> tocomments = tocomments(commentList);
        return tocomments;
    }

    private List<CommentPo> tocomments(List<BbsReplay> list) {
        List<CommentPo> commentPos = BeanCopyUtils.copyBeanList(list, CommentPo.class);
        for (CommentPo commentPo : commentPos) {
            String nickName = userService.getById(commentPo.getCreateBy()).getNickName();
            commentPo.setUsername(nickName);

            if(commentPo.getToCommentId()!=-1){
                String toCommentName = userService.getById(commentPo.getToCommentUserId()).getNickName();
                commentPo.setToCommentUserName(toCommentName);
            }

        }

        return commentPos;
    }
}




