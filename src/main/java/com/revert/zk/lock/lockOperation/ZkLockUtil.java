package com.revert.zk.lock.lockOperation;

import com.revert.zk.common.zkNode.ZkGlobalVar;
import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

@Log4j2
@Component
public class ZkLockUtil {

    @Autowired(required = false)
    private ZooKeeper zooKeeper;

    public static ConcurrentMap<String, CountDownLatch> latch = new ConcurrentHashMap<String, CountDownLatch>();

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 获取锁
     *
     * @param fnName 方法名称
     * @param data 数据
     * @return
     */
    public boolean tryLock(String fnName, byte data[]){
//        boolean flag = false;
        String threadName = Thread.currentThread().getName();
        try {
            String path = ZkGlobalVar.ROOT_PATH_LOCKS+fnName;
            Stat stat = zooKeeper.exists(path, false);
            String createdPath = null;
            // zookeeper不存在 这个锁
            if(stat == null){
                //创建临时节点
                createdPath = zooKeeper.create(path, data,
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
                log.info("线程名称{}=======····创建成功：{}", threadName, createdPath);
            }
            //创建临时节点
            createdPath = zooKeeper.create(path+"/", data,
                                                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                                    CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("线程名称{}=======····创建成功：{}", threadName, createdPath);
            threadLocal.set(createdPath);
            //
            List<String> listNodes = zooKeeper.getChildren(path, false);
            if(listNodes.size() > 0){
                String firstNode = listNodes.get(0);
                System.out.println("firstNode====="+firstNode);
                if(firstNode.equals(threadLocal.get())){
                    // 没有等待锁
                    System.out.println("createdPath============="+threadLocal.get());
                    stat = zooKeeper.exists(path+"/"+threadLocal.get(), true);
                    log.info("线程名称{}=======没有等待锁直接执行：" , threadName, threadLocal.get());
                    return true;
                }else{

                    String watcherNode = listNodes.get(listNodes.size() - 1);
                    System.out.println("watcherNode============="+watcherNode);
                    stat = zooKeeper.exists(path+"/"+watcherNode, true);
                    log.info("线程名称{}=======等待锁直接执行：{}" , threadName, createdPath);
                    return false;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 释放锁
     * @param fnName
     */
    public void unLock(String fnName){
        try {
            zooKeeper.delete(fnName, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public CountDownLatch waitLock(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        if(threadLocal.get() == null){
            return null;
        }
        latch.put(threadLocal.get(), countDownLatch);
        return countDownLatch;
    }


}
