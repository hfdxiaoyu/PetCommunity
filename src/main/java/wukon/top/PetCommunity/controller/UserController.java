package wukon.top.PetCommunity.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wukon.top.PetCommunity.domain.User;
import wukon.top.PetCommunity.domain.UserRole;
import wukon.top.PetCommunity.domain.dto.LoginDto;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.service.RoleMenuService;
import wukon.top.PetCommunity.service.UserRoleService;
import wukon.top.PetCommunity.service.UserService;
import wukon.top.PetCommunity.util.ResponseResult;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    //登录的接口
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto){
        User user = new User();
        //对象拷贝
        BeanUtils.copyProperties(loginDto,user);
        //登录
        return userService.login(user);
    }

    //退出登录的接口
    //设置权限
//    @PreAuthorize("@ex.hasAnyAuthority('system:admin:list','system:user:list')")
    @RequestMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }

    /**
      *功能描述：注册的接口
      */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        //对密码进行加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存储用户信息
        boolean save = userService.save(user);
        if (save) {
            //注册成功之后为用户赋予权限信息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_name",user.getUserName());
            //根据用户名查询用户信息
            User oneUser = userService.getOne(queryWrapper);
            UserRole userRole = new UserRole(oneUser.getId(),2L);
            boolean save1 = userRoleService.save(userRole);
            if (save1)
                log.info("用户{}权限添加成功",oneUser);
            else
                log.error("用户{}权限添加失败",oneUser);
            return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "注册成功");
        }else {
            log.info("{}注册失败",user);
            return new ResponseResult(StatusCodeEnum.ERROR.getCode(), "注册失败");
        }
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
      *功能描述：更新用户信息
      *@param:
      *@return:
      *@auther:
      *@date:
      */
    @PostMapping("/update")
    public ResponseResult editOneUser(@RequestBody User user){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setEntity(user);
        boolean update = userService.update(updateWrapper);
        if (!update)
            return new ResponseResult(StatusCodeEnum.ERROR.getCode(),"更新失败" ,update);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"更新成功" ,update);
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
