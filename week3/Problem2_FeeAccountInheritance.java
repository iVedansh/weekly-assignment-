public class Problem2_FeeAccountInheritance {
    static class FeeAccount {
        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = 0;
        }

        void pay(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected.");
                return;
            }
            amountPaid += amount;
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount extends FeeAccount {
        HostelFeeAccount(String regNo, double totalFee) {
            super(regNo, totalFee);
        }

        void payInTwoInstallments(double amount) {
            pay(amount / 2);
            pay(amount / 2);
        }
    }

    static class ScholarshipFeeAccount extends FeeAccount {
        private double scholarshipPercent;

        ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
            super(regNo, totalFee);
            this.scholarshipPercent = scholarshipPercent;
        }

        double effectiveDue() {
            return getDue() * (1 - scholarshipPercent / 100.0);
        }
    }

    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA01", 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("RA02", 200000);
        ScholarshipFeeAccount scholarship =
                new ScholarshipFeeAccount("RA03", 180000, 20);

        plain.pay(150000);
        hostel.payInTwoInstallments(60000);

        FeeAccount[] accounts = {plain, hostel, scholarship};

        for (FeeAccount account : accounts) {
            if (account instanceof ScholarshipFeeAccount) {
                System.out.println("Scholarship account effective due: Rs "
                        + ((ScholarshipFeeAccount) account).effectiveDue());
            } else if (account instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + account.getDue());
            } else {
                System.out.println("Plain account due: Rs " + account.getDue());
            }
        }
    }
}
