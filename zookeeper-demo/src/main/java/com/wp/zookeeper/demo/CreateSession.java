package com.wp.zookeeper.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * <p>Title: CreateSession </p>
 * <p>Description: </p>
 * @Author : Wang Pan 
 * @Date: 2021-01-08 10:40
 */
public class CreateSession implements Watcher {
    // 这个类使一个线程等待，主要不让main方法结束
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.10.63:2181",5000,new CreateSession());
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        // 表示会话真正建立
        System.out.println("==============client connect to zookeeper=============");
    }

    /**
     * @param event
     * @Description: 当前类实现了Watcher接口，重写了process方法，该方法负责处理来自zookeeper服务端的watcher通知，
     * 在收到服务端发送过来的SyncConnecte事件之后，解除主程序在countDownLatch上的等待阻塞，至此会话创建完毕。
     * @author: Wang Pan
     * @Date: 2021/1/8 10:43
     * @return: void
     * @throws
     */
    @Override
    public void process(WatchedEvent event) {
        // 当连接创建了，服务端发送给客户端SyncConnected事件
        if(event.getState() == Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }
}
