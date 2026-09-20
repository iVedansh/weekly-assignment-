class RaceEntry {
    protected String bibNumber;
    protected double balanceDue;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }
        this.bibNumber = bibNumber;
        this.balanceDue = entryFee;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber
                + " | Balance: " + balanceDue;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber
                + " | Category: " + category
                + " | Balance: " + balanceDue;
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + bibNumber
                + " | Team Size: " + teamSize
                + " | Balance: " + balanceDue;
    }
}

public class Problem4 {
    static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();

        for (RaceEntry entry : entries) {
            report.append(entry.announce()).append(" | ");

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                report.append("[Team size via downcast: ")
                      .append(relay.getTeamSize())
                      .append("] | ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {
        RaceEntry runner =
                new RunnerEntry("BIB2001", 80, "Open 10K");
        RaceEntry relay =
                new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(announceAll(new RaceEntry[]{runner, relay}));

        // This compiles but fails at runtime because the object is not a RelayTeamEntry:
        // RaceEntry plain = new RaceEntry("BIB5001", 50);
        // RelayTeamEntry bad = (RelayTeamEntry) plain;
    }
}
