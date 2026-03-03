package pcd.lab03.check_act;

public class BoundedCounter {

	private int cont;
	private int min, max;
	
	public BoundedCounter(int min, int max){
		this.cont = this.min = min;
		this.max = max;
	}
	
	public synchronized void inc() throws OverflowException {
			if (cont + 1 > max){
				throw new OverflowException();
			}
			cont++;
	}

	public  synchronized void dec() throws UnderflowException {
			if (cont - 1 < min){
				throw new UnderflowException();
			}
			cont--;
	}
	
	public  int getValue(){
		synchronized (this){
			return cont;
		}
	}
}
