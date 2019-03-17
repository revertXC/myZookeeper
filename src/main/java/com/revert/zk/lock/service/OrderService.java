package com.revert.zk.lock.service;

import com.revert.zk.lock.lockOperation.ZkLockUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * xiecong
 */
@Service
@Log4j2
public class OrderService {

    @Autowired(required = false)
    private ZkLockUtil zkLockUtil;

    public String genOrder(String sessionID){
        String ThreadName = Thread.currentThread().getName();
        String lockName = "/genOrder";
        log.info("锁名称：{}", lockName);
//        Thread.currentThread().lo
        if(zkLockUtil.tryLock(lockName, lockName.getBytes())){
            // 获取到锁
            log.info("线程名称：{}=======lockName:{} 拿到锁", ThreadName, lockName);
            execOper(sessionID);
            return "success";
        }
        try {
            log.info("线程名称：{}=======lockName：{}, 没有拿到锁, 即将进入等待状态",ThreadName, lockName);
            zkLockUtil.waitLock().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        }
        execOper(sessionID);
        return "success";
    }


    private void execOper(String sessionID){
        String ThreadName = Thread.currentThread().getName();
        log.info("{}=======sessionID: {}， 生产了订单", ThreadName, sessionID);
        try {
            Thread.sleep(1000L);
            zkLockUtil.unLock(ZkLockUtil.threadLocal.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
