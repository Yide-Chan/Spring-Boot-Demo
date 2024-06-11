package com.chenyide.lock4zookeeper.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyide
 * @version v1.0
 * @className zookeeperProperties
 * @description 配置项
 * @date 2024/6/12 0:35
 **/
@Data
@ConfigurationProperties(prefix = "zk")
public class zookeeperProperties {
    /**
     * 连接地址
     */
    private String url;

    /**
     * 超时时间(毫秒)，默认1000
     */
    private int timeout = 1000;

    /**
     * 重试次数，默认3
     */
    private int retry = 3;
}
