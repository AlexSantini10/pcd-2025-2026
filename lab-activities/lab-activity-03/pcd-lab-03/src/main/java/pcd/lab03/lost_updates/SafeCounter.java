package pcd.lab03.lost_updates;

/**
 * 
 * This class is Thread-safe
 * 
 */
public class SafeCounter {

	private int cont;
	
	public SafeCounter(int base){
		this.cont = base;
	}
	
	public synchronized void inc(){
		cont++;
	}
	
	public synchronized int getValue(){
		return cont;
	}
}
