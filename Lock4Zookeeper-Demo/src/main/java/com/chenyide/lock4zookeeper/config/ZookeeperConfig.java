package com.chenyide.lock4zookeeper.config;

import com.chenyide.lock4zookeeper.properties.zookeeperProperties;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyide
 * @version v1.0
 * @className ZookeeperConfig
 * @description
 * @date 2024/6/12 0:33
 **/

@Configuration
@EnableConfigurationProperties(zookeeperProperties.class)
public class ZookeeperConfig {
    private final zookeeperProperties zkProps;

    @Autowired
    public ZookeeperConfig(zookeeperProperties zkProps) {
        this.zkProps = zkProps;
    }

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkProps.getTimeout(), zkProps.getRetry());
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkProps.getUrl(), retryPolicy);
        client.start();
        return client;
    }
}
