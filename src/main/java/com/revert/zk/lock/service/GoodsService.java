package com.revert.zk.lock.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * xiecong
 * 商品
 */
@Service
@Log4j2
public class GoodsService {


    /**
     *
     * @param params
     * @return
     */
    public List<Map<String, String>> queryByParams(String params){
        List<Map<String, String>> result = new LinkedList<>();
        for(int i=0; i<10 ;i++){
            Map<String, String> map = new HashMap<>();
            map.put("goodsName","商品名称"+i);
            map.put("size",""+i);
            map.put("test","test"+i);
            map.put("len","10");
            result.add(map);
        }
        return result;
    }



}
