package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        if (ifMoreThan(oomages, M)) return false;
        if (ifLessThan(oomages, M)) return false;
        return true;
    }

    private static boolean ifLessThan(List<Oomage> oomages, int M) {
        int leastOomageNum = Integer.MAX_VALUE;
        int[] hashBuckets = new int[M];
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            hashBuckets[bucketNum] += 1;
        }
        for (int i = 0; i < M; i++) {
            if (hashBuckets[i] < leastOomageNum) {
                leastOomageNum = hashBuckets[i];
            }
        }
        return leastOomageNum < (oomages.size() / 50);
    }

    private static boolean ifMoreThan(List<Oomage> oomages, int M) {
        int mostOomageNum = 0;
        int[] hashBuckets = new int[M];
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            hashBuckets[bucketNum] += 1;
        }
        for (int i = 0; i < M; i++) {
            if (hashBuckets[i] > mostOomageNum) {
                mostOomageNum = hashBuckets[i];
            }
        }
        return mostOomageNum > (oomages.size() / 2.5);
    }
}
