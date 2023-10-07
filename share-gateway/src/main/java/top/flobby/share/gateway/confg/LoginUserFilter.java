package top.flobby.share.gateway.confg;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.flobby.share.gateway.util.JwtUtil;

/**
 * @author : Flobby
 * @program : share-api
 * @description : token 校验过滤器
 * @create : 2023-10-07 14:28
 **/

@Slf4j
@Component
public class LoginUserFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        // 排除不需要拦截的请求
        if (path.contains("/admin")
                || path.contains("/hello")
                || path.contains("/user/login")
                || path.contains("/user/register")) {
            log.info("不需要登录验证：{}", path);
            return chain.filter(exchange);
        } else {
            log.info("需要登录验证：{}", path);
        }

        // 获取 header 的 token 参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
        log.info("会员登录验证开始，token：{}", token);
        if (CharSequenceUtil.isBlank(token)) {
            log.warn("token 为空，请求被拦截！, {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 校验 token 是否有效
        boolean validate = JwtUtil.validate(token);
        if (validate) {
            log.info("token 有效");
            return chain.filter(exchange);
        } else {
            log.warn("token 无效，拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * 设置优先级
     * 值越小，优先级越高
     *
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
