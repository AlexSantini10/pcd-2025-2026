package pcd.lab03.cs_raw;

public class MyWorkerB extends Worker {
	
	private Object lock;
	
	public MyWorkerB(String name, Object lock){
		super(name);
		this.lock = lock;
	}

	public void run(){
		while (true){
			/* critical section */
			synchronized(lock){
			  b1();	
			  b2();
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
