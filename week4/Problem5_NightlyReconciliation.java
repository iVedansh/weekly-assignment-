public class Problem5_NightlyReconciliation {
    static class DeliveryAccount {
        static {
            System.out.println("Delivery reconciliation engine initialized.");
        }

        protected String studentId;
        protected double orderValue;

        DeliveryAccount(String studentId, double orderValue) {
            this.studentId = studentId;
            this.orderValue = orderValue;
        }

        DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        final double calculateSurgeFee(int delayMinutes) {
            if (delayMinutes < 0 || orderValue < 0) {
                throw new IllegalArgumentException("Invalid order value or delay");
            }

            if (delayMinutes == 0) {
                return 0.0;
            }

            int first = Math.min(delayMinutes, 5);
            int second = Math.max(0, Math.min(delayMinutes, 15) - 5);
            int third = Math.max(0, delayMinutes - 15);

            return orderValue * 0.005 * first
                    + orderValue * 0.01 * second
                    + orderValue * 0.02 * third;
        }
    }

    static class Premium extends DeliveryAccount {
        Premium(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        @Override
        final double calculateSurgeFee(int delayMinutes) {
            return super.calculateSurgeFee(delayMinutes) * 0.5;
        }
    }

    static double grandTotal;

    static void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            return;
        }

        // The supplied amount is used as this account's reconciled order value.
        account.orderValue = amount;

        double fee = account.calculateSurgeFee(delayMinutes);
        grandTotal += fee;

        if (account instanceof Premium) {
            System.out.println(account.studentId + " premium fee: Rs " + fee);
        } else {
            System.out.println(account.studentId + " regular fee: Rs " + fee);
        }
    }

    static void processBatch(DeliveryAccount[] accounts,
                             double[] amounts,
                             int[] delayMinutesArray) {
        if (accounts.length != amounts.length
                || accounts.length != delayMinutesArray.length) {
            throw new IllegalArgumentException("Parallel arrays must have matching lengths");
        }

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }

            processAccount(accounts[i], amounts[i], delayMinutesArray[i]);
            processed++;

            if (accounts[i] instanceof Premium) {
                premiumCount++;
            } else {
                regularCount++;
            }
        }

        System.out.println(processed + " processed | " + nullSkipped
                + " null skipped | " + premiumCount + " premium | "
                + regularCount + " regular | grand total surge fees = Rs "
                + grandTotal);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delays = {10, 5, 0};

        processBatch(accounts, amounts, delays);
    }
}
