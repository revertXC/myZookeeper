package com.revert.curator.lock.common.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xiecong
 */
@Configuration
@ConfigurationProperties(prefix = "zk")
public class CuratorConfig {

    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private int sessionTimeout;


    @Bean(name = "curatorFramework", initMethod = "start")
    public CuratorFramework curatorFramework(){
        /** 重试策略
         *      baseSleepTimeMs 重试初始时间
         *      maxRetries 最大重试次数
         * */
        RetryPolicy retryPolicy  = new ExponentialBackoffRetry(1000,3);

        /**
         * zookeeper 连接
         *      connectString 连接地址
         *      sessionTimeoutMs 会话超时时间
         *      connectionTimeoutMs 连接超时时间
         *      retryPolicy 重试策略
         * */
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(url)
                .sessionTimeoutMs(sessionTimeout)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        return client;
    }




}
