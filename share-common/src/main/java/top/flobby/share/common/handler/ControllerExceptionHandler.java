package top.flobby.share.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.flobby.share.common.exception.BusinessException;
import top.flobby.share.common.resp.CommonResp;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 接口异常处理
 * @create : 2023-10-07 13:21
 **/

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 系统异常处理
     *
     * @param e e
     * @return {@link CommonResp}<{@link Object}>
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResp<Object> exceptionHandler(Exception e) {
        log.error("系统异常:", e);
        return CommonResp.error(e.getMessage());
    }

    /**
     * 自定义异常处理
     *
     * @param e e
     * @return {@link CommonResp}<{@link Object}>
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public CommonResp<Object> exceptionHandler(BusinessException e) {
        log.error("业务异常:", e);
        return CommonResp.error(e.getE().getDesc());
    }

}
