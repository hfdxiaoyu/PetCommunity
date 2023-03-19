package wukon.top.PetCommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.User;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.UserService;
import wukon.top.PetCommunity.util.ResponseResult;

import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/16
 * @Description:wukon.top.PetCommunity.controller
 * @version:1.0
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //登录的接口
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        //登录
        return userService.login(user);
    }

    //退出登录的接口
    @RequestMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }

    /**
      *功能描述：注册的接口
      */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        boolean save = userService.save(user);
        if (save)
          return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "注册成功");
        else
            return new ResponseResult(StatusCodeEnum.ERROR.getCode(), "注册失败");
    }

    /**
      *功能描述：获取所有用户信息
      */
    @GetMapping("getAllUser")
    public ResponseResult getAllUser(){

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), userService.list());
    }

    /**
      *功能描述：分页查询
      */
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String email,
                                   @RequestParam(defaultValue = "") String address){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //加入模糊查询
        if (!"".equals(username))
            queryWrapper.like("username",username);
        //框架会自动加and
        if (!"".equals(email))
            queryWrapper.like("email",email);
        if (!"".equals(address))
            queryWrapper.like("address",address);
        //根据id倒序
        queryWrapper.orderByDesc("id");
        IPage<User> userPage = userService.page(page, queryWrapper);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), userPage);
    }

    /**
     *功能描述：新增或者更新用户信息
     *@return: Integer
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody User user){
        boolean save = userService.save(user);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"新增成功" ,save);
    }

    /**
      *功能描述：根据用户名查询一个用户的方法
      */
    @GetMapping("/username/{username}")
    public ResponseResult findOne(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),StatusCodeEnum.SUCCESS.getMsg() ,userService.getOne(queryWrapper));
    }

    /**
      *功能描述：删除一个用户
      */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id){
        boolean b = userService.removeById(id);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "删除成功",b);
    }

    /**
     *功能描述：批量删除
     */
    @PostMapping("/delete/batch/")
    public ResponseResult deleteBatch(@RequestBody List<Integer> ids){
        boolean b = userService.removeByIds(ids);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "批量删除成功",b);
    }

}
