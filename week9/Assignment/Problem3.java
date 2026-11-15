import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Problem3 {
    static class Job {
        String name;
        int remainingTime;

        Job(String name, int remainingTime) {
            this.name = name;
            this.remainingTime = remainingTime;
        }
    }

    static class Finish {
        String name;
        int time;

        Finish(String name, int time) {
            this.name = name;
            this.time = time;
        }
    }

    static class LabScheduler {
        private final int quantum;
        private final Queue<Job> queue = new ArrayDeque<>();
        private final List<String> slices = new ArrayList<>();
        private final List<Finish> finished = new ArrayList<>();

        LabScheduler(int quantum) {
            this.quantum = quantum;
        }

        void addJob(String name, int requiredTime) {
            queue.offer(new Job(name, requiredTime));
        }

        void run() {
            int currentTime = 0;

            while (!queue.isEmpty()) {
                Job job = queue.poll();
                int start = currentTime;
                int runTime = Math.min(quantum, job.remainingTime);

                currentTime += runTime;
                job.remainingTime -= runTime;
                slices.add(job.name + "(" + start + "-" + currentTime + ")");

                if (job.remainingTime > 0) {
                    queue.offer(job);
                } else {
                    finished.add(new Finish(job.name, currentTime));
                }
            }
        }

        void printResult() {
            System.out.println(String.join(" ", slices));
            System.out.print("finish");
            for (Finish item : finished) {
                System.out.print(" " + item.name + " " + item.time);
                if (item != finished.get(finished.size() - 1)) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int quantum = sc.nextInt();
        int n = sc.nextInt();

        LabScheduler scheduler = new LabScheduler(quantum);
        for (int i = 0; i < n; i++) {
            scheduler.addJob(sc.next(), sc.nextInt());
        }

        scheduler.run();
        scheduler.printResult();
        sc.close();
    }
}
