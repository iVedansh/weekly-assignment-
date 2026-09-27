interface Exportable { String exportData(); }
class ExportCounter {
    private static int totalExports = 0;
    static void increment() { totalExports++; }
    static int getTotalExports() { return totalExports; }
}
class ReportGenerator implements Exportable {
    private final String reportName;
    public ReportGenerator(String reportName) {
        if (reportName == null || reportName.isBlank()) throw new IllegalArgumentException("Report name is required");
        this.reportName = reportName;
    }
    public String exportData() {
        ExportCounter.increment();
        return "Exported report: " + reportName;
    }
}
class UserProfile implements Exportable {
    private final String username;
    public UserProfile(String username) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username is required");
        this.username = username;
    }
    public String exportData() {
        ExportCounter.increment();
        return "Exported profile: " + username;
    }
}
public class Problem2_OneClickDataExport {
    public static int getTotalExports() { return ExportCounter.getTotalExports(); }
    public static void exportAll(Exportable[] items) {
        for (Exportable item : items) System.out.println(item.exportData());
    }
    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");
        Exportable ref = r;
        exportAll(new Exportable[]{ref, u});
        System.out.println(getTotalExports());
    }
}