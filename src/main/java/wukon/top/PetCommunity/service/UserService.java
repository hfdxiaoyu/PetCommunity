package wukon.top.PetCommunity.service;

import wukon.top.PetCommunity.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import wukon.top.PetCommunity.util.ResponseResult;

/**
* @author 13069
* @description 针对表【user】的数据库操作Service
* @createDate 2023-03-15 10:16:43
*/
public interface UserService extends IService<User> {

    /**
      *功能描述：登录的接口
      *@param: User
      *@return: ResponseResult
      */
    ResponseResult login(User user);

    /**
     *功能描述：登出的接口
     *@param: null
     *@return: ResponseResult
     */
    ResponseResult logout();
}
