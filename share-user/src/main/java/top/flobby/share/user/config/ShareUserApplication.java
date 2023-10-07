package top.flobby.share.user.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 用户模块启动类
 * @create : 2023-10-07 09:56
 **/

@Slf4j
@SpringBootApplication(scanBasePackages = {"top.flobby"})
@MapperScan("top.flobby.share.*.mapper")
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
