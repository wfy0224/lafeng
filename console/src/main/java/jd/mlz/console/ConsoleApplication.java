package jd.mlz.console;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@SpringBootApplication(scanBasePackages = "jd.mlz")
@MapperScan("jd.mlz.module.module.*.mapper")
@EnableAdminServer
public class ConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }
}
