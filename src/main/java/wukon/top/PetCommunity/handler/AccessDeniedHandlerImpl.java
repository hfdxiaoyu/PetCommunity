package wukon.top.PetCommunity.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import wukon.top.PetCommunity.util.ResponseResult;
import wukon.top.PetCommunity.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther:1306933
 * @Date:2022/8/8
 * @version:1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //设置认证失败的响应码 HttpStatus.UNAUTHORIZED Spring内置的枚举类型
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(),"我的权限不足");
        //序列化
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,json);
    }
}
