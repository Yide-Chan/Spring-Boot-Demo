package com.chenyide.rabbitmq.product;

import com.chenyide.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;


import java.util.HashMap;
import java.util.Map;

import static com.chenyide.rabbitmq.constants.RabbitMqConstants.EXCHANGE_NAME;


/**
 * @author chenyide
 * @version v1.0
 * @className TopicProduct
 * @description
 * @date 2024/4/18 14:55
 **/
@Slf4j
public class TopicProduct {
    public TopicProduct() {
        try (Channel channel = RabbitMqUtils.getChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            /**
             * Q1-->绑定的是
             *      中间带orange带3个单词的字符串(*.orange.*)
             * Q2-->绑定的是
             *      最后一个单词是rabbit的3个单词(*.*.rabbit)
             *      第一个单词是lazy的多个单词(lazy.#)
             *
             */
            Map<String, String> bindingKeyMap = new HashMap<>();
            bindingKeyMap.put("quick.orange.rabbit", "被队列Q1Q2接收到");
            bindingKeyMap.put("lazy.orange.elephant", "被队列Q1Q2接收到");
            bindingKeyMap.put("quick.orange.fox", "被队列Q1接收到");
            bindingKeyMap.put("lazy.brown.fox", "被队列Q2接收到");

            bindingKeyMap.put("lazy.pink.rabbit", "虽然满足两个绑定但只被队列Q2接收一次");
            bindingKeyMap.put("quick.brown.fox", "不匹配任何绑定不会被任何队列接收到会被丢弃");
            bindingKeyMap.put("quick.orange.male.rabbit", "是四个单词不匹配任何绑定会被丢弃");
            bindingKeyMap.put("lazy.orange.male.rabbit", "是四个单词但匹配Q2");

            // 从map中获取发送消息的routingKey和消息本体
            for (Map.Entry<String, String> bindingKeyEntry : bindingKeyMap.entrySet()) {
                String bindingKey = bindingKeyEntry.getKey();
                String message = bindingKeyEntry.getValue();
                channel.basicPublish(EXCHANGE_NAME, bindingKey, null,
                        message.getBytes("UTF-8"));
                System.out.println("生产者发出消息" + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发送消息失败！");
        }
    }
}
