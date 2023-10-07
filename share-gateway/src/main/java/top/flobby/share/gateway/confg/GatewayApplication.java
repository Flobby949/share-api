package top.flobby.share.gateway.confg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 网关启动类
 * @create : 2023-10-07 12:21
 **/


@SpringBootApplication(scanBasePackages = {"top.flobby"})
@Slf4j
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        log.info("{}，启动成功！", env.getProperty("spring.application.name"));
        log.info("网关地址：127.0.0.1:{}", env.getProperty("server.port"));
    }
}
