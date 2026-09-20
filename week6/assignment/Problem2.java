class RaceEntry {
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
    }

    public void pay(double amount) {
        if (amount > 0) {
            balanceDue = Math.max(0, balanceDue - amount);
        }
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue;
    }
}

class RunnerEntry extends RaceEntry {
    protected String category;

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

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public String announce() {
        return "Elite Runner | Bib: " + bibNumber
                + " | Category: " + category
                + " | Sponsor Bonus: " + sponsorBonus
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

public class Problem2 {
    static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Base or direct runner entry";
    }

    static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;
        for (RaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        RaceEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        RaceEntry elite = new EliteRunnerEntry(
                "BIB3001", 150, "Elite Full Marathon", 500);
        RaceEntry relay = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runner.announce());
        System.out.println(elite.announce());
        System.out.println(relay.announce());

        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(relay));

        System.out.println(getTotalBalanceDue(
                new RaceEntry[]{runner, elite, relay}));
    }
}
