package top.flobby.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.common.util.JwtUtil;
import top.flobby.share.content.domain.entity.Share;
import top.flobby.share.content.service.ShareService;

import java.util.List;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 管理员列表
 * @create : 2023-10-13 14:56
 **/

@RestController
@RequestMapping("share/admin")
public class ShareAdminController {
    @Resource
    private ShareService shareService;

    @GetMapping("list")
    public CommonResp<List<Share>> getSharesNotYet(@RequestParam(defaultValue = "1", required = false) Integer pageNo,
                                                   @RequestParam(defaultValue = "3", required = false) Integer pageSize,
    @RequestHeader(value = "token")String token) {
        String role = JwtUtil.getJSONObject(token).getStr("roles");
        if (!"admin".equals(role)) {
            return CommonResp.error("无权限");
        }
        return CommonResp.success(shareService.notPassShareList(pageSize, pageNo));
    }
}
