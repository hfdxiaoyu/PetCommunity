package wukon.top.PetCommunity.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther:1306933
 * @Date:2023/3/18
 * @Description:wukon.top.PetCommunity.domain
 * @version:1.0
 * SpringSecurity登录需要的实体类
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;
    private List<String> permissions;//权限信息

    public LoginUser(User user,List<String> permissions){
        this.user = user;
        this.permissions = permissions;
    }

    //定义成成员变量，这样就不用每次都生成一次了
    //SimpleGrantedAuthority这个成员变量反序列化存入redis中时可能会报错，所以设置进行忽略
    @JSONField(serialize = false) //设置了这个属性之后就不会被序列化
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null)
            return authorities;
        //permissions中String类型的权限封装成SimpleGrantedAuthority实现类
        //Steam流的形式
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new).
                collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == 0;
    }
}
