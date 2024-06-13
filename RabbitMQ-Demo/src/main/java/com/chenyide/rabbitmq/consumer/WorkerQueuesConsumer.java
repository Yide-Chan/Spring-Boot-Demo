package com.chenyide.rabbitmq.consumer;

import com.chenyide.rabbitmq.constants.RabbitMqConstants;
import com.chenyide.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenyide
 * @version v1.0
 * @className WorkerQueuesConsumer
 * @description
 * @date 2024/4/18 14:22
 **/
@Slf4j
public class WorkerQueuesConsumer {
    @RabbitListener(queues = RabbitMqConstants.TEST1_QUEUE)
    public void Consumer() throws IOException, TimeoutException {
        // 接受消息
        //使用工具类创建连接工厂并获取信道
        Channel channel = RabbitMqUtils.getChannel();
        // 接受消息参数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //进行手动应答
            /**
             * 参数1：消息的标记  tag
             * 参数2：multiple 是否批量应答，false：不批量应答 true：批量
             **/
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            System.out.println("接受到的消息：" + message.getBody());
        };
        // 取消消费参数
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag + "消费者取消消费借口回调逻辑");
        };
        // 采用手动应答
        boolean autoAck = false;
        /**
         * String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback)
         * 启动一个消费者，并返回服务端生成的消费者标识
         * queue:队列名
         * autoAck：true 接收到传递过来的消息后acknowledged（应答服务器），false 接收到消息后不应答服务器
         * deliverCallback： 当一个消息发送过来后的回调接口
         * cancelCallback：当一个消费者取消订阅时的回调接口;取消消费者订阅队列时除了使用{@link Channel#basicCancel}之外的所有方式都会调用该回调方法
         * @return 服务端生成的消费者标识
         */
        channel.basicConsume(RabbitMqConstants.TEST1_QUEUE, autoAck, deliverCallback, cancelCallback);
    }

    @RabbitListener(queues = RabbitMqConstants.TEST1_QUEUE)
    public void Consumer1(Message message, Channel channel) {
        try {
            //手动确认消息已经被消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("Test1消费消息：" + message.toString() + "。成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Test1消费消息：" + message.toString() + "。失败！");
        }
    }
}
