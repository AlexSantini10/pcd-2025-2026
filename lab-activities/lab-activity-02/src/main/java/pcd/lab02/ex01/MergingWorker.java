package pcd.lab02.ex01;

import java.util.List;

public class MergingWorker extends AbstractWorker {
	
	private int[] array;
	private List<SortingWorker> workers;
	
	public MergingWorker(String name, int[] array, List<SortingWorker> workers){
		super(name);
		this.array = array;
		this.workers = workers;
	}
	
	public void run() {
		int nParts = workers.size();
		log("started - merging " + nParts +" parts");
		log("waiting for subparts to be sorted...");
		try {
			for (var w1: workers) {
				w1.join();
			}
			log("subparts sorted, going to merge...");
			
			long t0 = System.currentTimeMillis();
			int[] merged = this.merge(array, nParts);
			for (int i = 0; i < merged.length; i++) {
				array[i] = merged[i];
			}
			long t1 = System.currentTimeMillis();
			log("completed -- " + (t1 - t0) + " ms for merging.");
		} catch(InterruptedException ex) {
			log("exception.");
		}
	}
	
	private int[] merge(int[] v, int nParts) {
		var vnew = new int[v.length];

		int partSize = v.length/nParts;
		int from = 0; 

		var indexes = new int[nParts];
		var max = new int[nParts];
		for (int i = 0; i < indexes.length - 1; i++) {
			indexes[i] = from;
			max[i] = from + partSize;
			from = max[i];
		}
		indexes[indexes.length - 1] = from;
		max[indexes.length - 1] = v.length;

		int i3 = 0;
		boolean allFinished = false;
		while (!allFinished) {
			
			int min = Integer.MAX_VALUE;
			int index = -1;
			for (int i = 0; i < indexes.length; i++) {
				if (indexes[i] < max[i]) {
					if (v[indexes[i]] < min) {
						index = i;
						min = v[indexes[i]];
					}
				}
			}
			
			if (index != -1) {
				vnew[i3] = v[indexes[index]];
				indexes[index]++;
				i3++;
			} else {
				allFinished = true;
			}
		}
		return vnew;
	}

}
