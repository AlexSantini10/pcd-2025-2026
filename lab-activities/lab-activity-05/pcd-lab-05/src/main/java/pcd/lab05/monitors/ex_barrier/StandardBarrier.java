package pcd.lab05.monitors.ex_barrier;

public class StandardBarrier implements Barrier {

    private final int nParticipants;
    private int count;
    private int generation;

    public StandardBarrier(int nParticipants) {
        if (nParticipants <= 0) {
            throw new IllegalArgumentException("nParticipants must be > 0");
        }
        this.nParticipants = nParticipants;
        this.count = 0;
        this.generation = 0;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        int myGeneration = generation;

        count++;

        if (count == nParticipants) {
            count = 0;
            generation++;
            notifyAll();
            return;
        }

        while (myGeneration == generation) {
            wait();
        }
    }
}