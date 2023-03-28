package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.BbsTheme;
import wukon.top.PetCommunity.domain.BbsTheme;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.BbsThemeService;
import wukon.top.PetCommunity.util.ResponseResult;

import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/17
 * @Description:wukon.top.PetCommunity.controller
 * @version:1.0
 * 主题
 */
@RestController
@RequestMapping("/bbsTheme")
public class BbsThemeController {

    @Autowired
    private BbsThemeService bbsThemeService;

    /**
     *功能描述：获取所有用户信息
     */
    @GetMapping("getAllBbsTheme")
    public ResponseResult getAllBbsTheme(){

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), bbsThemeService.list());
    }

    /**
     *功能描述：分页查询
     */
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize)
    {
        IPage<BbsTheme> page = new Page<>(pageNum,pageSize);
        QueryWrapper<BbsTheme> queryWrapper = new QueryWrapper<>();

        //根据id倒序
        queryWrapper.orderByDesc("id");
        IPage<BbsTheme> BbsThemeIPage = bbsThemeService.page(page, queryWrapper);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), BbsThemeIPage);
    }

    /**
     *功能描述：新增标签信息
     *@return: Integer
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody BbsTheme BbsTheme){
        boolean save = bbsThemeService.save(BbsTheme);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"新增成功" ,save);
    }


    /**
     *功能描述：删除一个角色
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id){
        boolean b = bbsThemeService.removeById(id);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "删除成功",b);
    }

    /**
     *功能描述：批量删除
     */
    @PostMapping("/delete/batch/")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids){
        boolean b = bbsThemeService.removeByIds(ids);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "批量删除成功",b);
    }

}
