package top.flobby.share.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 测试接口
 * @create : 2023-10-07 09:57
 **/

@RestController
public class TestController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
