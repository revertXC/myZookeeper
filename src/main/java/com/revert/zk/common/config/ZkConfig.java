package com.revert.zk.common.config;

import com.revert.zk.common.watcher.DefaultWatcher;
import com.revert.zk.common.zkNode.ZkGlobalVar;
import lombok.Getter;
import lombok.Setter;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

//@Configuration
//@AutoConfigureAfter(DefaultWatcher.class)
//@ConfigurationProperties(prefix = "zk")
public class ZkConfig {

    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private int sessionTimeout;

    @Autowired
    private DefaultWatcher defaultWatcher;

    @Bean("zooKeeper")
    public ZooKeeper initZkClient(){

        ZooKeeper zooKeeper = null;
        try{
            zooKeeper = new ZooKeeper(url, sessionTimeout, defaultWatcher);
            initRootPath(zooKeeper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return zooKeeper;
    }

    /**
     * 初始化
     * @param zooKeeper
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void initRootPath(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
        /** 锁根节点*/
        Stat stat = zooKeeper.exists(ZkGlobalVar.ROOT_PATH_LOCKS, false);
        if(stat == null){
            // 创建根节点
            String path = zooKeeper.create(ZkGlobalVar.ROOT_PATH_LOCKS, "Hello World".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("创建根节点："+path);
        }
    }

}
