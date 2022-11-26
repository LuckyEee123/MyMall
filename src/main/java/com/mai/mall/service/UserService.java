package com.mai.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mai.mall.controller.dto.LoginDTO;
import com.mai.mall.pojo.User;

public interface UserService extends IService<User> {
    LoginDTO login(User user);

    User getByUsername(String username);
}
