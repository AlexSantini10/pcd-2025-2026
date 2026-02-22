package pcd.lab01.ex01;

import java.util.*;

public class SequentialSort {

    static final int VECTOR_SIZE = 400_000_000;

    public static void main(String[] args) {

        log("Num elements to sort: " + VECTOR_SIZE);
        log("Generating array.");
        var v = genArray(VECTOR_SIZE);
        log("Array generated.");

        // Baseline sequenziale, serve come riferimento per lo speedup.
        var vSeq = v.clone();
        log("Sequential sorting...");
        long ts0 = System.nanoTime();
        Arrays.sort(vSeq, 0, vSeq.length);
        long ts1 = System.nanoTime();
        long seqMs = (ts1 - ts0) / 1_000_000;
        log("Sequential done. Time elapsed: " + seqMs + " ms");

        // Versione concorrente generalizzata:
        // 1) split in p chunk disgiunti
        // 2) sort locale di ogni chunk in parallelo
        // 3) merge a round (coppie) finché resta un run unico
        int p = Runtime.getRuntime().availableProcessors();
        log("Parallel sorting with p=" + p + " threads...");

        var vPar = v.clone();

        long tp0 = System.nanoTime();
        parallelSort(vPar, p);
        long tp1 = System.nanoTime();

        long parMs = (tp1 - tp0) / 1_000_000;
        log("Parallel done. Time elapsed: " + parMs + " ms");

        double speedup = (double) (ts1 - ts0) / (double) (tp1 - tp0);
        log("Speedup: " + speedup + "x");

        // dumpArray(vPar);
    }

    // Sorting parallelo con soli Thread:
    // - ogni thread ordina un range [from,to) dello stesso array
    // - poi facciamo merge a livelli usando un buffer ausiliario
    private static void parallelSort(int[] v, int p) {

        int n = v.length;
        if (n <= 1) {
            return;
        }

        // Se p > n non ha senso, evito thread inutili.
        p = Math.max(1, Math.min(p, n));

        // bounds[i]..bounds[i+1] è il chunk i-esimo.
        int[] bounds = computeBounds(n, p);

        // Phase 1: sort locale, nessuna contesa perché i range sono disgiunti.
        Thread[] sortThreads = new Thread[p];
        for (int i = 0; i < p; i++) {
            final int from = bounds[i];
            final int to = bounds[i + 1];
            sortThreads[i] = new Thread(() -> Arrays.sort(v, from, to), "sort-" + i);
            sortThreads[i].start();
        }
        joinAll(sortThreads);

        // Phase 2: merge. All'inizio abbiamo p run ordinati.
        // In ogni round unisco a coppie: (0,1), (2,3), ...
        int runs = p;
        int[] start = new int[runs];
        int[] end = new int[runs];
        for (int i = 0; i < runs; i++) {
            start[i] = bounds[i];
            end[i] = bounds[i + 1];
        }

        // src/dst: ping-pong per evitare copie inutili ad ogni round.
        int[] src = v;
        int[] dst = new int[n];

        while (runs > 1) {

            int mergedRuns = (runs + 1) / 2;
            int[] nextStart = new int[mergedRuns];
            int[] nextEnd = new int[mergedRuns];

            Thread[] mergeThreads = new Thread[mergedRuns];

            for (int k = 0; k < mergedRuns; k++) {
                int left = 2 * k;
                int right = left + 1;

                int a0 = start[left];
                int a1 = end[left];

                int b0;
                int b1;

                // Se runs è dispari, l'ultimo run rimane “spaiato” e lo copio.
                if (right < runs) {
                    b0 = start[right];
                    b1 = end[right];
                } else {
                    b0 = a1;
                    b1 = a1;
                }

                int out0 = a0;

                nextStart[k] = out0;
                nextEnd[k] = b1;

                // Java: la lambda vuole variabili final/effectively-final.
                // src/dst cambiano a fine round, quindi faccio snapshot qui.
                final int[] srcRef = src;
                final int[] dstRef = dst;
                final int a0f = a0;
                final int a1f = a1;
                final int b0f = b0;
                final int b1f = b1;
                final int out0f = out0;

                mergeThreads[k] = new Thread(() -> {
                    if (b0f == b1f) {
                        System.arraycopy(srcRef, a0f, dstRef, out0f, a1f - a0f);
                    } else {
                        mergeTwoSortedRanges(srcRef, a0f, a1f, b0f, b1f, dstRef, out0f);
                    }
                }, "merge-" + k);

                mergeThreads[k].start();
            }

            joinAll(mergeThreads);

            // Swap e aggiorno la lista dei run per il round successivo.
            int[] tmp = src;
            src = dst;
            dst = tmp;

            start = nextStart;
            end = nextEnd;
            runs = mergedRuns;
        }

        // Se l'ultimo risultato sta nel buffer ausiliario, lo ricopio su v.
        if (src != v) {
            System.arraycopy(src, 0, v, 0, n);
        }
    }

    // Partiziona n elementi in p chunk contigui quasi uguali.
    private static int[] computeBounds(int n, int p) {
        int[] bounds = new int[p + 1];
        bounds[0] = 0;

        int base = n / p;
        int rem = n % p;

        int idx = 0;
        for (int i = 0; i < p; i++) {
            int size = base + (i < rem ? 1 : 0);
            idx += size;
            bounds[i + 1] = idx;
        }
        return bounds;
    }

    // Merge lineare di due range già ordinati: [a0,a1) e [b0,b1).
    private static void mergeTwoSortedRanges(int[] src,
                                             int a0, int a1,
                                             int b0, int b1,
                                             int[] dst,
                                             int dst0) {

        int i = a0;
        int j = b0;
        int k = dst0;

        while (i < a1 && j < b1) {
            int x = src[i];
            int y = src[j];
            if (x <= y) {
                dst[k++] = x;
                i++;
            } else {
                dst[k++] = y;
                j++;
            }
        }

        while (i < a1) {
            dst[k++] = src[i++];
        }

        while (j < b1) {
            dst[k++] = src[j++];
        }
    }

    // Join “a blocco”, è la barriera tra una fase e la successiva.
    private static void joinAll(Thread[] threads) {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while joining threads.", e);
            }
        }
    }

    private static int[] genArray(int n) {
        Random gen = new Random(System.currentTimeMillis());
        var v = new int[n];
        for (int i = 0; i < v.length; i++) {
            v[i] = gen.nextInt();
        }
        return v;
    }

    private static void dumpArray(int[] v) {
        for (var l : v) {
            System.out.print(l + " ");
        }
        System.out.println();
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}