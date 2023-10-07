package top.flobby.share.user.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 用户模块启动类
 * @create : 2023-10-07 09:56
 **/

@SpringBootApplication(scanBasePackages = {"top.flobby"})
public class ShareUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShareUserApplication.class, args);
    }
}
