package pcd.lab02.ex01;

import java.util.Arrays;

public class SortingWorker extends AbstractWorker {
	
	private int[] array;
	private int from, to;
	
	public SortingWorker(String name, int[] array, int from, int to){
		super(name);
		this.array = array;
		this.from = from;
		this.to = to;
		
	}
	
	public void run() {
		log("started - sorting from " + from + " " + to);
		Arrays.sort(array, from, to + 1);
		log("completed.");
	}
}
