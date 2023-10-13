package top.flobby.share.user.rocketmq;

import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import top.flobby.share.common.util.SnowUtil;
import top.flobby.share.user.domain.dto.UpdateBonusMqDTO;
import top.flobby.share.user.domain.entity.BonusEventLog;
import top.flobby.share.user.domain.entity.User;
import top.flobby.share.user.mapper.BonusEventLogMapper;
import top.flobby.share.user.mapper.UserMapper;

import java.util.Date;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 消息队列监听类
 * @create : 2023-10-13 16:19
 **/

@Service
@RocketMQMessageListener(consumerGroup = "consumer", topic = "add-bonus")
public class AddBonusListener implements RocketMQListener<UpdateBonusMqDTO> {

    @Resource
    private UserMapper userMapper;
    @Resource
    private BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UpdateBonusMqDTO updateBonusMqDTO) {
        // 1. 给用户加积分
        Long userId = updateBonusMqDTO.getUserId();
        User user = userMapper.selectById(userId);
        user.setBonus(user.getBonus() + updateBonusMqDTO.getBonus());
        userMapper.updateById(user);

        // 2. 写入日志
        bonusEventLogMapper.insert(BonusEventLog.builder()
                .id(SnowUtil.getSnowflakeNextId())
                .userId(updateBonusMqDTO.getUserId())
                .value(updateBonusMqDTO.getBonus())
                .event("CONTRIBUTE")
                .createTime(new Date())
                .description("投稿加积分")
                .build());
    }
}
