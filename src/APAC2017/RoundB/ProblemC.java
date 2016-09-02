package APAC2017.RoundB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by 31798 on 2016/9/2.
 */
public class ProblemC {

    public static void main(String[] args) throws FileNotFoundException {
        //System.setIn(new FileInputStream("in.txt"));
        System.setIn(new FileInputStream("C-large-practice.in"));

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int kase = 1; kase <= T; kase++) {
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
            Point[] points = new Point[2 * N];
            points[0] = new Point(L1, 0, 1);
            points[1] = new Point(R1 + 1, 0, -1);
            Long preX = L1;
            Long preY = R1;
            for (int i = 1; i < N; i++) {
                long xi = (A * preX + B * preY + C1) % M;
                long yi = (A * preY + B * preX + C2) % M;
                preX = xi;
                preY = yi;
                long Li = Math.min(xi, yi);
                long Ri = Math.max(xi, yi);
                points[i * 2] = new Point(Li, i, 1);
                points[i * 2 + 1] = new Point(Ri + 1, i, -1);
            }
            Arrays.sort(points);
            //quickSort(points, 0, points.length - 1);
            long lastStart = points[0].position;
            long lastSingleStart = points[0].position;
            long totalLength = 0;
            long[] covereds = new long[N];
            Set<Integer> edges = new HashSet<>();
            for (int i = 0; i < points.length; i++) {
                if (edges.size() == 1) {
                    covereds[edges.iterator().next()] += points[i].position - lastSingleStart;
                }
                if (points[i].type == 1) {
                    edges.add(points[i].id);
                    if (edges.size() == 1) {
                        lastStart = points[i].position;
                    }
                } else {
                    edges.remove(points[i].id);
                }
                if (edges.size() == 1) {
                    lastSingleStart = points[i].position;
                } else if (edges.size() == 0) {
                    totalLength += points[i].position - lastStart;
                }
            }
            long maxSingleLength = 0;
            for (int i = 0; i < covereds.length; i++) {
                maxSingleLength = Math.max(maxSingleLength, covereds[i]);
            }
            System.out.println("Case #" + kase + ": " + (totalLength - maxSingleLength));
        }
    }

    static class Point implements Comparable<Point> {
        long position;
        int id;
        int type;//1 means start of an edge, -1 means end

        public Point(long position, int id, int type) {
            this.position = position;
            this.id = id;
            this.type = type;
        }

        @Override
        public int compareTo(Point p) {
            if (position < p.position || position == p.position && type > p.type) {
                return -1;
            } else if (position > p.position || position == position && type < p.type) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    static void quickSort(Point[] points, int start, int end) { // just for practicing, stack over flow when large data set
        if (points != null && start < end) {
            int mid = partition(points, start, end);
            quickSort(points, start, mid - 1);
            quickSort(points, mid + 1, end);
        }
    }

    private static int partition(Point[] points, int start, int end) {
        int pivot = start;
        swap(points[pivot], points[end]);
        int small = start - 1;
        for (int i = start; i < end; i++) {
            if (points[i].compareTo(points[end]) < 0) {
                if (++small != i) {
                    swap(points[i], points[small]);
                }
            }
        }
        swap(points[++small], points[end]);
        return small;
    }

    private static void swap(Point p1, Point p2) {
        Point tmp = new Point(p2.position, p2.id, p2.type);
        p2.position = p1.position;
        p2.id = p1.id;
        p2.type = p1.type;
        p1.position = tmp.position;
        p1.id = tmp.id;
        p1.type = tmp.type;
    }
}
