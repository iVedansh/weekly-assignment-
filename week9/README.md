# Week 9 — Category A Practice Problems

Java solutions for all five problems in the supplied **Category A · Practice Problems** sheet.

## Files

| File | Main concepts |
| --- | --- |
| `Assignment/Problem1.java` | Static nested class, member inner class, iterator-style cursor |
| `Assignment/Problem2.java` | Abstract class, inheritance, runtime polymorphism |
| `Assignment/Problem3.java` | Queue, Round-Robin scheduling |
| `Assignment/Problem4.java` | Field hiding, static member lookup, method overriding |
| `Assignment/Problem5.java` | Stable merge sort, Kadane's algorithm, binary search, two pointers |

## Compile and run

Run each program from the `week9` directory:

```bash
javac Assignment/Problem1.java
java -cp Assignment Problem1
```

Replace `Problem1` with `Problem2` through `Problem5` for the other programs.

## Input formats

- **Problem 1:** number of line items, followed by `product quantity unitPrice` on each line.
- **Problem 2:** number of rides, followed by `Auto distance`, `Cab distance surgeFactor`, or `Bike distance waitingMinutes` for each ride.
- **Problem 3:** quantum, number of jobs, then `jobName requiredTime` for each job.
- **Problem 4:** no input.
- **Problem 5:** candidate count, then each candidate's name and six scores. After the candidates, enter any number of `CUTOFF x` and `PAIR target` queries; queries are read until end-of-file.

## Complexity

| Problem | Operation | Time | Extra space |
| --- | --- | --- | --- |
| 1 | Add one item | O(1) amortized | O(1) amortized |
| 1 | Walk through all items | O(n) | O(1) cursor space |
| 2 | Price all rides | O(n) | O(n) for the Ride array |
| 3 | Run scheduler | O(sum ceil(burstTime / quantum)) | O(n) |
| 4 | Print the three look-ups | O(1) | O(1) |
| 5 | Stable merge sort | O(n log n) | O(n) |
| 5 | Best climb for all candidates | O(n) total | O(1) per candidate |
| 5 | Each CUTOFF query | O(log n) | O(1) |
| 5 | Each PAIR query | O(n) | O(1) |

The scheduler's running-time expression counts each time slice; it is at most O(T) where T is the sum of all required job times when the quantum is at least one.

For Problem 5, the best climb is the maximum-sum contiguous sequence of weekly score changes. Equal-gain runs keep the earliest run. Merge sort is stable, so candidates with equal totals remain in their original registration order.
