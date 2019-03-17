package com.revert.zk.lock.demo.server1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("api/v1/server1")
public class Server1Controller {

    /** 共享值*/
    String condition = "false";
    /** 模拟并发*/
    CountDownLatch countDownLatch = new CountDownLatch(5);

    ConcurrentMap m = new ConcurrentHashMap();
    @RequestMapping(method = RequestMethod.GET)
    public String test1() throws InterruptedException {
        /** 全部线程等待在这里*/
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + "等待·····");
        countDownLatch.await();
        synchronized (Server1Controller.class){
            if(condition.equals("false")){
                condition = "true";
                /** 做操作。。。。。。*/
                Thread.sleep(500L);
                condition = "false";
                System.out.println(threadName + "执行完成");
            }else{
                System.err.println(threadName + " Error!!!");
            }
        }
        System.out.println();
        return "success";
    }

    @RequestMapping(value = "1", method = RequestMethod.GET)
    public String test11() throws InterruptedException {
        countDownLatch.countDown();
        return "success";
    }


}
