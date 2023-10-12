package top.flobby.share.common.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 启动类注解
 * @create : 2023-10-08 14:02
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@MapperScan("top.flobby.share.*.mapper")
@SpringBootApplication(scanBasePackages = {"top.flobby"})
@EnableFeignClients(basePackages = {"top.flobby"})
public @interface ShareApiApplication {
}
