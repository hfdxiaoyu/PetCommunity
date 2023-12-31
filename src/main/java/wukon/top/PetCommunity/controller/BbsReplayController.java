package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.BbsReplay;
import wukon.top.PetCommunity.domain.dto.AddReplay;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.BbsReplayService;
import wukon.top.PetCommunity.util.BeanCopyUtils;
import wukon.top.PetCommunity.util.JwtUtil;
import wukon.top.PetCommunity.util.ResponseResult;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/17
 * @Description:wukon.top.PetCommunity.controller
 * @version:1.0
 * 评论控制层
 */
@RestController
@RequestMapping("/bbsReplay")
public class BbsReplayController {
    
    @Autowired
    private BbsReplayService bbsReplayService;

    /**
     *功能描述：获取所有用户信息
     */
    @GetMapping("getAllBbsReplay")
    public ResponseResult getAllBbsReplay(){

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), bbsReplayService.list());
    }

    /**
     *功能描述：分页查询
     */
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String articleId)
    {
        IPage<BbsReplay> page = new Page<>(pageNum,pageSize);
        QueryWrapper<BbsReplay> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("article_id",articleId);
        //根据id倒序
//        queryWrapper.orderByDesc("id");
        IPage<BbsReplay> BbsReplayIPage = bbsReplayService.page(page, queryWrapper);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), BbsReplayIPage);
    }

    /**
      *功能描述：根据文章id查询评论信息
      *@param articleId 文章id
     * @param pageNum  页数
     * @param pageSize  每一页大小
      *@return: ResponseResult
      */
    @GetMapping("/replayList")
    public ResponseResult replayList(@RequestParam Long articleId,
                                     @RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize){
        return bbsReplayService.commentList(articleId,pageNum,pageSize);
    }

    /**
      *功能描述：新增评论
      *@param: replay
      *@return: ResponseResult
      */
    @PostMapping("/addreplay")
    public ResponseResult addreplay(@RequestBody AddReplay replay, HttpServletRequest request) throws Exception {
        BbsReplay bbsReplay = BeanCopyUtils.copyBean(replay,BbsReplay.class);
        String token = request.getHeader("token");//拿到token
        //对token进行解析
        Claims claims = JwtUtil.parseJWT(token);
        //拿到userid
        String userid = claims.getSubject();
        long l = Long.parseLong(userid);
        bbsReplay.setCreateBy(l);
        bbsReplay.setUpdateBy(l);

        return bbsReplayService.addComment(bbsReplay);
    }

    /**
     *功能描述：新增权限信息
     *@return: Integer
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody BbsReplay BbsReplay){
        boolean save = bbsReplayService.save(BbsReplay);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"新增成功" ,save);
    }


    /**
     *功能描述：删除一个角色
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id){
        boolean b = bbsReplayService.removeById(id);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "删除成功",b);
    }

    /**
     *功能描述：批量删除
     */
    @PostMapping("/delete/batch/")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids){
        boolean b = bbsReplayService.removeByIds(ids);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "批量删除成功",b);
    }


}
