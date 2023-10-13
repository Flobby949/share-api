package top.flobby.share.content.domain.dto;

import lombok.Data;
import top.flobby.share.common.enums.AuditStatusEnum;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 审核DTO
 * @create : 2023-10-13 15:25
 **/

@Data
public class ShareAuditDTO {
    private AuditStatusEnum auditStatusEnum;
    private String reason;
    private Boolean showFlag;
}
