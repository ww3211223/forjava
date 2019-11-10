package com.nonono.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        testZookeeper();
        testZookeeperEnd();
        System.out.println("------zookeeper test end.------");
    }

    public static void testZookeeper() {
        try {
            BaseZookeeper zookeeper = new BaseZookeeper();
            zookeeper.connectZookeeper("192.168.36.78:2181");
            zookeeper.createNode("/nonono", "first test node.", CreateMode.PERSISTENT);

            //注册子节点变更事件
            zookeeper.getZooKeeper().getChildren("/nonono", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("/nonono path is changed. type:" + watchedEvent.getType());
                }
            }, null);

            //创建节点，触发子节点变更事件
            zookeeper.createNode("/nonono/first", "first test node.", CreateMode.EPHEMERAL);
            Thread.sleep(1000);
            zookeeper.createNode("/nonono/second", "second test node.", CreateMode.EPHEMERAL);
            Thread.sleep(1000);
            zookeeper.createNode("/nonono/third", "third test node.", CreateMode.EPHEMERAL);

            //输出data
            System.out.println("data:" + zookeeper.getData("/nonono/first"));

            //注册节点变更事件
            zookeeper.getZooKeeper().getData("/nonono/first", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("/nonono first data is changed. type:" + watchedEvent.getType());
                }
            }, null);

            Thread.sleep(3000);
            //更改节点内容，触发节点变更事件
            zookeeper.setData("/nonono/first", "first test node2.");
            System.out.println("new data:" + zookeeper.getData("/nonono/first"));
            Thread.sleep(3000);
            zookeeper.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testZookeeperEnd() {
        try {
            BaseZookeeper zookeeper = new BaseZookeeper();
            zookeeper.connectZookeeper("192.168.36.78:2181");

            zookeeper.deleteNode("/nonono");
            System.out.println("/ path childrens:" + zookeeper.getChildren("/"));
            zookeeper.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
