package pcd.lab03.check_act;

public class WorkerA extends Thread {

    private final BoundedCounter counter;
    private final int ntimes;

    public WorkerA(BoundedCounter c, int ntimes) {
        this.counter = c;
        this.ntimes = ntimes;
    }

    public void run() {
        try {
            for (int i = 0; i < ntimes; i++) {
                counter.tryDec();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}