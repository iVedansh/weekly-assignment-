import java.util.Locale;
import java.util.Scanner;

abstract class Ride {
    protected double distance;

    Ride(double distance) {
        this.distance = distance;
    }

    abstract double calculateFare();
}

class Auto extends Ride {
    Auto(double distance) {
        super(distance);
    }

    @Override
    double calculateFare() {
        return 30 + 15 * distance;
    }
}

class Cab extends Ride {
    private double surgeFactor;

    Cab(double distance, double surgeFactor) {
        super(distance);
        this.surgeFactor = surgeFactor;
    }

    @Override
    double calculateFare() {
        return (50 + 20 * distance) * surgeFactor;
    }
}

class Bike extends Ride {
    private int waitingMinutes;

    Bike(double distance, int waitingMinutes) {
        super(distance);
        this.waitingMinutes = waitingMinutes;
    }

    @Override
    double calculateFare() {
        return 20 + 10 * distance + 2 * waitingMinutes;
    }
}

public class Problem2 {
    private static String format(double value) {
        return String.format(Locale.US, "%.2f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Ride[] rides = new Ride[n];

        for (int i = 0; i < n; i++) {
            String type = sc.next();
            double distance = sc.nextDouble();

            switch (type.toLowerCase()) {
                case "auto":
                    rides[i] = new Auto(distance);
                    break;
                case "cab":
                    double surge = sc.nextDouble();
                    rides[i] = new Cab(distance, surge);
                    break;
                case "bike":
                    int waiting = sc.nextInt();
                    rides[i] = new Bike(distance, waiting);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown ride type: " + type);
            }
        }

        double grandTotal = 0;
        for (Ride ride : rides) {
            double fare = ride.calculateFare();
            System.out.println(ride.getClass().getSimpleName() + ": " + format(fare));
            grandTotal += fare;
        }
        System.out.println("Total: " + format(grandTotal));
        sc.close();
    }
}
