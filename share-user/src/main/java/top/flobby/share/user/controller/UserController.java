package top.flobby.share.user.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Long count() {
        return userService.count();
    }

}
