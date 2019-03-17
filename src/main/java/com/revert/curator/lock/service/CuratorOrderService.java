package com.revert.curator.lock.service;

import com.revert.curator.lock.common.utils.CuratorBeanUtils;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * xiecong
 */
@Service
public class CuratorOrderService {
    @Autowired
    private CuratorBeanUtils curatorBeanUtils;


    public String buyGoods(){
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+"线程准备进行订单···");
        InterProcessMutex interProcessMutex = curatorBeanUtils.getCacheInterProcessMutex("/orderLocks");
        try {
            System.out.println(threadName+" 开始上锁");
            /** 上锁*/
            interProcessMutex.acquire();
            System.out.println(threadName+"获取到锁，生成订单");
            /** 业务逻辑*/
            Thread.sleep(500L);
            /** 释放*/
            interProcessMutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
