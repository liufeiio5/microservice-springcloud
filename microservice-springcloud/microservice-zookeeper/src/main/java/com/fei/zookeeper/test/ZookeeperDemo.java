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

public class ZookeeperDemo
{
	/**
	 * 集群连接地址
	 */
	private static final String	CONNECT_ADDR    = "127.0.0.1:2181";
	/**
	 * session超时时间
	 */
	private static final int	SESSION_OUTTIME = 2000;
	/**
	 * 信号量,阻塞程序执行,用户等待zookeeper连接成功,发送成功信号，
	 */
	private static final CountDownLatch	countDownLatch  = new CountDownLatch(1);

	public static void main( String[] args) throws IOException, InterruptedException, KeeperException
	{
		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher()
		{

			public void process( WatchedEvent event)
			{
				// 获取时间的状态
				KeeperState keeperState = event.getState();
				EventType tventType = event.getType();
				// 如果是建立连接
				if (KeeperState.SyncConnected == keeperState)
				{
					if (EventType.None == tventType)
					{
						// 如果建立连接成功,则发送信号量,让后阻塞程序向下执行
						countDownLatch.countDown();
						System.out.println("zk 建立连接");
					}
				}
			}

		});
		// 进行阻塞
		countDownLatch.await();
		String result = zk.create("/itmayiedu_temp", "yushengjun".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		System.out.println("result:" + result);
		try
		{
			Thread.sleep(10000);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		zk.close();
	}

}
