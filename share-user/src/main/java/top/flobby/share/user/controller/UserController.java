package top.flobby.share.user.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.user.domain.dto.LoginDTO;
import top.flobby.share.user.domain.entity.User;
import top.flobby.share.user.service.UserService;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 用户接口
 * @create : 2023-10-07 12:49
 **/

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("count")
    public CommonResp<Long> count() {
        return CommonResp.success(userService.count());
    }

    @PostMapping("login")
    public CommonResp<User> login(@Valid @RequestBody LoginDTO loginDTO) {
        return CommonResp.success(userService.login(loginDTO));
    }

    @PostMapping("register")
    public CommonResp<Long> register(@Valid @RequestBody LoginDTO loginDTO) {
        return CommonResp.success(userService.register(loginDTO));
    }

}
