package top.flobby.share.content.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.flobby.share.common.resp.CommonResp;
import top.flobby.share.content.domain.entity.Notice;
import top.flobby.share.content.service.NoticeService;

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

    @GetMapping("notice")
    public CommonResp<Notice> getLatestNotice() {
        return CommonResp.success(noticeService.getLatestNotice());
    }
}
