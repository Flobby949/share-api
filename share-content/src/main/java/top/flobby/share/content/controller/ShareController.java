package top.flobby.share.content.controller;

import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.common.util.JwtUtil;
import top.flobby.share.content.domain.dto.ExchangeDTO;
import top.flobby.share.content.domain.entity.Notice;
import top.flobby.share.content.domain.entity.Share;
import top.flobby.share.content.domain.vo.ShareVO;
import top.flobby.share.content.service.NoticeService;
import top.flobby.share.content.service.ShareService;

import java.util.List;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 接口
 * @create : 2023-10-08 14:28
 **/


@Slf4j
@RestController
@RequestMapping("share")
public class ShareController {
    @Resource
    private NoticeService noticeService;
    @Resource
    private ShareService shareService;

    private final int MAX = 100;

    @GetMapping("notice")
    public CommonResp<Notice> getLatestNotice() {
        return CommonResp.success(noticeService.getLatestNotice());
    }

    @GetMapping("list")
    public CommonResp<List<Share>> getShareList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "3") Integer pageSize,
            @RequestHeader(required = false, value = "token") String token
    ) {
        if (pageSize > MAX) {
            pageSize = MAX;
        }
        return CommonResp.success(shareService.getList(title, pageNo, pageSize, getUserIdFromToken(token)));
    }

    private long getUserIdFromToken(String token) {
        long userId = 0;
        String noToken = "no-token";
        if (!noToken.equals(token)) {
            JSONObject tokenObject = JwtUtil.getJSONObject(token);
            log.info("解析到的 token 数据为：{}", tokenObject);
            userId = tokenObject.getLong("id");
        } else {
            log.info("没有 token");
        }
        return userId;
    }

    @GetMapping("{id}")
    public CommonResp<ShareVO> getShareById(@PathVariable Long id) {
        return CommonResp.success(shareService.getShareById(id));
    }

    @PostMapping("exchange")
    public CommonResp<Share> exchangeContent(@RequestBody @Valid ExchangeDTO exchangeDTO) {
        return CommonResp.success(shareService.exchangeShare(exchangeDTO));
    }
}
