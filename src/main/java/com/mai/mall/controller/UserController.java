package com.mai.mall.controller;

import com.mai.mall.common.Result;
import com.mai.mall.pojo.User;
import com.mai.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUsers")
    public Result getUsers() {
        return Result.success(userService.list());
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Valid User user) {
        return Result.success(userService.login(user));
    }

}
