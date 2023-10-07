package top.flobby.share.gateway.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Flobby
 * @program : share-api
 * @description : JWT 工具类
 * @create : 2023-10-07 14:04
 **/

@Slf4j
public class JwtUtil {

    /**
     * 盐值很重要，不能泄漏，且每个项目都应该不一样，可以放到配置文件中
     */
    private static final String KEY = "share-api";

    public static boolean validate(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(KEY.getBytes());
        // validate包含了verify
        boolean validate = jwt.validate(0);
        log.info("JWT token 校验结果：{}", validate);
        return validate;
    }

}
