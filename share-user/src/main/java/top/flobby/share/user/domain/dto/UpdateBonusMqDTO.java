package top.flobby.share.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 消息队列DTO
 * @create : 2023-10-13 16:09
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBonusMqDTO {
    private Integer bonus;
    private Long userId;
}
