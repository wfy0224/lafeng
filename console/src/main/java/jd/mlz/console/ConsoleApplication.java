package jd.mlz.console;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@SpringBootApplication(scanBasePackages = "jd.mlz")
@MapperScan("jd.mlz.module.module.*.mapper")
public class ConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }
}
