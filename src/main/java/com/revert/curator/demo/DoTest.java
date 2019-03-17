package com.revert.curator.demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * xiecong
 */
public class DoTest {

    public static void main(String[] args) throws Exception {
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
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(3000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();

        String nodePath = "/curatorNode";

        /** 检查是否存在*/
        Stat stat = client.checkExists().forPath(nodePath);
        if(stat == null){
            /** 默认创建持久节点*/
            String newNodePath = client.create().forPath(nodePath, "hello World".getBytes());
            /** 临时节点 有序*/
            newNodePath = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(nodePath+"/locks/", "hello World".getBytes());
            System.out.println("创建节点成功："+newNodePath);
        }else{
            System.out.println("已经存在节点："+nodePath);
        }

        /** 获取节点值*/
        byte val[] = client.getData().forPath(nodePath);
        System.out.println("获取节点值："+new String(val));

        client.setData().forPath(nodePath, "update value！！！".getBytes());

        val = client.getData().forPath(nodePath);
        System.out.println("修改之后："+ new String(val));

        /** 节点删除*/
        client.delete().forPath(nodePath);
        System.out.println("删除节点成功："+nodePath);


    }

}
