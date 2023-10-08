package top.flobby.share.common.enums;

import lombok.Getter;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 审核类型枚举
 * @create : 2023-10-08 17:47
 **/

@Getter
public enum AuditStatusEnum {
    PASSED("PASS"),
    NOT_YET("NOT_YET"),
    REJECTED("REJECTED");

    private final String desc;

    AuditStatusEnum(String desc) {
        this.desc = desc;
    }
}
