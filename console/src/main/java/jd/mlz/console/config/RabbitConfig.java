package jd.mlz.console.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
* @author wangfeiyu
** @date 2025-03-11
*/

@Configuration
public class RabbitConfig {

    @Bean
    public Queue myQueue() {
        // 参数说明：
        // 1. 队列名称
        // 2. 是否持久化（true表示持久化）
        // 3. 是否排他（false表示不排他）
        // 4. 是否自动删除（false表示不自动删除）
        return new Queue("gc_record_export_task_id", true, false, false);
    }
}

