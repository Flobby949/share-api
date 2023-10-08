package top.flobby.share.content.service;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.flobby.share.common.enums.AuditStatusEnum;
import top.flobby.share.content.domain.entity.MidUserShare;
import top.flobby.share.content.domain.entity.Share;
import top.flobby.share.content.mapper.MidUserShareMapper;
import top.flobby.share.content.mapper.ShareMapper;

import java.util.List;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 分享业务
 * @create : 2023-10-08 17:42
 **/

@Slf4j
@Service
public class ShareService {

    @Resource
    private ShareMapper shareMapper;
    @Resource
    private MidUserShareMapper midUserShareMapper;

    /**
     * 查询用户可查看的资源列表
     *
     * @param title title
     * @param userId userId
     * @return {@link List}<{@link Share}>
     */
    public List<Share> getList(String title, Long userId) {
        // 构造查询条件
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        // 按照 id 降序查询所有数据
        wrapper.orderByDesc(Share::getId);
        // 如果标题关键词不空，则加上模糊查询条件，否则结果即所有数据
        if (CharSequenceUtil.isNotEmpty(title)) {
            wrapper.like(Share::getTitle, title);
        }
        // 过滤出所有已经通过审核的数据并需要显示的数据
        wrapper.eq(Share::getAuditStatus, AuditStatusEnum.PASSED.getDesc()).eq(Share::getShowFlag, true);
        // 执行按条件查询
        List<Share> shares = shareMapper.selectList(wrapper);
        log.info(shares + "");
        // 处理后的 Share 数据列表
        List<Share> sharesDeal;
        // 1. 如果用户未登录，name downloadUrl 全部设为 null
        if (userId == null) {
            sharesDeal = shares.stream().peek(share -> share.setDownloadUrl(null)).toList();
        }
        // 2. 如果用户登录了，那么查询 mid_user_share，如果没有数据，那么这条数据，那么这条 share 的 downloadUrl 也设为 null
        // 只有自己分享的资源才能直接看到下载链接，否则显示“兑换”
        else {
            sharesDeal = shares.stream().peek(share -> {
                MidUserShare midUserShare = midUserShareMapper.selectOne(new QueryWrapper<MidUserShare>().lambda()
                        .eq(MidUserShare::getUserId, userId)
                        .eq(MidUserShare::getShareId, share.getId())
                );
                if (midUserShare == null) {
                    share.setDownloadUrl(null);
                }
            }).toList();
        }
        return sharesDeal;
    }

}
