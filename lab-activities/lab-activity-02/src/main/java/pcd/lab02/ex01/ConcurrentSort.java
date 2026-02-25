package pcd.lab02.ex01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ConcurrentSort {

    static final boolean isDebugging = false;

    public static void main(String[] args) {

        BenchmarkConfig cfg = BenchmarkConfig.fromArgs(args);

        int cores = Runtime.getRuntime().availableProcessors();
        int[] threadConfigs = buildThreadConfigs(cores);

        log("Cores (availableProcessors): " + cores);
        log("Vector size: " + cfg.vectorSize);
        log("Warmup runs: " + cfg.warmupRuns + ", measured runs: " + cfg.measuredRuns);
        log("Thread configs: " + Arrays.toString(threadConfigs));

        log("Generating base array...");
        int[] base = genArray(cfg.vectorSize);
        log("Base array generated.");

        if (isDebugging) {
            dumpArray(base);
        }

        double t1Ms = Double.NaN;
        List<Result> results = new ArrayList<>();

        for (int nWorkers : threadConfigs) {
            if (nWorkers <= 0) {
                continue;
            }

            log("Benchmark with nSortingWorkers=" + nWorkers);

            for (int i = 0; i < cfg.warmupRuns; i++) {
                int[] v = Arrays.copyOf(base, base.length);
                runOnce(v, nWorkers);
            }

            long totalNs = 0;
            for (int i = 0; i < cfg.measuredRuns; i++) {
                int[] v = Arrays.copyOf(base, base.length);

                long t0 = System.nanoTime();
                runOnce(v, nWorkers);
                long t1 = System.nanoTime();

                long dt = t1 - t0;
                totalNs += dt;

                if (isDebugging) {
                    assert (isSorted(v));
                }
            }

            double avgMs = (totalNs / (double) cfg.measuredRuns) / 1_000_000.0;
            if (nWorkers == 1) {
                t1Ms = avgMs;
            }
            double speedup = Double.isNaN(t1Ms) ? Double.NaN : (t1Ms / avgMs);

            results.add(new Result(nWorkers, avgMs, speedup));
        }

        printResults(results);
    }

    private static void runOnce(int[] v, int nSortingWorkers) {
        int jobSize = v.length / nSortingWorkers;
        int from = 0;
        int to = jobSize - 1;

        List<SortingWorker> workers = new ArrayList<>();
        for (int i = 0; i < nSortingWorkers - 1; i++) {
            SortingWorker w = new SortingWorker("worker-" + (i + 1), v, from, to);
            w.start();
            workers.add(w);

            from = to + 1;
            to += jobSize;
        }

        SortingWorker w = new SortingWorker("worker-" + nSortingWorkers, v, from, v.length - 1);
        w.start();
        workers.add(w);

        MergingWorker m = new MergingWorker("merger", v, workers);
        m.start();

        try {
            m.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
    }

    private static int[] buildThreadConfigs(int cores) {
        int c = Math.max(1, cores);

        List<Integer> vals = new ArrayList<>();
        vals.add(1);
        vals.add(2);
        vals.add(4);
        vals.add(c);
        vals.add(2 * c);

        List<Integer> unique = new ArrayList<>();
        for (int v : vals) {
            if (!unique.contains(v)) {
                unique.add(v);
            }
        }

        int[] out = new int[unique.size()];
        for (int i = 0; i < unique.size(); i++) {
            out[i] = unique.get(i);
        }
        return out;
    }

    private static int[] genArray(int n) {
        int numElem = n;
        if (isDebugging) {
            numElem = Math.min(n, 100);
        }

        Random gen = new Random(123456789L);
        int[] v = new int[numElem];
        for (int i = 0; i < v.length; i++) {
            v[i] = isDebugging ? (gen.nextInt() % 100) : gen.nextInt();
        }
        return v;
    }

    private static boolean isSorted(int[] v) {
        if (v.length == 0) {
            return true;
        }
        int curr = v[0];
        for (int i = 1; i < v.length; i++) {
            if (curr > v[i]) {
                return false;
            }
            curr = v[i];
        }
        return true;
    }

    private static void dumpArray(int[] v) {
        for (int l : v) {
            System.out.print(l + " ");
        }
        System.out.println();
    }

    private static void printResults(List<Result> results) {
        System.out.println();
        System.out.println("N\tT_avg_ms\tSpeedup");
        for (Result r : results) {
            String sp = Double.isNaN(r.speedup) ? "n/a" : String.format("%.3f", r.speedup);
            System.out.println(r.nWorkers + "\t" + String.format("%.3f", r.avgMs) + "\t" + sp);
        }
        System.out.println();
    }

    private static void log(String msg) {
        System.out.println("[ " + System.currentTimeMillis() + " ][ "
                + Thread.currentThread().getName() + " ] " + msg);
    }

    private static class Result {
        final int nWorkers;
        final double avgMs;
        final double speedup;

        Result(int nWorkers, double avgMs, double speedup) {
            this.nWorkers = nWorkers;
            this.avgMs = avgMs;
            this.speedup = speedup;
        }
    }

    private static class BenchmarkConfig {
        final int vectorSize;
        final int warmupRuns;
        final int measuredRuns;

        BenchmarkConfig(int vectorSize, int warmupRuns, int measuredRuns) {
            this.vectorSize = vectorSize;
            this.warmupRuns = warmupRuns;
            this.measuredRuns = measuredRuns;
        }

        static BenchmarkConfig fromArgs(String[] args) {
            int vectorSize = 20_000_000;
            int warmupRuns = 2;
            int measuredRuns = 5;

            if (args.length >= 1) {
                vectorSize = parsePositiveInt(args[0], vectorSize);
            }
            if (args.length >= 2) {
                warmupRuns = parseNonNegativeInt(args[1], warmupRuns);
            }
            if (args.length >= 3) {
                measuredRuns = parsePositiveInt(args[2], measuredRuns);
            }

            return new BenchmarkConfig(vectorSize, warmupRuns, measuredRuns);
        }

        static int parsePositiveInt(String s, int def) {
            try {
                int v = Integer.parseInt(s.trim());
                return v > 0 ? v : def;
            } catch (Exception ex) {
                return def;
            }
        }

        static int parseNonNegativeInt(String s, int def) {
            try {
                int v = Integer.parseInt(s.trim());
                return v >= 0 ? v : def;
            } catch (Exception ex) {
                return def;
            }
        }
    }
}