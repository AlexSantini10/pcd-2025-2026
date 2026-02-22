package pcd.lab02.io_bound_test;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * Active component doing a (simulated) job 
 * mixing CPU and I/O
 * 
 * 
 */
public class Worker extends Thread {
	
	private double howMuchIOJob;
	private double howMuchCPUJob;
	private Random gen;
	private long totalIOtime;
	private long totalAmountOfCPUJob;
	private long totalAmountOfIOJob;

	public Worker(double howMuchCPUJob, double howMuchIOJob, int seed, long totalAmountOfCPUJob, long totalAmountOfIOJob) {
		this.howMuchIOJob = howMuchIOJob;
		this.howMuchCPUJob = howMuchCPUJob;
		this.totalAmountOfCPUJob = totalAmountOfCPUJob;
		this.totalAmountOfIOJob = totalAmountOfIOJob;
		gen = new Random(seed);
	}
	
	public void run() {		
		log("started - CPU job todo: " + Math.rint(howMuchCPUJob*100) +"% - IO job todo: " + Math.rint(howMuchIOJob*100) );
		
		totalIOtime = 0;
		var t0 = System.currentTimeMillis();
		
		/* breaking the job in parts, to mix CPU/IO randomly, minimising syncs */
		
		int nPieces = 5;
		double pieceOfCPUJob = howMuchCPUJob*(1.0/nPieces);
		double pieceOfIOJob = howMuchIOJob*(1.0/nPieces);
		int numPiecesOfCPUJobDone = 0; 
		int numPiecesOfIOJobDone = 0; 
	
		if (howMuchIOJob > 0) {
			
			/* while there is still work to do...*/
			while (numPiecesOfCPUJobDone < nPieces || numPiecesOfIOJobDone < nPieces) {
				
				/* 
				 * if there is still both CPU and IO work todo, 
				 * choose at random what to do 
				 * 
				 */
				if (numPiecesOfCPUJobDone < nPieces && numPiecesOfIOJobDone < nPieces) {
					var who = gen.nextBoolean();
					if (who) {
						doSomeCPUjob(pieceOfCPUJob);		
						numPiecesOfCPUJobDone++;
					} else {
						doSomeIOjob(pieceOfIOJob);
						numPiecesOfIOJobDone++;
					}
				} else if (numPiecesOfCPUJobDone < nPieces) {
					/* only CPU jobs left */
					doSomeCPUjob(pieceOfCPUJob);		
					numPiecesOfCPUJobDone++;
				} else {
					/* only IO jobs left */
					doSomeIOjob(pieceOfIOJob);				
					numPiecesOfIOJobDone++;
				}
			}
		} else {
			doSomeCPUjob(howMuchCPUJob);		
		}
		var t1 = System.currentTimeMillis();
		log("done - job duration: " + (t1-t0) + "ms - For IO: " + totalIOtime + "ms." );
	}
	
	private void doSomeCPUjob(double factor) {
		log("doing some CPU job (" + Math.rint(factor*100) +"%)");
		final int vectorSize = (int) (totalAmountOfCPUJob * factor);		
		var v = new int[vectorSize];
		for (int i = 0; i < v.length; i++) {
			v[i] = gen.nextInt();
		}			
		Arrays.sort(v, 0, v.length);
	}

	private void doSomeIOjob(double factor) {
		var ioJobTime = (long) Math.rint(totalAmountOfIOJob*factor);
		log("doing some IO job (" + Math.rint(factor*100) +"% = " + ioJobTime + "ms)");
		try {
			sleep(ioJobTime);
		} catch (Exception ex) {}
		totalIOtime += ioJobTime;
	}

	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() +  " ][ " + this.getName() + " ] " + msg); 
	}
}
