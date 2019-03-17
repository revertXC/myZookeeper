package com.revert.demo.subscription;

import com.revert.demo.subscription.watcher.DefaultWatcher;
import org.apache.zookeeper.*;

import java.io.IOException;

public class DoTest {

    public static ZooKeeper zooKeeper;
    public static final String nodeName = "/test";
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {


        String connectString = "127.0.0.1:2181";
        int sessionTimeout = 5000;
        Watcher watcher = new DefaultWatcher();
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
        String _1 = zooKeeper.create("/test/001", "hello World".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        String _2 = zooKeeper.create("/test/001/", "/test/001".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        //List<String> list = zooKeeper.getChildren("/test", false);
//        System.out.println(_1);
//        zooKeeper.delete(nodeName, -1);
        System.out.println(_1);
        System.out.println(_2);
        /** create(final String path, byte data[], List<ACL> acl, CreateMode createMode)
         *  path：路径
         *  data：值
         *  acl：节点操作权限(https://blog.csdn.net/idler_bm/article/details/51004140)
     *          CREATE(c): 创建权限，可以在在当前node下创建child node
         *      DELETE(d): 删除权限，可以删除当前的node
         *      READ(r): 读权限，可以获取当前node的数据，可以list当前node所有的child nodes
         *      WRITE(w): 写权限，可以向当前node写数据
         *      ADMIN(a): 管理权限，可以设置当前node的permission
         *
         *  createMode：节点生命周期
         *      PERSISTENT：持久化，无顺序的
         *      PERSISTENT_SEQUENTIAL：持久的且带顺序的
         *      EPHEMERAL：临时，依赖session，无顺序的
         *      EPHEMERAL_SEQUENTIAL：临时，有顺序的
         * */
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("========= 操作指令 ==============1:add，2、set，3、del，其他 query");
//            String instructStr = scanner.next();
//            switch (instructStr) {
//                case "1":
//                    String _1 = zooKeeper.create(nodeName, "hello World".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//                    /** 重新监听*/
//                    zooKeeper.getData(nodeName, true, null);
//                    System.out.println("**********创建成功：" + _1);
//                    break;
//                case "2":
//                    Stat statue = zooKeeper.setData(nodeName, "Hello World!!!!!".getBytes(), -1);
//                    System.out.println("**********修改数据成功");
//                    break;
//                case "3":
//                    zooKeeper.delete(nodeName, -1);
//                    System.out.println("**********删除成功");
//                    break;
//                default:
//                    System.out.println("**********"+new String(zooKeeper.getData(nodeName, true, null)));
//                    break;
//            }
//        }
    }

}
