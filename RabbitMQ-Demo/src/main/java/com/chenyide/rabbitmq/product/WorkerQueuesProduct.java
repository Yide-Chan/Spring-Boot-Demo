package com.chenyide.rabbitmq.product;

import com.chenyide.rabbitmq.constants.RabbitMqConstants;
import com.chenyide.rabbitmq.utils.RabbitMqUtils;
import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenyide
 * @version v1.0
 * @className WorkerQueuesProduct
 * @description
 * @date 2024/4/18 14:17
 **/

public class WorkerQueuesProduct {

    @RabbitListener(queues = RabbitMqConstants.TEST1_QUEUE)
    public void Product(Message message) throws IOException, TimeoutException {
        // 发送大量消息
        Channel channel = RabbitMqUtils.getChannel();

        /**
         * public static QueueDeclareOk QueueDeclare(String queue, Boolean durable, Boolean exclusive, Boolean autoDelete, IDictionary arguments);
         * queue:声明的队列名称
         * durable：是否持久化，是否将队列持久化到mnesia数据库中，有专门的表保存我们的队列声明。
         * exclusive：排外，①当前定义的队列是connection的channel是共享的，其他的connection是访问不到的。②当connection关闭的时候，队列将被删除。
         * autoDelete：自动删除，当最后一个consumer(消费者)断开之后，队列将自动删除。
         * arguments：参数是rabbitmq的一个扩展，功能非常强大，基本是AMPQ中没有的。
         */
        channel.queueDeclare(RabbitMqConstants.TEST1_QUEUE, false, false, false, null);
        /**
         * void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * 发布消息
         * 发布到不存在的交换器将导致信道级协议异常，该协议关闭信道，
         * exchange: 要将消息发送到的交换器
         * routingKey: 路由KEY
         * props: 消息的其它属性，如：路由头等
         * body: 消息体
         */
        channel.basicPublish("", RabbitMqConstants.TOPIC_TEST1_ROUTINGKEY, null, message.getBody());
        System.out.println("发送消息完成：" + message);
    }

}


