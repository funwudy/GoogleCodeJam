package APAC2017.RoundB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by 31798 on 2016/8/28.
 */
public class ProblemC_NotEfficientEnough {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("C-small-practice.in"));
        //System.setIn(new FileInputStream("in.txt"));
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int N;
            long L1, R1, A, B, C1, C2, M;
            N = sc.nextInt();
            L1 = sc.nextLong();
            R1 = sc.nextLong();
            A = sc.nextLong();
            B = sc.nextLong();
            C1 = sc.nextLong();
            C2 = sc.nextLong();
            M = sc.nextLong();
            Interval[] intervals = new Interval[N + 1];
            intervals[1] = new Interval(L1, R1);
            Interval xy = intervals[1];
            for (int j = 2; j <= N; j++) {
                long x = (A * xy.left + B * xy.right + C1) % M;
                long y = (A * xy.right + B * xy.left + C2) % M;
                xy = new Interval(x, y);
                long L = Math.min(x, y);
                long R = Math.max(x, y);
                intervals[j] = new Interval(L, R);
            }
            sort(intervals, 1, N);
            //Arrays.sort(intervals, 1, N + 1);
            boolean[] flags = new boolean[N + 1];
            long min = Long.MAX_VALUE;
            for (int j = 1; j <= N; j++) {
                long sum = 0;
                flags[j] = true;
                for (int k = 1, l = 1; k <= N; k = l) {
                    if (!flags[k]) {
                        l = k + 1;
                        Interval tmp = new Interval(intervals[k].left, intervals[k].right);
                        while (l <= N && intervals[l].left <= tmp.right + 1) {
                            if (!flags[l]) {
                                tmp.right = Math.max(tmp.right, intervals[l].right);
                            }
                            l++;
                        }
                        sum += (tmp.right - tmp.left + 1);
                    } else {
                        l++;
                    }
                }
                flags[j] = false;
                min = Math.min(min, sum);
            }
            System.out.println("Case #" + (i + 1) + ": " + min);
        }

    }

    public static int partition(Interval[] intervals, int start, int end) {
        int idx = start;
        swap(intervals[idx], intervals[end]);
        int small = start - 1;
        for (; idx < end; idx++) {
            if (intervals[idx].left < intervals[end].left) {
                if (++small != idx) {
                    swap(intervals[small], intervals[idx]);
                }
            }
        }
        swap(intervals[++small], intervals[end]);
        return small;
    }

    public static void sort(Interval[] intervals, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = partition(intervals, start, end);
        if (start < mid - 1) {
            sort(intervals, start, mid - 1);
        }
        if (mid + 1 < end){
            sort(intervals, mid + 1, end);
        }
    }

    private static void swap(Interval interval1, Interval interval2) {
        Interval tmp = new Interval(interval1.left, interval1.right);
        interval1.left = interval2.left;
        interval1.right = interval2.right;
        interval2.left = tmp.left;
        interval2.right = tmp.right;
    }


    static class Interval implements Comparable<Interval> {
        long left, right;

        public Interval(long left, long right) {
            this.left = left;
            this.right = right;
        }


        @Override
        public int compareTo(Interval interval) {
            if (left < interval.left) {
                return -1;
            } else if (left > interval.left) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}




