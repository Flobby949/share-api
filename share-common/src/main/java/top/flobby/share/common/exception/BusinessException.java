package top.flobby.share.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.flobby.share.common.enums.BusinessExceptionEnum;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 自定义异常
 * @create : 2023-10-07 13:30
 **/

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException{
    private BusinessExceptionEnum e;
}
