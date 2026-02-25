package pcd.lab02.ex01;

import java.util.*;

public class ConcurrentSort {

	static final int VECTOR_SIZE = 400_000_000;
	static final boolean isDebugging = false;
	
	public static void main(String[] args) {
	
		
		int nSortingWorkers = 20; // best number in theory: Runtime.getRuntime().availableProcessors() + 1
		
		log("Num elements to sort: " + VECTOR_SIZE);
		log("Num sorting workers: " + nSortingWorkers);
	
		log("Generating array.");
		int[] v = genArray(VECTOR_SIZE);
		
		log("Array generated.");
		if (isDebugging) {
			dumpArray(v);
		}
		
		long t0 = System.currentTimeMillis();	
		
		int jobSize = v.length/nSortingWorkers;
		int from = 0; 
		int to = jobSize - 1;
		
		List<SortingWorker> workers = new ArrayList<SortingWorker>();
		for (int i = 0; i < nSortingWorkers - 1; i++) {
			var w = new SortingWorker("worker-"+(i+1), v, from, to);
			w.start();
			workers.add(w);
			from = to + 1;
			to += jobSize;
		}
		
		var w = new SortingWorker("worker-" + nSortingWorkers, v, from, v.length - 1);
		w.start();
		workers.add(w);

		MergingWorker m = new MergingWorker("merger", v, workers);
		m.start();
		try {
			m.join();
			long t1 = System.currentTimeMillis();
			log("Done. Time elapsed: " + (t1 - t0) + " ms");
			
			if (isDebugging) {
				dumpArray(v);
				assert (isSorted(v));
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private static int[] genArray(int n) {
		int numElem = n;
		if (isDebugging) {
			numElem = 100;
			Random gen = new Random(System.currentTimeMillis());
			int v[] = new int[numElem];
			for (int i = 0; i < v.length; i++) {
				v[i] = gen.nextInt() % 100;
			}
			return v;
		} else {
			Random gen = new Random(System.currentTimeMillis());
			int v[] = new int[numElem];
			for (int i = 0; i < v.length; i++) {
				v[i] = gen.nextInt();
			}
			return v;
		}
	}

	private static boolean isSorted(int[] v) {
		if (v.length == 0) {
			return true;
		} else {
			int curr = v[0];
			for (int i = 1; i < v.length; i++) {
				if (curr > v[i]) {
					return false;
				} else {
					curr = v[i];
				}
			}
			return true;
		}
	}
	
	private static void dumpArray(int[] v) {
		for (int l:  v) {
			System.out.print(l + " ");
		}
		System.out.println();
	}

	private static void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() +  " ][ " + Thread.currentThread().getName() + " ] " + msg); 
	}
}
