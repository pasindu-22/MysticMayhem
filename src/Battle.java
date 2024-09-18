import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Battle
{
    protected int turn;

    protected HashMap<String, Warrior> player1;
    protected HashMap<String, Warrior> player2;

    protected ArrayList<Warrior> army1_attack;
    protected ArrayList<Warrior> army1_defend;

    protected ArrayList<Warrior> army2_attack;
    protected ArrayList<Warrior> army2_defend;

    static Map<String, Integer> attack_priority = new HashMap<>();
    static Map<String, Integer> defend_priority = new HashMap<>();

    static
    {
        attack_priority.put("Archer", 1);
        attack_priority.put("Knight", 2);
        attack_priority.put("MythicalCreature", 3);
        attack_priority.put("Mage", 4);
        attack_priority.put("Healer", 5);

        defend_priority.put("Healer", 1);
        defend_priority.put("MythicalCreature", 2);
        defend_priority.put("Archer", 3);
        defend_priority.put("Knight", 4);
        defend_priority.put("Mage", 5);
    }

    public Battle(Player player1, Player player2)
    {
        this.turn = 1;
        this.player1 = new HashMap<>(player1.getArmy());
        this.player2 = new HashMap<>(player2.getArmy());
    };

    public void battleGround(String homeground)
    {

    }

    public void setup_battle(){};

    public void fight()
    {
        Warrior attacker;
        Warrior defender;

        while (this.turn <= 10)
        {
            if (army1_defend.isEmpty())
            {
                break;
            }
            else if (army2_defend.isEmpty())
            {
                break;
            }
            else
            {
                // player 1 attacks first
                attacker = army1_attack.get(0);
                if (attacker.getType().equalsIgnoreCase("Healer"))
                {
                    Warrior lowhealthWarrior = get_lowesthealth(this.player1);
                    lowhealthWarrior.setHealth(0.1 * attacker.getAttack());
                }

                else
                {
                    defender = army2_defend.get(0);
                    defender.setHealth((0.5 * attacker.getAttack() - 0.1 * defender.getDefence()));

                    if (defender.getHealth() > 0)
                    {
                        continue;
                    }

                    else
                    {
                        player2.remove(defender.getType());
                        army2_defend.remove(defender);
                        army2_attack.remove(defender);
                        army2_attack = attack_lineup(player2);
                        army2_defend = defend_lineup(player2);
                    }
                }

                // then player 2 get their turn
                attacker = army2_attack.get(0);
                if (attacker.getType().equalsIgnoreCase("Healer"))
                {
                    Warrior lowhealthWarrior = get_lowesthealth(this.player2);
                    lowhealthWarrior.setHealth(0.1 * attacker.getAttack());
                }

                else
                {
                    defender = army1_defend.get(0);
                    defender.setHealth((0.5 * attacker.getAttack() - 0.1 * defender.getDefence()));

                    if (defender.getHealth() > 0)
                    {
                        continue;
                    }

                    else
                    {
                        player1.remove(defender.getType());
                        army1_defend.remove(defender);
                        army1_attack.remove(defender);
                        army1_attack = attack_lineup(player1);
                        army1_defend = defend_lineup(player1);
                    }
                }
            }
        }
    }


    public static Warrior get_lowesthealth(HashMap<String, Warrior> army)
    {
        ArrayList<Warrior> warriors = new ArrayList<>(army.values());
        warriors.sort(Comparator.comparing(Warrior::getHealth));
        return warriors.get(0);
    }

    public static ArrayList<Warrior> attack_lineup(HashMap<String, Warrior> army)
    {
        ArrayList<Warrior> attackers = new ArrayList<>(army.values());

        attackers.sort(Comparator.comparing(Warrior::getSpeed)
                .reversed()
                .thenComparing(w -> attack_priority.get(w.getType())));

        return attackers;
    };

    public static ArrayList<Warrior> defend_lineup(HashMap<String, Warrior> army)
    {
        ArrayList<Warrior> defenders = new ArrayList<>(army.values());

        defenders.sort(Comparator.comparing(Warrior::getDefence)
                .reversed()
                .thenComparing(w -> defend_priority.get(w.getType())));

        return defenders;
    };

}

class Hillcrest_Battle extends Battle
{
    public Hillcrest_Battle(Player player1, Player player2)
    {
        super(player2, player2);
    }

    @Override
    public void setup_battle()
    {
        // homeground advantages to the warriors.
        for (Warrior w: player1.values())
        {
            switch (w.get_ethnicity())
            {
                case "Highlander":
                    w.setAttack(1);
                    w.setDefence(1);
                    break;

                case "Marshlander":
                    w.setSpeed(-1);
                    break;

                case "Sunchildren":
                    w.setSpeed(-1);
                    break;
            }
        }
        for (Warrior w: player2.values())
        {
            switch (w.get_ethnicity())
            {
                case "Highlander":
                    w.setAttack(1);
                    w.setDefence(1);
                    break;

                case "Marshlander":
                    w.setSpeed(-1);
                    break;

                case "Sunchildren":
                    w.setSpeed(-1);
                    break;
            }
        }

        // organize the attacking and defending line ups.
        army1_attack = attack_lineup(player1);
        army1_defend = defend_lineup(player1);

        army2_attack = attack_lineup(player2);
        army2_defend = defend_lineup(player2);
    }

    @Override
    public void fight() {
        Warrior attacker;
        Warrior defender;

        while (this.turn <= 10)
        {
            if (army1_defend.isEmpty())
            {
                break;
            }
            else if (army2_defend.isEmpty())
            {
                break;
            }
            else
            {
                // player 1 attack first
                attacker = army1_attack.get(0);
                if (attacker.getType().equalsIgnoreCase("Healer"))
                {
                    Warrior lowhealthWarrior = get_lowesthealth(this.player1);
                    lowhealthWarrior.setHealth(0.1 * attacker.getAttack());
                }

                else
                {
                    defender = army2_defend.get(0);
                    defender.setHealth((0.5 * attacker.getAttack() - 0.1 * defender.getDefence()));

                    if (defender.getHealth() > 0)
                    {
                        continue;
                    }

                    else
                    {
                        player2.remove(defender.getType());
                        army2_defend.remove(defender);
                        army2_attack.remove(defender);
                        army2_attack = attack_lineup(player2);
                        army2_defend = defend_lineup(player2);
                    }
                }
                // give a bonus turn for highlanders with 0.2 of there attack.
                bonus_turn(attacker, army2_defend.get(0));

                // then player 2 get their turn
                attacker = army2_attack.get(0);
                if (attacker.getType().equalsIgnoreCase("Healer"))
                {
                    Warrior lowhealthWarrior = get_lowesthealth(this.player2);
                    lowhealthWarrior.setHealth(0.1 * attacker.getAttack());
                }

                else
                {
                    defender = army1_defend.get(0);
                    defender.setHealth((0.5 * attacker.getAttack() - 0.1 * defender.getDefence()));

                    if (defender.getHealth() > 0)
                    {
                        continue;
                    }

                    else
                    {
                        player1.remove(defender.getType());
                        army1_defend.remove(defender);
                        army1_attack.remove(defender);
                        army1_attack = attack_lineup(player1);
                        army1_defend = defend_lineup(player1);
                    }
                }
                bonus_turn(attacker, army1_defend.get(0));
            }
        }
    }

    public void bonus_turn(Warrior attacker, Warrior defender)
    {
        if (attacker.getType().equalsIgnoreCase("Highlander"))
        {
            if (attacker.getType().equalsIgnoreCase("Healer"))
            {
                Warrior lowhealthWarrior = get_lowesthealth(this.player1);
                lowhealthWarrior.setHealth(0.1 * (attacker.getAttack() * 0.2));
            }

            else
            {
                defender = army2_defend.get(0);
                defender.setHealth((0.5 * (attacker.getAttack() * 0.2) - 0.1 * defender.getDefence()));

                if (defender.getHealth() > 0)
                {
                    System.out.println(" ");
                }

                else
                {
                    player2.remove(defender.getType());
                    army2_defend.remove(defender);
                    army2_attack.remove(defender);
                    army2_attack = attack_lineup(player2);
                    army2_defend = defend_lineup(player2);
                }
            }
        }
    }



}