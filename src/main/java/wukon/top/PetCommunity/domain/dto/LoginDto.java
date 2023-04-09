package wukon.top.PetCommunity.domain.dto;

import lombok.Data;

/**
 * @Auther:1306933
 * @Date:2023/4/8
 * @Description:wukon.top.PetCommunity.domain.dto
 * @version:1.0
 * 接收前端传过来的参数
 */
@Data
public class LoginDto {
    private String userName; //用户名
    private String password; //密码
}
