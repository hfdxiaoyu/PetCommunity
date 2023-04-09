package wukon.top.PetCommunity.domain.po;

import lombok.Data;

/**
 * @Auther:1306933
 * @Date:2023/4/9
 * @Description:wukon.top.PetCommunity.domain.po
 * @version:1.0
 * 登录后响应到前端的用户信息
 */
@Data
public class LoginUserPo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
      * token
      */
    private String token;

    /**
     * 头像url
     */
    private String avatar;
}
