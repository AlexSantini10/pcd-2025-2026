package pcd.lab02.bballs;

public class BasicAgent extends Thread {
	
	/* 
	 * short-cut for cooperative termination,+
	 * Better approach: using a shared flag object...
	 */
	private volatile boolean stopped;
	
	protected BasicAgent(String name) {
		super(name);
		this.stopped = false;
	}

	protected boolean hasBeenStopped() {
		return this.stopped;
	}
	
	public void notifyStopped() {
		this.stopped = true;
	}
	
	protected void log(String msg) {
		synchronized (System.out) {
			System.out.println("[ " + this.getName()+ " ][ " + System.currentTimeMillis() + " ] " + msg); 
		}
	}

}
