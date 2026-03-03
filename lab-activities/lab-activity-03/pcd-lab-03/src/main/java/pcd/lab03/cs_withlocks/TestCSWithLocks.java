package pcd.lab03.cs_withlocks;

import java.util.concurrent.locks.*;

public class TestCSWithLocks {
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		new MyWorkerB("MyAgent-01", lock).start();
		new MyWorkerA("MyAgent-02", lock).start();		
	}
}
