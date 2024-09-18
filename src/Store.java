import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Store instance;
    protected static ArrayList<Warrior> warriors = new ArrayList<>();
    protected static ArrayList<Equipment> equipments = new ArrayList<>();

    private Store() {

    }

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();


        }
        return instance;
    }

    public static void addWarrior(String type,String name,String ethnicity,int price,double attack,double defence,double health,double speed) {
        warriors.add(new Warrior(type,name,ethnicity,price,attack,defence,health,speed,null,null));
    }

    public static void addEquipment(String type,String name,int price,double attack,double defence,double health,double speed) {
        equipments.add(new Equipment(type,name,price,attack,defence,health,speed));
    }

    public static void displayWarriors() {
        System.out.println(" Type     Name       Health        Speed      Attack      Defence     Price");
        for (Warrior w: warriors) {
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            System.out.printf(w.type + " :: " + w.getName() + " : Health : " + w.getHealth() + " Speed : " + w.getSpeed() + " Attack : " + w.getAttack() + " Defence : " + w.getDefence() + " Price : " + w.price + "gc\n");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
        }
    }

    public static void displayEquipments() {
        System.out.println("  Type      Name       Health        Speed        Attack        Defence        Price");
        for (Equipment e: equipments) {
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            System.out.println(e.type + " :: " + e.getName() + " --> Speed : " + e.getSpeed() + " Attack : " + e.getAttack() + " Defence : " + e.getDefence() + " Price : " + e.price + "gc\n");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
        }
    }

    public static void createStore() {

        addWarrior("Archer", "Shooter", "Highlander", 80, 11, 4, 6, 9);
        addWarrior("Archer", "Ranger", "Highlander", 115, 14, 5, 8, 10);
        addWarrior("Archer", "Sunfire", "Sunchildren", 160, 15, 5, 7, 14);
        addWarrior("Archer", "Zing", "Sunchildren", 200, 16, 9, 11, 14);
        addWarrior("Archer", "Saggitarius", "Mystic", 230, 18, 7, 12, 17);

        addWarrior("Knight", "Squire", "Marshlander", 85, 8, 9, 7, 8);
        addWarrior("Knight", "Cavalier", "Highlander", 110, 10, 12, 7, 10);
        addWarrior("Knight", "Templar", "Sunchildren", 155, 14, 16, 12, 12);
        addWarrior("Knight", "Zoro", "Highlander", 180, 17, 16, 13, 14);
        addWarrior("Knight", "SwiftBlade", "Marshlander", 250, 18, 7, 12, 17);

        addWarrior("Mage", "Warlock", "Marshlander", 100, 12, 7, 10, 12);
        addWarrior("Mage", "Illusionist", "Mystic", 120, 13, 8, 12, 14);
        addWarrior("Mage", "Enchanter", "Highlander", 160, 16, 10, 13, 16);
        addWarrior("Mage", "Conjurer", "Highlander", 195, 18, 15, 14, 12);
        addWarrior("Mage", "Eldritch", "Mystic", 250, 18, 20, 17, 13);

        addWarrior("Healer", "Soother", "Sunchildren", 95, 10, 8, 9, 6);
        addWarrior("Healer", "Medic", "Highlander", 125, 12, 9, 12, 7);
        addWarrior("Healer", "Alchemist", "Marshlander", 150, 13, 13, 13, 13);
        addWarrior("Healer", "Saint", "Mystic", 200, 16, 14, 17, 9);
        addWarrior("Healer", "Lightbringer", "Sunchildren", 260, 17, 15, 19, 12);


        addWarrior("Mythical Creature", "Dragon", "Sunchildren", 120, 12, 14, 15, 8);
        addWarrior("Mythical Creature", "Basilisk", "Marshlander", 165, 15, 11, 10, 12);
        addWarrior("Mythical Creature", "Hydra", "Marshlander", 205, 12, 16, 15, 11);
        addWarrior("Mythical Creature", "Phoenix", "Sunchildren", 275, 17, 13, 17, 19);
        addWarrior("Mythical Creature", "Pegasus", "Mystic", 340, 14, 18, 20, 20);

        addEquipment("Armour", "Chainmail", 70, 0, 1, 0, -1);
        addEquipment("Armour", "Regalia", 105, 0, 1, 0, 0);
        addEquipment("Armour", "Fleece", 150, 0, 2, 1, -1);

        addEquipment("Artefact", "Excalibur", 150, 2, 0, 0, 0);
        addEquipment("Artefact", "Amulet", 200, 1, -1, 1, 1);
        addEquipment("Artefact", "Crystal", 210, 2, 1, -1, -1);
    }

    public Warrior getWarrior(String name) {
        for (Warrior w: warriors) {
            if (w.getName().equalsIgnoreCase(name)) {
                return w;
            }
        }
        return null;
    }
    public Equipment getEquipment(String name) {
        for (Equipment e: equipments) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        System.out.println("Equipment not found");
        return null;
    }


}
