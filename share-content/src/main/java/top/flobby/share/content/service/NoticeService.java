package top.flobby.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
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

@Service
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    public Notice getLatestNotice() {
        List<Notice> noticeList = noticeMapper.selectList(new LambdaQueryWrapper<Notice>().eq(Notice::getShowFlag, 1).orderByDesc(Notice::getId));
        return noticeList.get(0);
    }
}
