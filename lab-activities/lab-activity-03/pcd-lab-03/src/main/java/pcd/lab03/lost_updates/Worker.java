package pcd.lab03.lost_updates;


public class Worker extends Thread {
	
	private SafeCounter counter;
	private long ntimes;
	
	public Worker(String name, SafeCounter counter, long ntimes){
		super(name);
		this.counter = counter;
		this.ntimes = ntimes;
	}
	
	public void run(){
		log("started");
		for (long i = 0; i < ntimes; i++){
			counter.inc();
		}
		log("completed");
	}
	
	private void log(String msg) {
		System.out.println("[ " + this.getName() + "] " + msg);
	}
	
}
