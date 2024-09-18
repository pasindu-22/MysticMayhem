import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private final String userName;
    private final int userId;
    private int wallet;
    private int xp=0;
    private static int playerCount = 0;
    private static final ArrayList<String> userNames = new ArrayList<>() ;
    private Map<String,Warrior> army = new HashMap<>(5);
    private final Store store = Store.getInstance();
    private String homeGround;

    public Player(String name, String userName,String homeGround, int userId, int wallet, int xp) {
        this.name = name;
        this.userName = userName;
        this.userId = userId;
        this.wallet = wallet;
        this.xp = xp;
        this.homeGround = homeGround;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp += xp;
    }

    public int getWallet() {
        return wallet;
    }
    public void setWallet(int amount) {
        this.wallet = amount;
    }

    public String getName() {
        return name;
    }

    public Map<String, Warrior> getArmy() {
        return army;
    }

    public String getHomeGround() {
        return homeGround;
    }

    public void setHomeGround(String homeGround) {
        this.homeGround = homeGround;
    }

    public static Player createPlayer(String name, String userName,String homeGround) {
        if (userNames.contains(userName)) {
            System.out.println("Username already taken!,try a different one.");
            return null;
        } else {
            System.out.println(name + " added to the game!");
            userNames.add(userName);
            playerCount++;
            int id = 10000 + playerCount;
            return new Player(name,userName,homeGround,id,500,0);
        }
    }
    public static Player createPlayer(String name, String userName,String homeGround, int wallet, int xp) {
        if (userNames.contains(userName)) {
            System.out.println("Username already taken!,try a different one.");
            return null;
        } else {
            System.out.println(name + " added to the game!");
            userNames.add(userName);
            playerCount++;
            int id = 10000 + playerCount;
            return new Player(name,userName,homeGround,id,wallet,xp);
        }
    }

    public void buyWarrior(String name){
        Warrior warrior = new Warrior(Store.getInstance().getWarrior(name));
        if (warrior==null) {
            throw new NullPointerException("Warrior " + name + " not found in the store!");
        }
        String type = warrior.getType();
        if (army.containsKey(type)) {               // If the player already has a warrior of the same type
            Warrior toSell = army.get(type);
            wallet = wallet + (int) ((toSell.price)*0.9);
            if (wallet>=warrior.price) {
                wallet = wallet - warrior.price;
                army.replace(type,warrior);
            }
        } else if(wallet>=warrior.price) {          // If the player does not have a warrior of the same type
            wallet = wallet - warrior.price;
            army.put(type,warrior);
        }
    }

    public void buyEquipment(String warrior,String equipment) {
        Equipment temp = Store.getInstance().getEquipment(equipment);
        if (temp==null) {
            throw new NullPointerException("Equipment " + equipment + " not found in the store!");
        }
        Equipment e = new Equipment(temp);

        if (wallet >= e.price) {            // If the player has enough money
            for (Warrior w : army.values()) {           // If the player has the warrior
                if (w.getName().equalsIgnoreCase(warrior)) {
                    if (e.type.equalsIgnoreCase("armour")) {
                        w.setArmour(e);
                    } else if (e.type.equalsIgnoreCase("artefact")) {
                        w.setArtefact(e);
                    }
                    wallet = wallet - e.price;
                    break;
                }
            }
        }
    }

    public void printPlayer() {
        System.out.println("Name: " + name + "\nusername: " + userName
                +"\nUser Id: " + userId
                + "\nWallet: " + wallet
                + "\nXP: " + xp
                + "\nArmy: " + army.toString());
    }





}


