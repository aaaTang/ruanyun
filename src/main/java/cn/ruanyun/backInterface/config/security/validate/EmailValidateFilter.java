package cn.ruanyun.backInterface.config.security.validate;


import cn.hutool.core.util.StrUtil;
import cn.ruanyun.backInterface.common.constant.CommonConstant;
import cn.ruanyun.backInterface.common.utils.ResponseUtil;
import cn.ruanyun.backInterface.config.properties.CaptchaProperties;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图形验证码过滤器
 * @author fei
 */
@Slf4j
@Configuration
public class EmailValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 判断URL是否需要验证
        Boolean flag = false;
        String requestUrl = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        for(String url : captchaProperties.getEmail()){
            if(pathMatcher.match(url, requestUrl)){
                flag = true;
                break;
            }
        }
        if(flag){
            String email = request.getParameter("email");
            String code = request.getParameter("code");
            if(StrUtil.isBlank(email)||StrUtil.isBlank(code)){
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"请传入邮件验证码所需参数email或code"));
                return;
            }
            String v = redisTemplate.opsForValue().get(CommonConstant.PRE_EMAIL + email);
            if(StrUtil.isBlank(v)){
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"验证码已过期，请重新获取"));
                return;
            }

            // 已验证清除key
            redisTemplate.delete(CommonConstant.PRE_EMAIL + email);
            // 验证成功 放行
            chain.doFilter(request, response);
            return;
        }
        // 无需验证 放行
        chain.doFilter(request, response);
    }
}
