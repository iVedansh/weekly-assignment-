public class Problem4_InstanceStaticBoundary {
    static class BrokenSrmStudent {
        static String name;
        static String regNo;
        static int attendance;

        BrokenSrmStudent(String name, String regNo, int attendance) {
            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }
    }

    static class SrmStudent {
        String name;
        String regNo;
        int attendance;

        static String university = "SRM";
        static int admissionCount = 0;

        SrmStudent(String name, int attendance) {
            this.name = name;
            this.attendance = attendance;
            admissionCount++;
            this.regNo = "RA231100301" + String.format("%03d", admissionCount);
        }

        void printIdCard() {
            System.out.println(name + " | " + regNo);
        }

        static void printTotalAdmissions() {
            System.out.println("Students admitted so far: " + admissionCount);
        }
    }

    public static void main(String[] args) {
        BrokenSrmStudent first = new BrokenSrmStudent("Ravi", "RA01", 80);
        BrokenSrmStudent second = new BrokenSrmStudent("Meera", "RA02", 90);

        System.out.println("Broken version:");
        System.out.println(first.name);
        System.out.println(second.name);

        System.out.println("Fixed version:");
        SrmStudent ravi = new SrmStudent("Ravi", 80);
        SrmStudent meera = new SrmStudent("Meera", 90);
        ravi.printIdCard();
        meera.printIdCard();
        SrmStudent.printTotalAdmissions();
    }

    // name, regNo and attendance must be instance fields because each student
    // owns different values. university and admissionCount are shared class data.
}
