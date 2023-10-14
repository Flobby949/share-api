package top.flobby.share.user.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.common.util.JwtUtil;
import top.flobby.share.user.domain.dto.LoginDTO;
import top.flobby.share.user.domain.dto.UpdateBonusDTO;
import top.flobby.share.user.domain.entity.BonusEventLog;
import top.flobby.share.user.domain.entity.User;
import top.flobby.share.user.domain.vo.UserLoginVO;
import top.flobby.share.user.service.UserService;

import java.util.List;

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
    public CommonResp<UserLoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return CommonResp.success(userService.login(loginDTO));
    }

    @PostMapping("register")
    public CommonResp<Long> register(@Valid @RequestBody LoginDTO loginDTO) {
        return CommonResp.success(userService.register(loginDTO));
    }

    @GetMapping("{id}")
    public CommonResp<User> getUserById(@PathVariable Long id) {
        return CommonResp.success(userService.getUserById(id));
    }

    @PutMapping("bonus")
    public CommonResp<User> updateBonus(@RequestBody UpdateBonusDTO updateBonusDTO) {
        return CommonResp.success(userService.updateUserBonus(updateBonusDTO));
    }

    @GetMapping("bonus")
    public CommonResp<List<BonusEventLog>> getUserBonusLog(@RequestParam(defaultValue = "1", required = false) Integer pageNo,
                                                           @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                                           @RequestHeader(value = "token") String token) {
        return CommonResp.success(userService.userBonusLog(JwtUtil.getJSONObject(token).getLong("id"), pageSize, pageNo));
    }

    @PostMapping("check")
    public CommonResp<Object> dailyCheck(@RequestHeader(value = "token") String token) {
        userService.dailyCheck(JwtUtil.getJSONObject(token).getLong("id"));
        return CommonResp.success();
    }
}
