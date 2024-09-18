import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Application {
    private ArrayList<Player> players = new ArrayList<>();
    private final Map<String,Warrior> defaultArmy1 = new HashMap<>(5);
    private final Map<String,Warrior> defaultArmy2 = new HashMap<>(5);

    public Application() {
        Store store = Store.getInstance();
        Store.createStore();
        Player p1 = Player.createPlayer("GeraltOfRivia", "whitewolf","Arcane",2000,32);
        p1.buyWarrior("Ranger");
        p1.buyWarrior("Squire");
        p1.buyWarrior("Warlock");
        p1.buyWarrior("Medic");
        p1.buyWarrior("Dragon");
        p1.buyEquipment("Ranger", "Chainmail");
        p1.buyEquipment("Medic","Amulet");
        p1.setWallet(215);
        players.add(p1);

        Player p2 = Player.createPlayer("Barbarian", "clasher","Hillcrest",1000,25);
        p2.buyWarrior("Shooter");
        p2.buyWarrior("Cavalier");
        p2.buyWarrior("Warlock");
        p2.buyWarrior("Saint");
        p2.buyWarrior("Dragon");
        p2.buyEquipment("Warlock", "Chainmail");
        p2.buyEquipment("Saint","Amulet");
        p2.setWallet(285);
        players.add(p2);
    }

    public void addPlayer(String name, String userName,String homeGround) {
        players.add(Player.createPlayer(name, userName,homeGround));
    }

    public void buyWarrior(String pName, String name) {
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(pName)) {
                p.buyWarrior(name);
                break;
            }
        }
    }

    public void buyEquipment(String pName, String warrior, String equipment) {
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(pName)) {
                p.buyEquipment(warrior, equipment);
                break;
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void battle(Player p1, Player p2) throws InterruptedException {
        List<String> types = Arrays.asList("Archer", "Knight", "Mythical Creature", "Mage", "Healer");

        // Check if the army map of both players contains all types of warriors
        if (!p1.getArmy().keySet().containsAll(types) || !p2.getArmy().keySet().containsAll(types)) {
            System.out.println("Both players must have all types of warriors for a battle!");
            return;
        }
        System.out.println(p1.getName() + " is Challenging " + p2.getName());

        // Create a copy of the players' armies
        HashMap<String, Warrior> army1 = new HashMap<>();
        for (Map.Entry<String, Warrior> entry : p1.getArmy().entrySet()) {
            army1.put(entry.getKey(), new Warrior(entry.getValue()));
        }
        HashMap<String, Warrior> army2 = new HashMap<>();
        for (Map.Entry<String, Warrior> entry : p2.getArmy().entrySet()) {
            army2.put(entry.getKey(), new Warrior(entry.getValue()));
        }
        // Set the ground
        String ground = p2.getHomeGround();
        if (ground.equalsIgnoreCase("Hillcrest")) {
            System.out.println("Battle in Hillcrest");
            setHillcrest(army1);
            setHillcrest(army2);
        }
        else if (ground.equalsIgnoreCase("Marshland")) {
            System.out.println("Battle in Marshland");
            setMarshland(army1);
            setMarshland(army2);
        } else if (ground.equalsIgnoreCase("Desert")) {
            System.out.println("Battle in Desert");
            setDesert(army1);
            setDesert(army2);
        } else if (ground.equalsIgnoreCase("Arcane")) {
            System.out.println("Battle in Arcane");
            setArcane(army1);
            setArcane(army2);
        }

        // Battle
        for (int i = 1; i < 11; i++) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            if (army1.isEmpty()) {
                winPlayer(p1, p2);
                break;
            } else if (army2.isEmpty()) {
                winPlayer(p2, p1);
                break;
            } else {
                System.out.println("Turn " + i + ": " + p1.getName());
                // Attack by challenger
                fight(army1, army2,ground);

            }
            if (army1.isEmpty()) {
                winPlayer(p1, p2);
                break;
            } else if (army2.isEmpty()) {
                winPlayer(p2, p1);
                break;
            } else {
                System.out.println("Turn " + i + ": " + p2.getName());
                // Attack by defender
                fight(army2, army1,ground);
            }
            if (army1.isEmpty()) {
                winPlayer(p1, p2);
                break;
            } else if (army2.isEmpty()) {
                winPlayer(p2, p1);
                break;
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        if (!army1.isEmpty() && !army2.isEmpty()) {
            System.out.println("-------------");
            System.out.println("Battle draw!");
            System.out.println("-------------");
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println(p1.getName() + " XP:" + p1.getXp() + " gold coins: " + p1.getWallet());
        System.out.println(p2.getName() + " XP:" + p2.getXp() + " gold coins: " + p2.getWallet());
        System.out.println("------------------------------------------------------------------------");

    }

    // Method to declare the winner and reward the winner
    private void winPlayer(Player p1, Player p2) {
        System.out.println("Congratulations!!! " + p2.getName() + " wins!");
        p2.setXp(1);
        int fine = p1.getWallet()-p1.getWallet() * 0.2>=0? (int)(p1.getWallet() * 0.2): p1.getWallet();
        p2.setWallet(p2.getWallet() +fine);
        p1.setWallet(p1.getWallet()-fine);
    }

    // Method to simulate the battle
    private void fight(HashMap<String, Warrior> army1, HashMap<String, Warrior> army2,String ground) throws InterruptedException {
        Warrior w1 = army1.get(attack_lineup(army1));           // Attacker
        Warrior w2;
        double health1 = w1.getHealth();
        double health2;
        boolean bonus = false;

        // Check if the attacker is a healer
        if (w1.getType().equalsIgnoreCase("healer") && army1.size()>1) {   // Healer's turn
            w2 = army1.get(getLowestHealth(army1));
            System.out.println(w1.getName() + " heals " + w2.getName());
            Thread.sleep(800);
            health2 = w2.getHealth() + (0.1 * w1.getAttack());
            checkHealth(army1, w1, w2, health1, health2);

            // Get next patient for bonus turn.
            w2 = army1.get(getLowestHealth(army1));

            // check if the healer's bonus turn is available
            if (w1.get_ethnicity().equalsIgnoreCase("Highlander") && ground.equalsIgnoreCase("Hillcrest")) {
                System.out.println();
                System.out.println("Bonus turn");
                System.out.println();
                System.out.println(w1.getName() + " heals " + w2.getName());
                Thread.sleep(800);
                bonus = true;
                health2 = w2.getHealth() + (0.1 * w1.getAttack() * 1.2);
            } else if (w1.get_ethnicity().equalsIgnoreCase("Mystic") && ground.equalsIgnoreCase("Arcane")) {
                System.out.println();
                System.out.println("Bonus turn");
                System.out.println();
                System.out.println(w1.getName() + " heals " + w2.getName());
                Thread.sleep(800);
                bonus = true;
                w1.setHealth(w1.getHealth()*1.1);
            }
            if (bonus) {            // If there was a bonus turn, set player/warrior properties.
                checkHealth(army1, w1, w2, health1, health2);
            }
        } else if(!(w1.getType().equalsIgnoreCase("healer"))) {        // Other warriors' turn
            // Attack if the attacker is not a healer
            w2 = army2.get(defend_lineup(army2));
            System.out.println(w1.getName() + " attacks " + w2.getName());
            Thread.sleep(800);
            health2 = w2.getHealth() - (0.5 * w1.getAttack() - 0.1 * w2.getDefence());
            checkHealth(army2, w1, w2, health1, health2);  // Setting health values.

            //Get next defender for bonus turn.
            w2 = army2.isEmpty()?null:army2.get(defend_lineup(army2));
            // check if the attacker's bonus turn is available
            if (!(army2.isEmpty())) {
                if (w1.get_ethnicity().equalsIgnoreCase("Highlander") && ground.equalsIgnoreCase("Hillcrest")) {
                    System.out.println();
                    System.out.println("Bonus turn");
                    System.out.println();
                    Thread.sleep(500);
                    System.out.println(w1.getName() + " attacks " + w2.getName());
                    Thread.sleep(1000);
                    bonus = true;
                    health2 = w2.getHealth() - (0.5 * w1.getAttack() * 1.2);
                } else if (w1.get_ethnicity().equalsIgnoreCase("Mystic") && ground.equalsIgnoreCase("Arcane")) {
                    System.out.println();
                    System.out.println("Bonus turn");
                    System.out.println();
                    Thread.sleep(500);
                    System.out.println(w1.getName() + " attacks " + w2.getName());
                    Thread.sleep(800);
                    bonus = true;
                    w1.setHealth(w1.getHealth() * 1.1);
                }
                if (bonus) {            // If there was a bonus turn, set player/warrior properties.
                    checkHealth(army2, w1, w2, health1, health2);
                }
            }
        }
        System.out.println();
        System.out.println();
    }

    // Method to check the health of the warriors and remove the dead warriors
    private void checkHealth(HashMap<String, Warrior> army, Warrior w1, Warrior w2, double health1, double health2) throws InterruptedException {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String roundedNumber1 = decimalFormat.format(health1);
        String roundedNumber2 = decimalFormat.format(health2);

        double h1 = Double.parseDouble(roundedNumber1);
        double h2 = Double.parseDouble(roundedNumber2);

        if (health2 > 0) {
            army.get(w2.getType()).setHealth(h2);
            System.out.println(w2.getName() + "'s health: " + h2);
            Thread.sleep(200);
            System.out.println(w1.getName() + "'s health: " + h1);
            Thread.sleep(200);
        } else {
            army.get(w2.getType()).setHealth(0);
            System.out.println(w2.getName() + "'s health: " + 0);
            Thread.sleep(200);
            System.out.println(w1.getName() + "'s health: " + h1);
            Thread.sleep(200);
            System.out.println(w2.getName() + " died!");
            Thread.sleep(500);
            army.remove(w2.getType());
        }
    }

    // Setting up ground environments.
    public void setHillcrest(HashMap<String, Warrior> army) {
        for (Warrior w : army.values()) {
            switch (w.get_ethnicity().toLowerCase()) {
                case "highlander":
                    w.setAttack((int)w.getAttack() + 1);
                    w.setDefence((int)w.getDefence() + 1);
                case "marshlander":
                    w.setSpeed((int)w.getSpeed() - 1);
                case "sunchildren":
                    w.setSpeed((int)w.getSpeed() - 1);

            }
        }
    }

    public void setMarshland(HashMap<String, Warrior> army) {
        for (Warrior w : army.values()) {
            switch (w.get_ethnicity().toLowerCase()) {
                case "marshlander":
                    w.setDefence((int) w.getDefence() + 2);
                    return;
                case "sunchildren":
                    w.setAttack((int) w.getAttack() - 1);
                    return;
                case "mystic":
                    w.setSpeed((int) w.getSpeed() - 1);
                    return;
            }
        }
    }
    public void setDesert(HashMap<String, Warrior> army) {
        for (Warrior w : army.values()) {
            switch (w.get_ethnicity().toLowerCase()) {
                case "marshlander":
                    w.setHealth((int) w.getHealth() - 1);
                    return;
                case "sunchildren":
                    w.setAttack((int) w.getAttack() + 1);
                    return;

            }
        }
    }
    public void setArcane(HashMap<String, Warrior> army) {
        for (Warrior w : army.values()) {
            switch (w.get_ethnicity().toLowerCase()) {
                case "mystic":
                    w.setAttack((int) w.getAttack() + 2);
                    return;
                case "highlander", "marshlander":
                    w.setSpeed((int) w.getSpeed() - 1);
                    w.setDefence((int) w.getDefence() - 1);
                    return;
            }
        }
    }

    // Getting Attack priorities according to constraints.
    static Map<String, Integer> attack_priority = new HashMap<>();
    static Map<String, Integer> defend_priority = new HashMap<>();

    static {
        attack_priority.put("Archer", 1);
        attack_priority.put("Knight", 2);
        attack_priority.put("Mythical Creature", 3);
        attack_priority.put("Mage", 4);
        attack_priority.put("Healer", 5);

        defend_priority.put("Healer", 1);
        defend_priority.put("Mythical Creature", 2);
        defend_priority.put("Archer", 3);
        defend_priority.put("Knight", 4);
        defend_priority.put("Mage", 5);
    }

    public String getLowestHealth(HashMap<String, Warrior> army) {
        ArrayList<Warrior> warriors = new ArrayList<>();
        for (String a : army.keySet()) {
            if (a.equalsIgnoreCase("Healer")) {
                continue;
            } else {
                warriors.add(new Warrior(army.get(a)));
            }
        }

        warriors.sort(Comparator.comparingDouble(Warrior::getHealth));
        return warriors.get(0).getType();
    }

    public static String attack_lineup(HashMap<String, Warrior> army) {
        ArrayList<Warrior> attackers = new ArrayList<>(army.values());

        attackers.sort(Comparator.comparing(Warrior::getSpeed)
                .reversed()
                .thenComparing(w -> attack_priority.get(w.getType())));

        return attackers.get(0).getType();
    }


    public static String defend_lineup(HashMap<String, Warrior> army) {
        ArrayList<Warrior> defenders = new ArrayList<>(army.values());

        defenders.sort(Comparator.comparing(Warrior::getDefence)
                .thenComparing(w -> defend_priority.get(w.getType())));

        return defenders.get(0).getType();
    }


    // Save players.
    public void savePlayers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("players.dat"))) {
            oos.writeObject(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load players.
    @SuppressWarnings("unchecked")
    public void loadPlayers() throws InterruptedException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("players.dat"))) {
            players = (ArrayList<Player>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved players found.");
        } catch (IOException e){
            System.out.println("An error occurred while reading saved players");
        } catch (ClassNotFoundException e) {
            System.out.println("The saved players are not compatible");
        }
    }

    public static void setDefaultArmy1(Player p) {
        p.buyWarrior("Shooter");
        p.buyWarrior("Squire");
        p.buyWarrior("Warlock");
        p.buyWarrior("Soother");
        p.buyWarrior("Dragon");
    }
    public static void setDefaultArmy2(Player p) {
        p.buyWarrior("Shooter");
        p.buyWarrior("Squire");
        p.buyWarrior("Illusionist");
        p.buyWarrior("Soother");
        p.buyWarrior("Dragon");
    }
}

