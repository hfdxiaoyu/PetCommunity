package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.BbsReplay;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.BbsReplayService;
import wukon.top.PetCommunity.util.ResponseResult;

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
