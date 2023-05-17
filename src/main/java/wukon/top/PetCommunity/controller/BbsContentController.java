package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BbsContentController {
    
    //使用lombook简化构造器注入
    private final BbsContentService bbsContentService;

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
//        QueryWrapper<BbsContent> queryWrapper = new QueryWrapper<>();

        //根据id倒序
//        queryWrapper.orderByDesc("id");
        IPage<BbsContent> BbsContentIPage = bbsContentService.page(page);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), BbsContentIPage);
    }

    /**
      *功能描述：首页的接口
      *@param: pageNum pageSize
     * @param theme 主题名字
     * @param pet 动物名字
      *@return: ResponseResult
      */
    @GetMapping("/indexPage")
    public ResponseResult indexPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String theme,
                                    @RequestParam(defaultValue = "") String pet){

        return bbsContentService.queryIndexContentListPaged(pageNum,pageSize,theme,pet);
    }

    @GetMapping("/getOneContent/{contentid}")
    public ResponseResult getOneContent(@PathVariable Integer contentid){
        return bbsContentService.queryOneIndexContent(contentid);
    }

    /**
     *功能描述：新增帖子
     *@return: Integer
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody BbsContent BbsContent){
        boolean save = bbsContentService.save(BbsContent);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"新增成功" ,save);
    }


    /**
     *功能描述：删除一个帖子
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

    /**
      *功能描述：收藏帖子
     * TODO:待实现
      */
    public ResponseResult  collectContent(@RequestBody BbsContent bbsContent){
        boolean b = bbsContentService.save(bbsContent);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "收藏帖子成功",b);
    }

    /**
     *功能描述：更新帖子信息
     *@param:
     *@return:
     *@auther:
     *@date:
     */
    @PostMapping("/update")
    public ResponseResult editOneUser(@RequestBody BbsContent bbsContent){
        UpdateWrapper<BbsContent> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setEntity(bbsContent);
        boolean update = bbsContentService.update(updateWrapper);
        if (!update)
            return new ResponseResult(StatusCodeEnum.ERROR.getCode(),"更新失败" ,update);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"更新成功" ,update);
    }
    
}
