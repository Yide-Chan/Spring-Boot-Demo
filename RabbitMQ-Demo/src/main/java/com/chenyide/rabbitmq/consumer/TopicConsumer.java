package com.chenyide.rabbitmq.consumer;


import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import static com.chenyide.rabbitmq.constants.RabbitMqConstants.EXCHANGE_NAME;


/**
 * @author chenyide
 * @version v1.0
 * @className TopicConsumer
 * @description
 * @date 2024/4/18 15:01
 **/
@Slf4j
public class TopicConsumer {
    public void TopicConsumer(Channel channel, Message message) {
        try {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            // 声明Q1队列与绑定关系
            // String queueName="Q1"; 可以改为以下方式灵活获取队列名称
            String queueName = channel.queueDeclare().getQueue();
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "*.orange.*");

            System.out.println("等待接收消息.....");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("接收队列:" + queueName + "绑定键:" + delivery.getEnvelope().getRoutingKey() + ",消息:" + message.getBody());
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Test1消费消息：" + message.toString() + "。失败！");
        }
    }
}
