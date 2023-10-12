package top.flobby.share.content.feign.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 更新积分DTO
 * @create : 2023-10-12 11:17
 **/

@Data
@Builder
public class UpdateBonusDTO {
    private Integer bonus;
    private String event;
    private String desc;
    private Long userId;
}
