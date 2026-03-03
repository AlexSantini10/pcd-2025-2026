package pcd.lab03.cs_raw;

public class MyWorkerA extends Worker {
	
	private Object lock;
	
	public MyWorkerA(String name, Object lock){
		super(name);
		this.lock = lock;
	}
	
	public void run(){
		while (true){
		  a1();			  
		  /* critical section */
		  synchronized(lock){
			  a2();	
			  a3();	
		  }
		}
	}
	
	protected void a1(){
		println("a1");
		wasteRandomTime(100,500);	
	}
	
	protected void a2(){
		println("a2");
		wasteRandomTime(300,700);	
	}
	protected void a3(){
		println("a3");
		wasteRandomTime(300,700);	
	}
}

