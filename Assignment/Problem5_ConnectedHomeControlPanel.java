abstract class HomeDevice {
    private static int counter = 0;
    private final String serialNumber;
    protected HomeDevice() { serialNumber = "HD-" + (++counter); }
    public abstract String activate();
    public String getSerialNumber() { return serialNumber; }
}
interface RemoteControllable { String connect(String appId); }
interface EnergyTrackable { double getConsumptionWatts(); }
class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private final double consumptionWatts;
    public WashingMachine(double consumptionWatts) {
        if (consumptionWatts < 0) throw new IllegalArgumentException("Consumption cannot be negative");
        this.consumptionWatts = consumptionWatts;
    }
    public String activate() { return "Washing machine " + getSerialNumber() + " activated"; }
    public String connect(String appId) { return "Washing machine " + getSerialNumber() + " connected to " + appId; }
    public double getConsumptionWatts() { return consumptionWatts; }
}
class Refrigerator extends HomeDevice implements EnergyTrackable {
    private final double consumptionWatts;
    public Refrigerator(double consumptionWatts) {
        if (consumptionWatts < 0) throw new IllegalArgumentException("Consumption cannot be negative");
        this.consumptionWatts = consumptionWatts;
    }
    public String activate() { return "Refrigerator " + getSerialNumber() + " activated"; }
    public double getConsumptionWatts() { return consumptionWatts; }
}
class MobileApp implements RemoteControllable {
    private final String appName;
    public MobileApp(String appName) {
        if (appName == null || appName.isBlank()) throw new IllegalArgumentException("App name is required");
        this.appName = appName;
    }
    public String connect(String appId) { return appName + " connected to " + appId; }
}
public class Problem5_ConnectedHomeControlPanel {
    public static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) System.out.println(item.connect(appId));
    }
    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) return ((EnergyTrackable) d).getConsumptionWatts();
        return 0.0;
    }
    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        Refrigerator fridge = new Refrigerator(150.0);
        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));
        System.out.println(getConsumptionIfTrackable(fridge));
        System.out.println(app.connect("HomeConnect"));
        HomeDevice ref = wm;
        System.out.println(getConsumptionIfTrackable(ref));
    }
}