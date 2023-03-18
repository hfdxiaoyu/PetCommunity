package wukon.top.PetCommunity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import wukon.top.PetCommunity.domain.User;
import wukon.top.PetCommunity.service.UserService;
import wukon.top.PetCommunity.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 13069
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-03-15 10:16:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




