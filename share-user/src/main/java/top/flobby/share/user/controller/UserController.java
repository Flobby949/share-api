package top.flobby.share.user.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.common.util.JwtUtil;
import top.flobby.share.user.domain.dto.LoginDTO;
import top.flobby.share.user.domain.dto.UpdateBonusDTO;
import top.flobby.share.user.domain.entity.BonusEventLog;
import top.flobby.share.user.domain.entity.User;
import top.flobby.share.user.domain.vo.UserLoginVO;
import top.flobby.share.user.service.UserService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Value("${web.custom-file-upload}")
    private String uploadPath;

    @PostMapping("upload")
    public CommonResp<String> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        File fileDir = new File(uploadPath + date);
        if (!fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        String originFileName = file.getOriginalFilename();
        if (StringUtils.isBlank(originFileName)) {
            return CommonResp.error();
        }
        String suffix = originFileName.substring(originFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + suffix;
        // 上传：文件复制搬运
        file.transferTo(new File(fileDir, newFileName));
        // 拼接返回上传文件访问路径
        return CommonResp.success(String.format("%s://%s:%s/%s/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                date, newFileName));
    }
}
