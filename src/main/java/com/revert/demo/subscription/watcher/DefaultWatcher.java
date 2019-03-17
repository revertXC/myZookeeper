package com.revert.demo.subscription.watcher;

import com.revert.demo.subscription.DoTest;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class DefaultWatcher implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
        int s = watchedEvent.getState().getIntValue();
        String p = watchedEvent.getPath();
        int t = watchedEvent.getType().getIntValue();
        if (s == Event.KeeperState.SyncConnected.getIntValue()) {
            System.out.println("getPath："+p+"   State："+s+"    type："+t);
            if(t > -1){
                try {
                    DoTest.zooKeeper.getData("/test", true, null);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接······");
            }
        }else{
            System.out.println("======= 事件监听 ========");
            System.out.println("getPath："+p+"   State："+s+"    type："+t);
        }
    }

}
