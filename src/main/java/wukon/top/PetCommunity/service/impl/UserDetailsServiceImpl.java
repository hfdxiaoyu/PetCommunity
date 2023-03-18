package wukon.top.PetCommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wukon.top.PetCommunity.domain.LoginUser;
import wukon.top.PetCommunity.domain.User;
import wukon.top.PetCommunity.mapper.MenuMapper;
import wukon.top.PetCommunity.mapper.UserMapper;

import java.util.List;
import java.util.Objects;

/**
 * @Auther:1306933
 * @Date:2023/3/18
 * @Description:wukon.top.PetCommunity.service.impl
 * @version:1.0
 * SpringSecurity会自动运行此类去获取用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //等值匹配
        queryWrapper.eq(User::getUserName,username);
        //查询用户信息
        User user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户就抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误");
        }


        //查询对应的权限信息
        List<String> list = menuMapper.selectPermsByUserId(user.getId());

        //存权限信息
//        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));

        //把数据封装成UserDetails返回
        return new LoginUser(user,list);
    }
}
