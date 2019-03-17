package com.revert.curator.lock.controller;

import com.revert.curator.lock.service.CuratorOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * xiecong
 */
@RestController
@RequestMapping("/api/v1/curator")
public class CuratorOrderController {

    @Autowired
    private CuratorOrderService curatorOrderService;

    private CountDownLatch countDownLatch = new CountDownLatch(2);

    @RequestMapping(method = RequestMethod.GET)
    public String test() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"进入Controller层····");
        countDownLatch.await();
        return curatorOrderService.buyGoods();
    }

    @RequestMapping(value = "222")
    public String d(@RequestParam(value = "id", required = false) String id){
        if(id == null){
            countDownLatch.countDown();
        }else{
            countDownLatch = new CountDownLatch(2);
        }
        return countDownLatch.getCount()+"";
    }

}
