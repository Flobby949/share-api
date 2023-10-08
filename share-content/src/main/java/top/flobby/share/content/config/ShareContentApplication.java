package top.flobby.share.content.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import top.flobby.share.common.annotation.ShareApiApplication;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 内容模块启动类
 * @create : 2023-10-08 14:00
 **/

@Slf4j
@ShareApiApplication
public class ShareContentApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ShareContentApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        log.info("{} 模块，启动成功！", env.getProperty("spring.application.name"));
    }
}
