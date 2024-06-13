package com.chenyide.rabbitmq.constants;
/**
 * @className RabbitMqConstants
 * @description  
 * @author chenyide
 * @date 2024/4/18 13:54
 * @version v1.0
**/

public class RabbitMqConstants {

    public final static String TEST1_QUEUE = "test1-queue";

    public final static String TEST2_QUEUE = "test2-queue";

    public final static String EXCHANGE_NAME = "test.topic.exchange";

    public final static String TOPIC_TEST1_ROUTINGKEY = "topic.test1.*";

    public final static String TOPIC_TEST1_ROUTINGKEY_TEST = "topic.test1.test";

    public final static String TOPIC_TEST2_ROUTINGKEY = "topic.test2.*";

    public final static String TOPIC_TEST2_ROUTINGKEY_TEST = "topic.test2.test";

}
