package pcd.lab03.cs_raw;

import java.util.concurrent.locks.Lock;

public class TestCSWithRawSynchBlocks {

	public static void main(String[] args) {

		var lock = new Object();

		new MyWorkerB("MyWorker-01", lock).start();
		new MyWorkerA("MyWorker-02", lock).start();		
	
	}

}
