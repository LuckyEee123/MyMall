package com.mai.mall.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mai.mall.common.domain.LoginUser;
import com.mai.mall.utils.TokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * 自定义退出处理类 返回成功
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = TokenUtils.getLoginUser(request);
        if (loginUser != null) {
            // 删除用户缓存记录
            TokenUtils.logout(loginUser.getTokenUUID());
            // TODO 记录用户退出日志
        }
    }
}
