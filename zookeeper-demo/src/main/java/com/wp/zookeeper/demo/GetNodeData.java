package com.wp.zookeeper.demo;

import org.apache.zookeeper.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p>Title: CreateNode </p>
 * <p>Description: 获取节点</p>
 * @Author : Wang Pan 
 * @Date: 2021-01-08 11:00
 */
public class GetNodeData implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.10.63:2181", 15000, new GetNodeData());
        System.out.println(zooKeeper.getState());
//        countDownLatch.await();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
       // 子节点列表发生变化时，服务器会发出NodeChildrenChange通知，
        // 但是不会把变化情况通知客户端，需要客户端自行获取，且通知是一次性的，需要反复注册监听
        if(event.getType()==Event.EventType.NodeChildrenChanged){
            // 再次获取节点内容
            try {
                List<String> children = zooKeeper.getChildren(event.getPath(), true);
                System.out.println(children);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            getNodeData();
            getChildrens();
            updateZNode();
        }
    }

    //获取某个节点内容
    private void getNodeData(){
        /**path:获取数据路径，watch：是否开启监听，stat：节点状态信息值为null：表示获取最新版本的数据*/
        try {
            byte[] data = zooKeeper.getData("/lg_persistent", false, null);
            System.out.println(new String(data));
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getChildrens(){
        try {
            List<String> children = zooKeeper.getChildren("/lg_persistent", true);
            System.out.println(children);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateZNode(){
        String s = null;
        try {
            s = zooKeeper.create("/lg_persistent/test", "子节点".getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("创建的子节点："+s);
    }
}
