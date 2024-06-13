package com.chenyide.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import static com.chenyide.rabbitmq.constants.RabbitMqConstants.EXCHANGE_NAME;



/**
 * @author chenyide
 * @version v1.0
 * @className RoutingConsumer
 * @description
 * @date 2024/4/18 15:22
 **/
@Slf4j
public class RoutingConsumer {
    public void RoutingConsumer(Channel channel, Message message) {
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            //声明一个队列
            channel.queueDeclare("console", false, false, false, null);

            //绑定交换机与队列
            channel.queueBind("console", EXCHANGE_NAME, "info");
            channel.queueBind("console", EXCHANGE_NAME, "warning");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("ReceiveLogsDirect01控制台打印接受到的消息：" + delivery.getBody().toString());
            };

            channel.basicConsume("console", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Test1消费消息：" + message.toString() + "。失败！");
        }
    }
}
