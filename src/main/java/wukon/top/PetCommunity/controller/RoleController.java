package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.Role;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.RoleService;
import wukon.top.PetCommunity.util.ResponseResult;

import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/17
 * @Description:wukon.top.PetCommunity.controller
 * @version:1.0
 * 角色控制
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;

    /**
      *功能描述：构造器注入
      */

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    /**
     *功能描述：获取所有用户信息
     */
    @GetMapping("getAllRole")
    public ResponseResult getAllRole(){

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), roleService.list());
    }

    /**
     *功能描述：分页查询
     */
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize)
                                   {
        IPage<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        //根据id倒序
        queryWrapper.orderByDesc("id");
        IPage<Role> rolePage = roleService.page(page, queryWrapper);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), rolePage);
    }

    /**
     *功能描述：新增权限信息
     *@return: Integer
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody Role role){
        boolean save = roleService.save(role);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"新增成功" ,save);
    }


    /**
     *功能描述：删除一个角色
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id){
        boolean b = roleService.removeById(id);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "删除成功",b);
    }

    /**
     *功能描述：批量删除
     */
    @PostMapping("/delete/batch/")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids){
        boolean b = roleService.removeByIds(ids);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "批量删除成功",b);
    }
}
