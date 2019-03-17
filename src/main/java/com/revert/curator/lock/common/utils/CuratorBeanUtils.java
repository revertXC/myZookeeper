package com.revert.curator.lock.common.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * xiecong
 */
@Component
public class CuratorBeanUtils {

    @Autowired
    private CuratorFramework curatorFramework;

    public static ConcurrentMap<String, InterProcessMutex> mutexMap = new ConcurrentHashMap<>();

    public InterProcessMutex getCacheInterProcessMutex(String path){
        InterProcessMutex interProcessMutex = mutexMap.get(path);
        if(interProcessMutex == null){
            synchronized ("CuratorBeanUtils-1"){
                interProcessMutex = new InterProcessMutex(curatorFramework, path);
                mutexMap.put(path, interProcessMutex);
            }
        }
        return interProcessMutex;
    }


}
