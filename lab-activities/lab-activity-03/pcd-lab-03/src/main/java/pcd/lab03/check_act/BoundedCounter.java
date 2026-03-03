package pcd.lab03.check_act;

public class BoundedCounter {

    private int cont;
    private final int min, max;

    public BoundedCounter(int min, int max) {
        this.cont = this.min = min;
        this.max = max;
    }

    public synchronized void inc() throws OverflowException {
        if (cont + 1 > max) {
            throw new OverflowException();
        }
        cont++;
    }

    public synchronized void dec() throws UnderflowException {
        if (cont - 1 < min) {
            throw new UnderflowException();
        }
        cont--;
    }

    public synchronized int getValue() {
        return cont;
    }

    // Check+act atomico: nessuna eccezione, fa "se possibile"
    public synchronized boolean tryInc() {
        if (cont == max) {
            return false;
        }
        cont++;
        return true;
    }

    public synchronized boolean tryDec() {
        if (cont == min) {
            return false;
        }
        cont--;
        return true;
    }
}