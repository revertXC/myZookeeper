package com.revert.curator.demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * xiecong
 */
public class DoTestLock {

    public static void main(String[] args) throws InterruptedException {
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

        //可重入的互斥锁
        InterProcessMutex lock = new InterProcessMutex(client, "/locks2222");
        final CountDownLatch countdown = new CountDownLatch(1);

        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //加锁
                        lock.acquire();
                        countdown.await();
                        //-------------业务处理开始
                        genarNo();
                        //-------------业务处理结束
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            //释放
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            },"t" + i).start();
        }
        Thread.sleep(10000000);
        countdown.countDown();

    }

    public static void genarNo(){
        String tName = Thread.currentThread().getName();
        System.out.println(tName+"线程执行业务·····");
    }

}
