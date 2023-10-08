package top.flobby.share.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import top.flobby.share.common.annotation.ShareApiApplication;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 用户模块启动类
 * @create : 2023-10-07 09:56
 **/

@Slf4j
@ShareApiApplication
public class ShareUserApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ShareUserApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        log.info("{} 模块，启动成功！", env.getProperty("spring.application.name"));
        log.info("测试地址：http://127.0.0.1:{}{}/hello",
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"));
    }
}
