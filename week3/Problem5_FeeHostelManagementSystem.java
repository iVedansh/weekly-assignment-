public class Problem5_FeeHostelManagementSystem {
    static class FeeAccount {
        private double totalFee;
        private double amountPaid;

        FeeAccount(double totalFee) {
            this.totalFee = totalFee;
        }

        void pay(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected: amount must be positive.");
                return;
            }
            amountPaid += amount;
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount extends FeeAccount {
        HostelFeeAccount(double totalFee) {
            super(totalFee);
        }

        void payInTwoInstallments(double amount) {
            pay(amount / 2);
            pay(amount / 2);
        }
    }

    static class HostelRoom {
        String roomNo;
        int beds;
        int occupied;

        HostelRoom(String roomNo, int beds) {
            this.roomNo = roomNo;
            this.beds = beds;
        }

        boolean allot(String studentName) {
            if (occupied < beds) {
                occupied++;
                return true;
            }
            return false;
        }
    }

    static class SrmStudent {
        static int totalStudents = 0;
        String name;
        String regNo;
        HostelFeeAccount feeAccount;
        HostelRoom room;

        SrmStudent(String name, String regNo, double totalFee) {
            this.name = name;
            this.regNo = regNo;
            this.feeAccount = new HostelFeeAccount(totalFee);
            totalStudents++;
        }

        String fullStatus() {
            String roomNumber = (room == null) ? "unallotted" : room.roomNo;
            return name + " | Due: Rs " + feeAccount.getDue()
                    + " | Room: " + roomNumber;
        }
    }

    public static void main(String[] args) {
        SrmStudent ravi = new SrmStudent("Ravi", "RA01", 150000);
        SrmStudent anitha = new SrmStudent("Anitha", "RA02", 200000);
        SrmStudent karthik = new SrmStudent("Karthik", "RA03", 200000);

        HostelRoom room1 = new HostelRoom("C-214", 1);
        HostelRoom room2 = new HostelRoom("C-507", 1);

        if (room1.allot(ravi.name)) ravi.room = room1;
        if (room2.allot(anitha.name)) anitha.room = room2;

        ravi.feeAccount.pay(10000);
        anitha.feeAccount.pay(20000);
        karthik.feeAccount.pay(-5000);

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());
        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}
