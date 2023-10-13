package top.flobby.share.content.service;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.flobby.share.common.enums.AuditStatusEnum;
import top.flobby.share.common.util.SnowUtil;
import top.flobby.share.content.domain.dto.ExchangeDTO;
import top.flobby.share.content.domain.dto.ShareSubmitDTO;
import top.flobby.share.content.domain.entity.MidUserShare;
import top.flobby.share.content.domain.entity.Share;
import top.flobby.share.content.domain.vo.ShareVO;
import top.flobby.share.content.feign.UserService;
import top.flobby.share.content.feign.model.UpdateBonusDTO;
import top.flobby.share.content.feign.model.User;
import top.flobby.share.content.mapper.MidUserShareMapper;
import top.flobby.share.content.mapper.ShareMapper;

import java.util.Date;
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
    @Resource
    private UserService userService;

    /**
     * 查询用户可查看的资源列表
     *
     * @param title title
     * @param userId userId
     * @return {@link List}<{@link Share}>
     */
    public List<Share> getList(String title, Integer pageNo, Integer pageSize, Long userId) {
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

        // 分页器
        Page<Share> page = Page.of(pageNo, pageSize);

        // 执行按条件查询
        List<Share> shares = shareMapper.selectList(page, wrapper);
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

    /**
     * 获取咨询内容信息
     *
     * @param id id
     * @return {@link ShareVO}
     */
    public ShareVO getShareById(Long id) {
        Share share = shareMapper.selectById(id);
        User publisher = userService.getUserById(share.getUserId()).getData();
        return ShareVO.builder()
                .share(share)
                .nickname(publisher.getNickname())
                .avatarUrl(publisher.getAvatarUrl())
                .build();
    }

    /**
     * 兑换资源
     *
     * @param exchangeDTO dto
     * @return {@link Share}
     */
    @Transactional(rollbackFor = Exception.class)
    public Share exchangeShare(ExchangeDTO exchangeDTO) {
        // 查询信息
        Share share = shareMapper.selectById(exchangeDTO.getShareId());
        if (share == null) {
            throw new IllegalArgumentException("资源不存在！");
        }
        User user = userService.getUserById(exchangeDTO.getUserId()).getData();
        // 用户是否兑换过
        MidUserShare midUserShare = midUserShareMapper.selectOne(new QueryWrapper<MidUserShare>().lambda()
                        .eq(MidUserShare::getUserId, exchangeDTO.getUserId())
                        .eq(MidUserShare::getShareId, exchangeDTO.getShareId()));
        if (midUserShare != null) {
            return share;
        }
        // 判断用户积分是否足够兑换
        if (user.getBonus() < share.getPrice()) {
            throw new IllegalArgumentException("用户积分不足！");
        }
        // 更新用户积分
        userService.updateBonus(UpdateBonusDTO.builder()
                        .bonus(share.getPrice() * -1)
                        .desc("兑换分享")
                        .event("BUY")
                        .userId(exchangeDTO.getUserId())
                .build());
        // 插入关联表数据
        midUserShareMapper.insert(MidUserShare.builder()
                        .id(SnowUtil.getSnowflakeNextId())
                        .shareId(exchangeDTO.getShareId())
                        .userId(exchangeDTO.getUserId())
                .build());
        return share;
    }

    public int contribute(ShareSubmitDTO shareSubmitDTO) {
        Share share = Share.builder()
                .id(SnowUtil.getSnowflakeNextId())
                .userId(shareSubmitDTO.getUserId())
                .title(shareSubmitDTO.getTitle())
                .createTime(new Date())
                .updateTime(new Date())
                .isOriginal(shareSubmitDTO.getIsOriginal())
                .author(shareSubmitDTO.getAuthor())
                .cover(shareSubmitDTO.getCover())
                .summary(shareSubmitDTO.getSummary())
                .price(shareSubmitDTO.getPrice())
                .downloadUrl(shareSubmitDTO.getDownloadUrl())
                .buyCount(0)
                .showFlag(false)
                .auditStatus("NOT YET")
                .reason("暂未审核")
                .build();
        return shareMapper.insert(share);
    }

    /**
     * 分页查询我的投稿
     *
     * @param userId userId
     * @param pageSize pageSize
     * @param pageNo pageNo
     * @return {@link List}<{@link Share}>
     */
    public List<Share> myContributeList(Long userId, Integer pageSize, Integer pageNo) {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Share::getUserId, userId);
        Page<Share> page = Page.of(pageNo, pageSize);
        return shareMapper.selectList(page, wrapper);
    }

    /**
     * 分页查询待审核列表
     *
     * @param pageSize pageSize
     * @param pageNo pageNo
     * @return {@link List}<{@link Share}>
     */
    public List<Share> notPassShareList(Integer pageSize, Integer pageNo) {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Share::getAuditStatus, AuditStatusEnum.NOT_YET).eq(Share::getShowFlag, false);
        Page<Share> page = Page.of(pageNo, pageSize);
        return shareMapper.selectList(page, wrapper);
    }

}
