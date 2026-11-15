import java.util.Scanner;

public class Problem5 {
    static class Candidate {
        String name;
        int[] scores = new int[6];
        int total;
        int registrationOrder;
        int bestGain;
        int bestStart;
        int bestEnd;

        Candidate(String name, int[] scores, int registrationOrder) {
            this.name = name;
            this.scores = scores;
            this.registrationOrder = registrationOrder;
            for (int score : scores) {
                total += score;
            }
            findBestClimb();
        }

        // Maximum-sum consecutive run of weekly score changes (Kadane's algorithm).
        // Strict comparisons preserve the earliest run when gains are tied.
        private void findBestClimb() {
            int currentSum = scores[1] - scores[0];
            int currentStart = 1;
            bestGain = currentSum;
            bestStart = 1;
            bestEnd = 2;

            for (int i = 2; i < scores.length; i++) {
                int change = scores[i] - scores[i - 1];

                if (currentSum + change < change) {
                    currentSum = change;
                    currentStart = i;
                } else {
                    currentSum += change;
                }

                if (currentSum > bestGain) {
                    bestGain = currentSum;
                    bestStart = currentStart;
                    bestEnd = i + 1;
                }
            }
        }
    }

    // Stable merge sort: candidates with equal totals retain registration order.
    private static void mergeSort(Candidate[] a, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);

        Candidate[] temp = new Candidate[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (a[i].total >= a[j].total) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        while (i <= mid) temp[k++] = a[i++];
        while (j <= right) temp[k++] = a[j++];

        for (int p = 0; p < temp.length; p++) {
            a[left + p] = temp[p];
        }
    }

    // The list is sorted in descending order. Return the first index below cutoff.
    private static int countAtLeast(Candidate[] a, int cutoff) {
        int low = 0, high = a.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (a[mid].total >= cutoff) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private static String findPair(Candidate[] a, int target) {
        int left = 0, right = a.length - 1;

        while (left < right) {
            int sum = a[left].total + a[right].total;
            if (sum == target) {
                return a[left].name + " + " + a[right].name;
            } else if (sum > target) {
                left++;
            } else {
                right--;
            }
        }
        return "no pair";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Candidate[] candidates = new Candidate[n];

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int[] scores = new int[6];
            for (int j = 0; j < 6; j++) {
                scores[j] = sc.nextInt();
            }
            candidates[i] = new Candidate(name, scores, i);
        }

        mergeSort(candidates, 0, n - 1);

        System.out.println("Shortlist:");
        for (int i = 0; i < n; i++) {
            Candidate c = candidates[i];
            System.out.println((i + 1) + ". " + c.name + " " + c.total
                    + " | best climb " + (c.bestGain >= 0 ? "+" : "") + c.bestGain
                    + " (test " + c.bestStart + " to " + c.bestEnd + ")");
        }

        // Process commands until end-of-file. Each query is O(log n) or O(n).
        while (sc.hasNext()) {
            String command = sc.next();
            int value = sc.nextInt();

            if (command.equalsIgnoreCase("CUTOFF")) {
                System.out.println("CUTOFF " + value + ": "
                        + countAtLeast(candidates, value) + " candidates");
            } else if (command.equalsIgnoreCase("PAIR")) {
                System.out.println("PAIR " + value + ": " + findPair(candidates, value));
            } else {
                throw new IllegalArgumentException("Unknown query: " + command);
            }
        }
        sc.close();
    }
}
