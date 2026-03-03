package pcd.lab03.lost_updates;


public class WorkerOK extends Thread {
	
	private UnsafeCounter counter;
	private long ntimes;
	
	public WorkerOK(String name, UnsafeCounter counter, long ntimes){
		super(name);
		this.counter = counter;
		this.ntimes = ntimes;
	}
	
	public void run(){
		log("started");
		for (long i = 0; i < ntimes; i++){
			synchronized (counter) {
				counter.inc();
			}
		}
		log("completed");
	}
	
	private void log(String msg) {
		System.out.println("[ " + this.getName() + "] " + msg);
	}
	
}
