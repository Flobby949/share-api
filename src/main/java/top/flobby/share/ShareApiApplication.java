package top.flobby.share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Flobby
 */

@SpringBootApplication
@RestController
public class ShareApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareApiApplication.class, args);
    }

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

}
