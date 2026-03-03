package pcd.lab03.cs_withlocks;

import java.util.concurrent.locks.Lock;

public class MyWorkerB extends Worker {
	
	private Lock lock;
	
	public MyWorkerB(String name, Lock lock){
		super(name);
		this.lock = lock;
	}

	public void run(){
		while (true){
		  try {
			  lock.lockInterruptibly();
			  b1();	
			  b2();
		  } catch (InterruptedException ex) {
		  } finally {
			  lock.unlock();
		  }
		  b3();
		}
	}
	
	protected void b1(){
		println("b1");
		wasteRandomTime(0,1000);	
	}
	
	protected void b2(){
		println("b2");
		wasteRandomTime(100,200);	
	}
	protected void b3(){
		println("b3");
		wasteRandomTime(1000,2000);	
	}
}
