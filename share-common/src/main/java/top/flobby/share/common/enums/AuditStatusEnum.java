package top.flobby.share.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 审核类型枚举
 * @create : 2023-10-08 17:47
 **/

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    PASS,
    NOT_YET,
    REJECTED
}
