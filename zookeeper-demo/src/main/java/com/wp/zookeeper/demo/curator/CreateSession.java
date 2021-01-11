package com.wp.zookeeper.demo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * <p>Title: CreateSession </p>
 * <p>Description: </p>
 * @Author : Wang Pan 
 * @Date: 2021-01-08 15:21
 */
public class CreateSession {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();

        CuratorFramework client1 = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(3000) // 连接超时时间
                .retryPolicy(retryPolicy) // 重试策略
                .namespace("base") // 独立命名空间 /base
                .build();
        client1.start();
        System.out.println("Zookeeper session2 established. ");
        String path = "/lg-curator/c1";
        //创建节点
        client1.create().forPath(path);
        client1.create().forPath(path,"woshi..".getBytes("utf-8"));
        client1.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
        //删除节点
        client1.delete().forPath(path);
        client1.delete().deletingChildrenIfNeeded().forPath(path);

        //获取数据
        byte[] bytes = client1.getData().forPath(path);
        //包含状态查询
        Stat stat = new Stat();
        client1.getData().storingStatIn(stat);
    }
}
