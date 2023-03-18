package wukon.top.PetCommunity.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import wukon.top.PetCommunity.domain.LoginUser;
import wukon.top.PetCommunity.util.JwtUtil;
import wukon.top.PetCommunity.util.RedisCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther:1306933
 * @Date:2023/3/18
 * @Description:wukon.top.PetCommunity.filter
 * @version:1.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //给它配置到springsecurity执行的过滤器之中去
        //获取token
        //从请求头中去获取
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            //放行 后面其它的过滤器会对它进行判断
            filterChain.doFilter(request,response);
            return;
        }
        //显示token
        String userid;
        try {
            //对token进行解析
            Claims claims = JwtUtil.parseJWT(token);
            //拿到userid
            userid = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:"+userid;
        //从redis查询
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        //判断有没有
        if (Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder,后面的认证会从里面取信息进行判断
        //TODO 获取权限信息封装到setAuthentication()中

        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        //存入SecurityContextHolder对象
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request,response);
    }
}
