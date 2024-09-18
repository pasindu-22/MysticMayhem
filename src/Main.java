import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Application app = new Application();
        app.loadPlayers();

        // Clear the console
//        clearConsole();


        Scanner scanner = new Scanner(System.in);
        System.out.printf("""
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                              Hello! Welcome to Mystic Mayhem      
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                                               [By Team Blue]
                """);

        int num;
        do {
            System.out.printf("""
                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    ____  ____                                        ____  ____
                    ____  ____                Mystic Mayhem           ____  ____
                    ____  ____                                        ____  ____
                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    --> 1.Go to your profile.
                    --> 2.Create a player profile.
                    --> 3.View all players.
                    --> 4.View credits.
                    --> 0.Exit.
                    """);
            num = scanner.nextInt();
            Thread.sleep(1000);
            clearConsole();
            // If user selects 1 go to profile.
            if (num==1) {
                System.out.print("Select ");
                Thread.sleep(200);
                System.out.print("your ");
                Thread.sleep(200);
                System.out.print("profile: ");
                Thread.sleep(200);
                System.out.println();
                Player p1=null;
                for (int i = 0; i < app.getPlayers().size(); i++) {
                    System.out.println("   --->>> " + app.getPlayers().get(i).getName());
                    System.out.println();
                    System.out.println("1.Select            2.Skip");
                    int x = scanner.nextInt();
                    if (x==1) {
                        System.out.println("Welcome " + app.getPlayers().get(i).getName() + "!");
                        System.out.println();
                        Thread.sleep(2000);
                        p1 = app.getPlayers().get(i);
                        clearConsole();
                        break;
                    }
                }

                int n;
                do {
                    // If no player is selected
                    if (p1==null){
                        System.out.println("Please select your profile to continue!");
                        break;
                    }
                    System.out.println( p1.getName() + " ||   XP : " + p1.getXp() + "  ||  Wallet : " + p1.getWallet());
                    System.out.printf("""
                                1. Start a Battle
                                2. Buy Warrior
                                3. Buy Equipment
                                4. View Profile
                                0. Exit to Main Menu
                           """);
                    n = scanner.nextInt();
                    clearConsole();
                    if (n==1) {
                            System.out.println("Select your opponent:");
                            System.out.println();
                            Player p2 = null;
                            for (int i = 0; i < app.getPlayers().size(); i++) {
                                Player player = app.getPlayers().get(i);
                                if (player.getName().equalsIgnoreCase(p1.getName())) {
                                    continue;
                                }
                                System.out.println("--->>>" + player.getName() + "  ||  XP : " + player.getXp());
                                System.out.println();
                                System.out.println(" 1.Select            2.Skip");
                                int y = scanner.nextInt();
                                if (y == 1) {
                                    p2 = player;
                                    break;
                                }
                            }
                            // If no opponent is selected
                            if (p2 == null) {
                                System.out.println("No opponent selected!");
                                Thread.sleep(2000);
                                continue;
                            }
                            // Battle
                            clearConsole();
                            app.battle(p1, p2);
                            String Q;
                            do {
                                System.out.println("Press Q to exit!");
                                Q = scanner.next();
                                Thread.sleep(1000);
                                if (Q.equalsIgnoreCase("q")) {
                                    break;
                                }
                                Thread.sleep(5000);
                            } while (true);

                    } else if (n==2) {                  // Buy Warrior
                        Store.displayWarriors();
                        System.out.println("Enter name of the warrior: ");
                        String name;
                        try {
                            name = scanner.next();
                            app.buyWarrior(p1.getName(), name);
                        } catch (NullPointerException e) {
                            System.out.println("Warrior not found!");
                            Thread.sleep(2000);
                            continue;
                        }
                        System.out.print(" --->");
                        System.out.println(name + " added to " + p1.getName());
                        Thread.sleep(2000);
                    } else if (n==3) {                  // Buy Equipment
                        Store.displayEquipments();
                        System.out.println("Enter equipment name: ");
                        String equipment = scanner.next();
                        String warrior = null;
                        for (Warrior w : p1.getArmy().values()) {
                            System.out.println(w.getName());
                            System.out.println(" 1.Select            2.Skip");
                            if (scanner.nextInt() == 1) {
                                warrior = w.getName();
                                break;
                            }
                        }
                        // If no warrior is selected
                        if (warrior == null) {
                            System.out.println("No warrior selected!");
                            Thread.sleep(2000);
                            continue;
                        }
                        // Buy equipment
                        try {
                            app.buyEquipment(p1.getName(), warrior, equipment);
                        } catch (NullPointerException e) {
                            Thread.sleep(2000);
                            continue;
                        }
                        System.out.println(" --->" + equipment + " added to " + warrior);
                        Thread.sleep(2000);
                    } else if (n==4){
                        p1.printPlayer();
                        String Q;
                        do {
                            System.out.println("Press Q to exit!");
                            Q = scanner.next();
                            Thread.sleep(1000);
                            if (Q.equalsIgnoreCase("q")) {
                                break;
                            }
                            Thread.sleep(5000);
                        } while (true);
                    } else if (n==0) {                      // Exit
                        System.out.println("Good Bye " + p1.getName() + "!");
                    }
                    // Clear the console
                    clearConsole();
                } while (n!=0);

                // Clear the console
                clearConsole();

            // If user selects 2 create a player profile
            }else if (num==2) {
                System.out.println("Enter name: ");
                String name = scanner.next();
                System.out.println("Enter a unique username: ");
                String userName = scanner.next();
                System.out.println("Enter Home Ground: ");
                System.out.println("--->>> Home Grounds : |Marshland|, |Hillcrest|, |Desert|, |Arcane|");
                String homeGround = scanner.next();
                app.addPlayer(name, userName,homeGround);
                System.out.println();
                System.out.println("Please Select a default army before battle");
                Player p = app.getPlayers().get(app.getPlayers().size()-1);
                System.out.println("1. Shooter , Squire , Warlock , Soother , Dragon");
                System.out.println("2. Shooter , Squire , Illusionist , Soother , Dragon");
                if (scanner.nextInt()==1) {
                    Application.setDefaultArmy1(p);
                }else {
                    Application.setDefaultArmy2(p);
                }
                System.out.println("Please go to your profile!");
                Thread.sleep(2000);
            } else if (num==3) {                // View all players
                System.out.println("Players: ");
                app.getPlayers().forEach(p -> System.out.println(p!=null?p.getName():""));
                Thread.sleep(2000);
            } else if (num==4) {
                System.out.println("Credits: ");
                Thread.sleep(500);
                System.out.println("Developed by : ");
                Thread.sleep(500);
                System.out.println("1. Osanda Samarathunga");
                Thread.sleep(500);
                System.out.println("2. Chehan Rupasinghe");
                Thread.sleep(500);
                System.out.println("3. Pasindu Sathsara");
                Thread.sleep(500);
                System.out.println("4. Ravindu Sandeep");
                Thread.sleep(500);
                System.out.println("[2024 Team Blue Cooperation]");
                Thread.sleep(5000);
            } else if (num==0) {                // Exit
                System.out.println("Good Bye!");
                app.savePlayers();
                Thread.sleep(1500);
            }

            // Clear the console
            clearConsole();
        } while (num!=0);
    }

    private static void clearConsole() {
        try {
                final String os = System.getProperty("os.name");

                if (os.contains("Windows")) {
                    // For Windows
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    // For Linux and macOS
                    Runtime.getRuntime().exec("clear");
                }
            } catch (Exception e) {
                System.out.println("Error while clearing the console: " + e.getMessage());
            }
    }
}
