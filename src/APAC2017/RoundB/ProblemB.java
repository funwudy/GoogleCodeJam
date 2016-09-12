package APAC2017.RoundB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by 31798 on 2016/9/8.
 */
public class ProblemB {

    public static final int MOD = 1000000007;
    public static final int MAXN = 100010;

    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("B-large-practice.in"));
        System.setOut(new PrintStream(new FileOutputStream("out.txt")));

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        long A, B, N, K;
        for (int kase = 1; kase <= T; kase++) {
            A = sc.nextLong();
            B = sc.nextLong();
            N = sc.nextLong();
            K = sc.nextLong();
//            Map<Long, Long> cntA = new HashMap<>(), cntB = new HashMap<>();
//            Map<Long, Long> cntEqual = new HashMap<>();
            long[] cntA = new long[MAXN], cntB = new long[MAXN];
            long[] cntEqual = new long[MAXN];
            for (int i = 1; i <= Math.min(N, K); i++) {
                long num = (N / K + (i <= N % K? 1 : 0)) % MOD;
                int ansA = (int) fastPow(i, A, K);
                cntA[ansA] += num;
                int ansB = (int) fastPow(i, B, K);
                cntB[ansB] += num;
                if ((ansA + ansB) % K == 0) {
                    cntEqual[i % (int) K] += num;
                }
            }
            long sum = 0;
            for (int i = 0; i < K; i++) {
                int other = (int) ((K - i) % K);
                cntA[i] %= MOD;
                cntB[other] %= MOD;
                sum += cntA[i] * cntB[other] % MOD;
                sum -= cntEqual[i];
                sum %= MOD;
            }
            sum = (sum + MOD) % MOD;
            System.out.println("Case #" + kase + ": " + sum);
        }
    }

    static long fastPow(long a, long b, long k) {
        long ans = 1;
        long base = a;
        while (b != 0) {
            if ((b & 1) != 0) {
                ans = (ans * base) % k;
            }
            base = (base * base) % k;
            b >>= 1;
        }
        return ans;
    }

    static void checkAndPut(Map<Long, Long> map, long key, long value) {
        if (map.containsKey(key)) {
            map.put(key, (map.get(key) + value) % MOD);
        } else {
            map.put(key, value);
        }
    }

}
