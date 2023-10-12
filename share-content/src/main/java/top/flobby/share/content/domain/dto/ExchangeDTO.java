package top.flobby.share.content.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 兑换DTO
 * @create : 2023-10-12 12:56
 **/

@Data
public class ExchangeDTO {
    @NotNull(message = "用户不能为空")
    private Long userId;
    @NotNull(message = "请选择要兑换的资源")
    private Long shareId;
}
