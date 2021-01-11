package org.apache.zookeeper.wp.test;

import org.apache.zookeeper.*;
import org.junit.Assert;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * <p>Title: CreateNode </p>
 * <p>Description: 创建节点</p>
 * @Author : Wang Pan 
 * @Date: 2021-01-08 11:00
 */
public class CreateNode implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.10.63:2181", 15000, new CreateNode());
        System.out.println(zooKeeper.getState());
//        countDownLatch.await();
        Thread.sleep(Integer.MAX_VALUE);

    }

    @Override
    public void process(WatchedEvent event) {
        // 当连接创建了，服务端发送给客户端SyncConnected事件
        if (event.getState() == Event.KeeperState.SyncConnected) {
//            countDownLatch.countDown();
            // 表示会话真正建立
            System.out.println("==============client connect to zookeeper=============");
        }
        try {
//            System.out.println(zooKeeper.getState());
            createNodeSync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNodeSync() throws Exception {
        // 权限模式 ZooDefs.Ids.OPEN_ACL_UNSAFE
        // : public final ArrayList<ACL> OPEN_ACL_UNSAFE = new ArrayList<ACL>(
        //                Collections.singletonList(new ACL(Perms.ALL, ANYONE_ID_UNSAFE)));
        // 其中ANYONE_ID_UNSAFE：Id ANYONE_ID_UNSAFE = new Id("world", "anyone");
        // Id构造函数参数:  String scheme 权限模式,
        // String id 授权对象，ip: 192.168.1.1/24;Digest:username:BASE64(SHA-1(username:password))
        // World 只有一个ID：anyone
        // Perms.ALL:代表权限
//        String s = zooKeeper.create("/lg_persistent", "持久节点内容".getBytes(Charset.forName("utf-8")), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println("创建的持久节点是："+s);
        System.out.println("===========开始创建节点==========");
//        System.out.println(zooKeeper.getState());
        String node_PERSISTENT = zooKeeper.create("/lg_persistent","持久节点内容".getBytes(Charset.forName("utf-8")),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        String node_PERSISTENT_SEQUENTIAL = zooKeeper.create("/lg_persistent_sequential", "持久顺序节点内容".getBytes(Charset.forName("utf-8")),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        String node_EPERSISTENT = zooKeeper.create("/lg_ephemeral", "临时节点内容".getBytes(Charset.forName("utf-8")), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("持久节点内容:" + node_PERSISTENT);
        System.out.println("持久顺序节点内容:" + node_PERSISTENT_SEQUENTIAL);
        System.out.println("临时节点内容：" + node_EPERSISTENT);
    }
}
