import java.io.Serializable;

public class Asset implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    protected int price;
    protected double attack;
    protected double defence;
    protected double health;
    protected double speed;
    protected String type;


    public Asset(String type,String name, int price, double attack, double defence, double health, double speed) {
        this.name = name;
        this.price = price;
        this.attack = attack;
        this.defence = defence;
        this.health = health;
        this.speed = speed;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public double getAttack() {
        return attack;
    }
    public double getDefence() {
        return defence;
    }
    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }
}

class Warrior extends Asset implements Serializable {

    private Equipment armour;
    private Equipment artefact;
    private final String ethnicity;
    private static final long serialVersionUID = 1L;

    public Warrior(String type, String name, String ethnicity, int price, double attack, double defence, double health, double speed, Equipment armour, Equipment artefact) {
        super(type,name, price, attack, defence, health, speed);
        this.armour = armour;
        this.artefact = artefact;
        this.ethnicity = ethnicity;
    }

    public Warrior(Warrior value) {
        super(value.type,value.getName(), value.price, value.attack, value.defence, value.health, value.speed);
        this.ethnicity = value.ethnicity;
        this.armour = value.armour;
        this.artefact = value.artefact;
    }

    public void setArmour(Equipment armour) {
        if (this.armour != null) {
            this.price = this.price - (int) (this.armour.price * 0.2);
            this.attack = this.attack - this.armour.getAttack();
            this.defence = this.defence - this.armour.getDefence();
            this.health = this.health - this.armour.getHealth();
            this.speed = this.speed - this.armour.getSpeed();
        }
        this.price = this.price + (int) (armour.price * 0.2);
        this.armour = armour;
        this.attack = this.attack + armour.getAttack();
        this.defence = this.defence + armour.getDefence();
        this.health = this.health + armour.getHealth();
        this.speed = this.speed + armour.getSpeed();
    }

    public void setArtefact(Equipment artefact) {
        if (this.artefact != null) {
            this.price = this.price - (int) (this.artefact.price * 0.2);
            this.attack = this.attack - this.artefact.getAttack();
            this.defence = this.defence - this.artefact.getDefence();
            this.health = this.health - this.artefact.getHealth();
            this.speed = this.speed - this.artefact.getSpeed();
        }
        this.price = this.price + (int) (artefact.price * 0.2);
        this.artefact = artefact;
        this.attack = this.attack + artefact.getAttack();
        this.defence = this.defence + artefact.getDefence();
        this.health = this.health + artefact.getHealth();
        this.speed = this.speed + artefact.getSpeed();
    }

    @Override
    public String toString() {
        return getName() + " + " + this.armour + " + " + this.artefact;
    }

    public void setAttack(int i) {
        this.attack = i;
    }

    public void setDefence(int i) {
        this.defence = i;
    }

    public void setSpeed(int i) {
        this.speed = i;
    }

    public String get_ethnicity() {
        return ethnicity;
    }
}

class Equipment extends Asset implements Serializable {
    private static final long serialVersionUID = 1L;
    public Equipment(String type,String name, int price, double attack, double defence, double health, double speed) {
        super(type,name, price, attack, defence, health, speed);
    }

    public Equipment(Equipment value) {
        super(value.type,value.getName(), value.price, value.attack, value.defence, value.health, value.speed);
    }

    @Override
    public String toString() {
        return getName();
    }
}