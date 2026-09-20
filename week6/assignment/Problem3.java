class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double balanceDue;

    private double[] lateFeeHistory = new double[10];
    private int historyCount = 0;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.balanceDue = entryFee;
    }

    public void pay(double amount) {
        if (amount > 0) {
            balanceDue = Math.max(0, balanceDue - amount);
        }
    }

    protected void applyLateFee(double amount) {
        if (amount > 0) {
            balanceDue += amount;
            if (historyCount < lateFeeHistory.length) {
                lateFeeHistory[historyCount++] = amount;
            }
        }
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[historyCount];
        System.arraycopy(lateFeeHistory, 0, copy, 0, historyCount);
        return copy;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class Problem3 {
    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");

        r.pay(30);
        r.applyLateFee(20);

        System.out.println(r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        history[0] = 999;

        System.out.println(java.util.Arrays.toString(r.getLateFeeHistory()));
    }
}
