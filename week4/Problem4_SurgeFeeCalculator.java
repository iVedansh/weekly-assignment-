public class Problem4_SurgeFeeCalculator {
    static final class SurgeFeeCalculator {
        private final double minimumSurgePercent;

        SurgeFeeCalculator(double minimumSurgePercent) {
            this.minimumSurgePercent = minimumSurgePercent;
        }

        final double calculateSurgeFee(double orderValue, int delayMinutes) {
            if (orderValue < 0 || delayMinutes < 0) {
                throw new IllegalArgumentException("orderValue and delayMinutes cannot be negative");
            }

            if (delayMinutes == 0) {
                return 0.0;
            }

            int first = Math.min(delayMinutes, 5);
            int second = Math.max(0, Math.min(delayMinutes, 15) - 5);
            int third = Math.max(0, delayMinutes - 15);

            double fee = orderValue * 0.005 * first
                    + orderValue * 0.01 * second
                    + orderValue * 0.02 * third;

            double floor = orderValue * minimumSurgePercent / 100.0;
            return Math.max(fee, floor);
        }
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);

        System.out.println("Rs " + calculator.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calculator.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calculator.calculateSurgeFee(500, 16));
    }
}
