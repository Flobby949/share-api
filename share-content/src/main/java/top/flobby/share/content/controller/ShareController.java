package top.flobby.share.content.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.content.domain.entity.Notice;
import top.flobby.share.content.domain.entity.Share;
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

    @GetMapping("notice")
    public CommonResp<Notice> getLatestNotice() {
        return CommonResp.success(noticeService.getLatestNotice());
    }

    @GetMapping("list")
    public CommonResp<List<Share>> getShareList(@RequestParam(required = false) String title) {
        return CommonResp.success(shareService.getList(title, 1L));
    }
}
