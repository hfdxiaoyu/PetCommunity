package wukon.top.PetCommunity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wukon.top.PetCommunity.filter.JwtAuthenticationTokenFilter;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启注解权限认证相关配置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //创建一个BCryptPasswordEncoder注入容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不建议通过父类去修改配置文件
        http
                //关闭csrf
                .csrf().disable()
                //这个对象里面有serrson的一些配置
                //sessionCreationPolicy它接收的参数是一个枚举类型
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //还想配置其它东西，就使用and(),它返回一个HttpSecurity对象，就可以继续对它进行配置了
                .and()
                //关于请求认证的配置
                .authorizeRequests()
                // 对于登录接口 anonymous()方法允许匿名访问（登录状态是不能访问这个接口的）
                //这个方法对某一种请求方式进行指定的配置
                // permitAll()无论是否登录都可以去访问
//                .antMatchers("/hello").permitAll()
                .antMatchers("/user/login").anonymous()
                //这个接口需要哪些权限可以访问
                .antMatchers("/user/*").hasAuthority("system:dept:list")
                // 除上面外的所有请求全部需要鉴权认证
                //.authenticated()任意用户认证后都可以访问
                .anyRequest().authenticated();
        //添加过滤器
        //第一个参数是要添加的过滤器，第二个参数是你的过滤器要添加在在哪个过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常过滤器
        http.exceptionHandling()
                //配置认证失败的处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                //配置授权失败的处理器
                .accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}