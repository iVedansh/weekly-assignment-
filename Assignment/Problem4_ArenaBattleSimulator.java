interface Attackable {
    String attack();
    String attack(String weaponName);
}
interface Defendable { String defend(); }
abstract class GameCharacter {
    private static int counter = 0;
    private final String characterId;
    protected GameCharacter() { characterId = "CHAR-" + (++counter); }
    public abstract String getSpecialMove();
    public String getCharacterId() { return characterId; }
}
class Warrior extends GameCharacter implements Attackable, Defendable {
    private final String name;
    public Warrior(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        this.name = name;
    }
    public String attack() { return name + " strikes with a blade"; }
    public String attack(String weaponName) { return name + " strikes with an " + weaponName; }
    public String defend() { return name + " raises a shield"; }
    public String getSpecialMove() { return name + " unleashes Whirlwind Slash"; }
}
class Trap implements Defendable {
    private final String trapType;
    public Trap(String trapType) {
        if (trapType == null || trapType.isBlank()) throw new IllegalArgumentException("Trap type is required");
        this.trapType = trapType;
    }
    public String defend() { return trapType + " triggers automatically"; }
}
public class Problem4_ArenaBattleSimulator {
    public static void resolveDefense(Defendable[] combatants) {
        for (Defendable combatant : combatants) System.out.println(combatant.defend());
    }
    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        Trap t = new Trap("Spike Pit");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());
        System.out.println(t.defend());
        resolveDefense(new Defendable[]{w, t});
    }
}