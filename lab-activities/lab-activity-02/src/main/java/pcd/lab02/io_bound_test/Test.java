package pcd.lab02.io_bound_test;

import java.util.*;


public class Test {
	
	public static void main(String[] args) {

		int nWorkers = 1; // Runtime.getRuntime().availableProcessors();
		
		long totalAmountOfCPUJob = 400_000_000;
		long totalAmountOfIOJob = 20000; 
		
		int seed = 100;
		
		double howMuchCPUJob = 1.0/nWorkers;
		double howMuchIOJob = 1.0/nWorkers;

		var workers = new ArrayList<Worker>();
		for (int i = 0; i < nWorkers; i++) {
			workers.add(new Worker(howMuchCPUJob, howMuchIOJob, seed, totalAmountOfCPUJob, totalAmountOfIOJob));
			seed++;
		}

		for (var w: workers) {
			w.start();
		}
 
		log("Number of workers: " + nWorkers + " - Amount of IO job: " + howMuchIOJob);
		log("started");			
		var t0 = System.currentTimeMillis();

		for (var w: workers) {
			try {
				w.join();
			} catch (Exception ex) {}
		}
		
		var t1 = System.currentTimeMillis();
		log("Done. Time elapsed: " + (t1 - t0) + "ms");
		
		
	}

	private static void log(String msg) {
		System.out.println(msg);
	}
}
