package top.flobby.share.common.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 时间格式处理配置
 * @create : 2023-10-12 09:35
 **/

@Configuration
public class DateTimeFormatConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置全局日期格式
            jacksonObjectMapperBuilder.timeZone("Asia/Shanghai"); // 设置时区
        };
    }
}
