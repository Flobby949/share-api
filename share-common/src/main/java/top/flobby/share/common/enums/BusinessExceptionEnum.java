package top.flobby.share.common.enums;

import lombok.Getter;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 自定义异常枚举
 * @create : 2023-10-07 13:26
 **/

@Getter
public enum BusinessExceptionEnum {
    PHONE_NOT_EXIST("手机号不存在"),
    PHONE_EXIST("手机号已被注册"),
    PASSWORD_ERROR("密码错误"),
    ALREADY_HAS_CHECK("今日已经签到");

    private final String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }
}
