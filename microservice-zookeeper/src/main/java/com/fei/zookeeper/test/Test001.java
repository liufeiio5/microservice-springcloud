package com.fei.zookeeper.test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Id;

public class Test001 {

	// 连接地址
	private static final String ADDRES = "127.0.0.1:2181";
	// session 会话
	private static final int SESSION_OUTTIME = 2000;
	// 信号量,阻塞程序执行,用户等待zookeeper连接成功,发送成功信号，
	private static final CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ZooKeeper zk = new ZooKeeper(ADDRES, SESSION_OUTTIME, new Watcher() {

			public void process(WatchedEvent event) {
				// 获取事件状态
				KeeperState keeperState = event.getState();
				// 获取事件类型
				EventType eventType = event.getType();
				if (KeeperState.SyncConnected == keeperState) {
					if (EventType.None == eventType) {

						System.out.println("zk 启动连接...");
						countDownLatch.countDown();
					}

				}
			}
		});
		// 进行阻塞
		countDownLatch.await();
		String result = zk.create("/itmayeidu_Lasting", "Lasting".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		System.out.println(result);
		zk.close();
	}

}
