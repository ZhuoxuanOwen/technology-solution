package com.galax.demo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperSimple implements Watcher{
	
	private static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) {
		
		try {
			ZooKeeper zooKeeper = new ZooKeeper(
					"127.0.0.1:2181",
					 5000, new ZookeeperSimple());
		
		
			System.out.println(zooKeeper.getState());
			countDownLatch.await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void process(WatchedEvent arg0) {
		
		countDownLatch.countDown();
		
	}

}
