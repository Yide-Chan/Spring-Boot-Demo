package com.chenyide.kafka.handle;

import com.chenyide.kafka.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author chenyide
 * @version v1.0
 * @className KafkaHandle
 * @description
 * @date 2024/6/13 12:34
 **/
@Slf4j
@Component
public class KafkaMessageHandle {
    @KafkaListener(topics = KafkaConstants.TOPIC_TEST, containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord record, Acknowledgment acknowledgment) {
        try {
            String message = (String) record.value();
            log.info("收到消息: {}", message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }
}
