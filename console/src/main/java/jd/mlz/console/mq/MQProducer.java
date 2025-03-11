package jd.mlz.console.mq;

/**
 * @author wangfeiyu
 * * @date 2025-03-11
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@Slf4j
public class MQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(BigInteger message) {
        rabbitTemplate.convertAndSend("gc_record_export_task_id", message);
        log.info("发送消息: " + message);
    }
}
