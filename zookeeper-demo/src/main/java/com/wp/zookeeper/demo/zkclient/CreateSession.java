package com.wp.zookeeper.demo.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * <p>Title: CreateSession </p>
 * <p>Description: </p>
 * @Author : Wang Pan 
 * @Date: 2021-01-08 14:40
 */
public class CreateSession {

    //创建一个zkclient实例来进行连接
    // 注意：zkclient通过对zookeeperAPI内部包装，将这个异步创建会话的过程同步了
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("192.168.10.63:2181");
        System.out.println("ZooKeeper session established.");
    }

    private void createNode(){
        ZkClient zkClient = new ZkClient("192.168.10.63:2181");
        String node_persistent = zkClient.create("/zk_persistent", "zkclient 创建的持久节点", CreateMode.PERSISTENT);
        //可以递归创建节点
        zkClient.create("/zk_persistent/test", "zkclient 创建的持久节点", CreateMode.PERSISTENT);
        //true可以递归创建节点
        zkClient.createPersistent("/zk_persistent/test", true);
    }

    private void deleteNode(){
        String path = "/lg-zkClient/lg-c1";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        // 可以递归删除节点
        zkClient.deleteRecursive(path);
        System.out.println("success delete znode.");
    }
}
