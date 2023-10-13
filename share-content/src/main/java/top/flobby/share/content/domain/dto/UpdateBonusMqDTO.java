package top.flobby.share.content.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 消息队列DTO
 * @create : 2023-10-13 16:09
 **/

@Data
@Builder
public class UpdateBonusMqDTO {
    private Integer bonus;
    private Long userId;
}
