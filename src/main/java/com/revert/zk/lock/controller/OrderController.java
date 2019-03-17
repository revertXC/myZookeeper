package com.revert.zk.lock.controller;

import com.revert.zk.lock.service.OrderService;
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
@RequestMapping("/api/v1/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    CountDownLatch countDownLatch = new CountDownLatch(2);

    @RequestMapping(method = RequestMethod.GET)
    public String order(@RequestParam(value = "id") String id) throws InterruptedException {
        countDownLatch.await();
        orderService.genOrder(id);
        return id+"success";
    }

    @RequestMapping("222")
    public String d(){
        countDownLatch.countDown();
        System.out.println(countDownLatch.getCount());
        return "success";
    }


}
