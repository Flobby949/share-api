package top.flobby.share.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Flobby
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusEventLog {
    /**
     * Id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 积分操作值
     */
    private Integer value;
    /**
     * 积分事件（签到、投稿、兑换等）
     */
    private String event;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 描述
     */
    private String description;


}
