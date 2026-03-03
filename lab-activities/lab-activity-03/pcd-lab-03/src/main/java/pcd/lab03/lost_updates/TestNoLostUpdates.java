package pcd.lab03.lost_updates;

public class TestNoLostUpdates {

	public static void main(String[] args) throws Exception {
		
		long ntimes = 10000; // 100_000_000l; // try with different values: 100, 200, 1000, 5000, ...
		// int ntimes = 100000; // try with different values: 100, 200, 1000, 5000, ...
		
		if (args.length > 0) {
			ntimes = Integer.parseInt(args[0]);
		}
		
		UnsafeCounter c = new UnsafeCounter(0);
		WorkerOK w1 = new WorkerOK("Worker-A", c, ntimes);
		WorkerOK w2 = new WorkerOK("Worker-B", c, ntimes);

		Cron cron = new Cron();
		cron.start();
		
		w1.start();
		w2.start();

		w1.join();
		w2.join();
		
		cron.stop();
		
		System.out.println("Counter final value: " + c.getValue() + " in " + cron.getTime()+"ms.");
	}
}
