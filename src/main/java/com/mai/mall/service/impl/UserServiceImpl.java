package com.mai.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mai.mall.common.domain.LoginUser;
import com.mai.mall.controller.dto.LoginDTO;
import com.mai.mall.pojo.User;
import com.mai.mall.exception.ServiceException;
import com.mai.mall.mapper.UserMapper;
import com.mai.mall.service.UserService;
import com.mai.mall.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Lazy  // 解决循环依赖问题
    @Resource
    private AuthenticationManager authenticationManager;

    public LoginDTO login(User user) {
//        String account = user.getAccount();
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("account", account);  // select * from user where account = xxx
//        queryWrapper.or().eq("phone", account);
//        queryWrapper.or().eq("email", account);

        // 用户验证
        Authentication authentication = null;
        try {
//            User dbUser = getOne(queryWrapper);
//            String dbPassword = dbUser.getPassword();
//            if (SecurityUtils.matchesPassword(user.getPassword(), dbPassword)) {
//                return TokenUtils.createToken(user);
//            }

            // 其实就是调用 UserDetailsServiceImpl.loadUserByUsername 方法
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword()));
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            String token = TokenUtils.createToken(loginUser);
            return new LoginDTO(token, loginUser.getUser());
        } catch (Exception e) {
            log.error("login发生异常", e);
            throw new ServiceException("用户名或密码错误");
        }
//        User dbUser = getOne(queryWrapper);
//        if (dbUser != null) {
//            String dbPassword = dbUser.getPassword();
//            if (dbPassword.equals(user.getPassword())) {
//                return dbUser;
//            }
//        }
    }

    public User getByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("account", username)
                .or().eq("email", username)
                .or().eq("phone", username);
        return getOne(queryWrapper);
    }

}
