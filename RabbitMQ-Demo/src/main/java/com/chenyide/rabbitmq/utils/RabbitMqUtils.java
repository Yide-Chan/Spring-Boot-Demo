package com.chenyide.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @author chenyide
 * @version v1.0
 * @className RabbitMqUtils
 * @description
 * @date 2024/4/18 13:40
 **/

/*
 * 此类为连接工厂创建信道的工具类
 * */
@Slf4j
@Component
public class RabbitMqUtils implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    RabbitTemplate rabbitTemplate;

    // 注入内部类接口
    @Autowired
    public void init() {
        // 发布确认
        rabbitTemplate.setConfirmCallback(this);
        /**
         * true：
         *      交换机无法将消息进行路由时，会将该消息返回给生产者
         * false：
         *      如果发现消息无法进行路由，则直接丢弃
         */
        rabbitTemplate.setMandatory(true);
        //设置回退消息交给谁处理
        rabbitTemplate.setReturnCallback(this);
    }


    // 得到一个连接的channel
    public static Channel getChannel() throws IOException, TimeoutException {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.163.128");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }

    /**
     * 交换机确认回调方法
     *
     * @param correlationData 回调的相关数据
     * @param ack             ack为true，nack为false
     * @param cause           原因，对于nack，如果可用，否则为null
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData == null ? "" : correlationData.getId();
        if (ack) {
            System.out.println("Success => " + id);
        } else {
            System.out.println("Failure => " + id + " [" + cause + "] ");
        }
    }

    /**
     * 消息发送到转换器的时候没有对列,配置了备份对列该回调则不生效
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息丢失：exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
    }
}

