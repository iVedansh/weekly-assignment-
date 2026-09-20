class RaceEntry {
    private static int bibCounter = 1000;
    private final String entryCode;

    protected String bibNumber;
    protected double entryFee;
    protected double balanceDue;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.balanceDue = entryFee;

        entryCode = "RACE-" + (++bibCounter);
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void pay(double amount) {
        if (amount > 0) {
            balanceDue = Math.max(0, balanceDue - amount);
        }
    }

    public void pay(double amount, String mode) {
        pay(amount);
        System.out.println("Paying via " + mode);
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        return code.charAt(0) == 'M'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isDigit(code.charAt(3))
                && Character.isUpperCase(code.charAt(4));
    }

    public static int getBibCounter() {
        return bibCounter - 1000;
    }
}

class EliteRunnerEntry extends RaceEntry {
    public EliteRunnerEntry(String bibNumber, double entryFee) {
        super(bibNumber, entryFee);
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        if (teamSize <= 0) {
            throw new IllegalArgumentException("Team size must be positive");
        }
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class Problem5 {
    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + relay + " relay | "
                + individual + " individual";
    }

    public static void main(String[] args) {
        RaceEntry r = new RaceEntry("BIB1001", 100);
        r.pay(10, "UPI");

        RaceEntry[] entries = {
                new EliteRunnerEntry("BIB2001", 150),
                null,
                new RelayTeamEntry("BIB4001", 300, 4)
        };

        System.out.println(settleNight(entries));

        System.out.println(RaceEntry.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));
        System.out.println(RaceEntry.isValidDiscountCode("X123A"));
        System.out.println(RaceEntry.getBibCounter());
    }
}
