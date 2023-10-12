package top.flobby.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import top.flobby.share.content.domain.entity.Notice;
import top.flobby.share.content.mapper.NoticeMapper;

import java.util.List;

/**
 * @author : Flobby
 * @program : share-api
 * @description : service
 * @create : 2023-10-08 14:27
 **/

@Slf4j
@Service
@RefreshScope
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    @Value(value = "${notice.flag}")
    private Boolean flag;

    public Notice getLatestNotice() {
        if (Boolean.FALSE.equals(flag)) {
            return Notice.builder()
                    .content("系统正在维护中...")
                    .build();
        }
        List<Notice> noticeList = noticeMapper.selectList(new LambdaQueryWrapper<Notice>().eq(Notice::getShowFlag, 1).orderByDesc(Notice::getId));
        return noticeList.get(0);
    }
}
