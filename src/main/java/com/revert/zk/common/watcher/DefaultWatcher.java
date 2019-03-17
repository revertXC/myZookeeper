package com.revert.zk.common.watcher;

import com.revert.zk.common.zkNode.ZkGlobalVar;
import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DefaultWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        int s = event.getState().getIntValue();
        String p = event.getPath();
        int t = event.getType().getIntValue();
        log.info("监听节点：{}，操作状态：{}，操作类型：{}",p, s, t);
        if (s == Event.KeeperState.SyncConnected.getIntValue()) {

            if(t > -1){
                if(t == Event.EventType.NodeDeleted.getIntValue()){
                    log.info("节点( {} )进行删除操作",p);
                    nodeDeleted(p);
                }
            }else{
                log.info("zk连接成功");
            }
        }else if(s == Event.KeeperState.Disconnected.getIntValue()){
            log.info("zk断开连接");
        }
    }


    private void nodeDeleted(String p){
        if(p.indexOf(ZkGlobalVar.ROOT_PATH_LOCKS) == 0){
            System.out.println("11111111111111");
        }
    }

}
