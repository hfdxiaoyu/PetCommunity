package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.ChattingRecords;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.ChattingRecordsService;
import wukon.top.PetCommunity.util.ResponseResult;

import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/17
 * @Description:wukon.top.PetCommunity.controller
 * @version:1.0
 */
@RestController
@RequestMapping("/chattingRecords")
public class ChattingRecordsController {
    
    @Autowired
    private ChattingRecordsService chattingRecordsService;

    /**
     *功能描述：获取所有用户信息
     */
    @GetMapping("getAllChattingRecords")
    public ResponseResult getAllChattingRecords(){

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), chattingRecordsService.list());
    }

    /**
     *功能描述：分页查询
     */
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize)
    {
        IPage<ChattingRecords> page = new Page<>(pageNum,pageSize);
        QueryWrapper<ChattingRecords> queryWrapper = new QueryWrapper<>();

        //根据id倒序
        queryWrapper.orderByDesc("id");
        IPage<ChattingRecords> ChattingRecordsIPage = chattingRecordsService.page(page, queryWrapper);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), ChattingRecordsIPage);
    }

    /**
     *功能描述：新增权限信息
     *@return: Integer
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody ChattingRecords ChattingRecords){
        boolean save = chattingRecordsService.save(ChattingRecords);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"新增成功" ,save);
    }


    /**
     *功能描述：删除一个角色
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id){
        boolean b = chattingRecordsService.removeById(id);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "删除成功",b);
    }

    /**
     *功能描述：批量删除
     */
    @PostMapping("/delete/batch/")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids){
        boolean b = chattingRecordsService.removeByIds(ids);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "批量删除成功",b);
    }
}
