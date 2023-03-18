package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.BbsContent;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.BbsContentService;
import wukon.top.PetCommunity.util.ResponseResult;

import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/17
 * @Description:wukon.top.PetCommunity.controller
 * @version:1.0
 * 文章内容管理
 */
@RestController
@RequestMapping("/bbsContent")
public class BbsContentController {
    
    @Autowired
    private BbsContentService bbsContentService;

    /**
     *功能描述：获取所有用户信息
     */
    @GetMapping("getAllBbsContent")
    public ResponseResult getAllBbsContent(){

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), bbsContentService.list());
    }

    /**
     *功能描述：分页查询
     */
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize)
    {
        IPage<BbsContent> page = new Page<>(pageNum,pageSize);
        QueryWrapper<BbsContent> queryWrapper = new QueryWrapper<>();

        //根据id倒序
        queryWrapper.orderByDesc("id");
        IPage<BbsContent> BbsContentIPage = bbsContentService.page(page, queryWrapper);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), BbsContentIPage);
    }

    /**
     *功能描述：新增权限信息
     *@return: Integer
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody BbsContent BbsContent){
        boolean save = bbsContentService.save(BbsContent);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"新增成功" ,save);
    }


    /**
     *功能描述：删除一个角色
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id){
        boolean b = bbsContentService.removeById(id);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "删除成功",b);
    }

    /**
     *功能描述：批量删除
     */
    @PostMapping("/delete/batch/")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids){
        boolean b = bbsContentService.removeByIds(ids);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "批量删除成功",b);
    }
    
}
