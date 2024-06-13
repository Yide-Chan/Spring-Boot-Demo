package com.chenyide.rabbitmq.product;

import com.chenyide.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.chenyide.rabbitmq.constants.RabbitMqConstants.EXCHANGE_NAME;


/**
 * @author chenyide
 * @version v1.0
 * @className RoutingProduct
 * @description
 * @date 2024/4/18 15:19
 **/
@Slf4j
public class RoutingProduct {
    public void DirectLogs() {
        try {
            Channel channel = RabbitMqUtils.getChannel();
            //BuiltinExchangeType.DIRECT 交换机的枚举类型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            //创建多个 bindingKey
            Map<String, String> bindingKeyMap = new HashMap<>();
            bindingKeyMap.put("info", "普通 info 信息");
            bindingKeyMap.put("warning", "警告 warning 信息");
            bindingKeyMap.put("error", "错误 error 信息");

            //debug 没有消费这接收这个消息 所有就丢失了
            bindingKeyMap.put("debug", "调试 debug 信息");
            for (Map.Entry<String, String> bindingKeyEntry : bindingKeyMap.entrySet()) {
                String bindingKey = bindingKeyEntry.getKey();
                String message = bindingKeyEntry.getValue();

                channel.basicPublish(EXCHANGE_NAME, bindingKey, null, message.getBytes("UTF-8"));
                System.out.println("生产者发出消息:" + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发送失败！");
        }
    }
}

