package com.wp.zookeeper.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * <p>Title: CreateNode </p>
 * <p>Description: 创建节点</p>
 * @Author : Wang Pan 
 * @Date: 2021-01-08 11:00
 */
public class UpdateNode implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.10.63:2181", 15000, new UpdateNode());
        System.out.println(zooKeeper.getState());
//        countDownLatch.await();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        // 当连接创建了，服务端发送给客户端SyncConnected事件
        if (event.getState() == Event.KeeperState.SyncConnected) {
            // 表示会话真正建立
            System.out.println("==============client connect to zookeeper=============");
        }
        try {
            updateNodeSync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateNodeSync() throws Exception {

        byte[] data = zooKeeper.getData("/lg_persistent", false, null);
        System.out.println("修改前的值："+new String(data));
        //修改 stat：状态信息对象 -1：最新版本
        Stat stat = zooKeeper.setData("/lg_persistent", "客户端修改内容".getBytes("utf-8"), -1);
        byte[] data1 = zooKeeper.getData("/lg_persistent", false, null);
        System.out.println("修改后的值："+new String(data1));
    }

}
