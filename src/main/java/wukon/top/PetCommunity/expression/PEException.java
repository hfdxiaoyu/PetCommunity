package wukon.top.PetCommunity.expression;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import wukon.top.PetCommunity.domain.LoginUser;

import java.util.List;

/**
 * @Auther:1306933
 * @Date:2023/3/18
 * @Description:wukon.top.PetCommunity.expression
 * @version:1.0
 */
@Component("ex")
public class PEException {

    public boolean hasAuthority(String authority){
        //获取当前用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //拿到权限信息
        List<String> permissions = loginUser.getPermissions();
        //判断用户权限集合中是否存在authority
        return permissions.contains(authority);
    }
}
