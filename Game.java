import java.util.*;
import java.io.*;

/**
* Main class of the program. Contains methods to read exits.txt and simulate a
* series of rounds for a player to navigate, choosing rooms/portals to find exit while
* avoiding magical police. Writes outcome of game to Outcome.txt.
* @author William Rosenboom
* @version 1.0
*/
public class Game
{
    public static final String FILE_NAME = "exits.txt";
    private static String[] direction;
    private static double[][] probabilities;
    private boolean foundExit;
    private boolean policeEncounter;
    private boolean noPortalsAvailable;

    /**
    * Getter method for probabilities, a 2d array to store each
    * probability contained in exits.txt in the appropriate row/direction.
    */
    public double[][] getProbabilities()
    {
        return probabilities;
    }

    /**
    * Getter method to store the name of the direction in exits.txt.
    */
    public String[] getDirection()
    {
        return direction;
    }

    /**
    * Returns true if exit is found.
    */
    public boolean isFoundExit()
    {
        return foundExit;
    }

    /**
    * Simulates a round of the game. Player chooses direction and follows
    * state of game
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param exit The object of the Exit class
    * @param police The object of the Magic Police class
    * @param items The object of the Item class
    */
    public void playRound(Player player, Room portals, Exit exit, MagicPolice police, Items items)
    {
        while(!(player.isGameWon() || player.isCaughtByPolice() || player.isGameEnd()))
        {
            int selectedIndex = portals.choosePortalDirection();
            noPortalsAvailable = selectedIndex == -1;
            if (selectedIndex != -1)
            {
                policeEncounter = police.checkForPoliceEncounter(selectedIndex);
                playRoundWithPortals(player, portals, exit, police, items, selectedIndex);
                if (policeEncounter)
                {
                    handlePoliceEncounters(player, portals, selectedIndex, police, items);
                }
            }
            else
            {
                playRoundWithoutPortals(player, portals, selectedIndex, police, items);
            }
        }
    }

    /**
    * Handles what happens when portals are available. Allows player to choose an
    * available portal direction and ends game by writing to Outcome.txt if
    * player finds an exit.
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param exit The object of the Exit class
    * @param police The object of the Magic Police class
    * @param items The object of the Item class
    * @param selectedIndex The number representing the players chosen direction
    */
    public void playRoundWithPortals(Player player, Room portals, Exit exit, MagicPolice police, Items items, int selectedIndex)
    {
        foundExit = exit.checkForExit(selectedIndex);
        policeEncounter = police.checkForPoliceEncounter(selectedIndex);
        if (foundExit)
        {
            try
            {
                PrintWriter writer = new PrintWriter(new File("Outcome.txt"));
                player.setGameWon(true);
                System.out.println("Game concluded! See 'Outcome.txt' file for result.");
                String outcome = "Congratulations! You found an exit and escaped Javalice!";
                writer.println(outcome);
                writer.close();
            }
            catch (IOException e)
            {
                System.out.println("Error in writing to file! Exiting...");
            } 
        }
        else
        {
            exit.updateExitChance(selectedIndex);
            police.updatePoliceEncounterChance(selectedIndex);
            portals.generatePortals();
            items.findMagicBox();
        }
    }
    
    /**
    * Handles logic of when no portal is available, depending on if player has
    * any jumps remaining or not.
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param selectedIndex The number representing the players chosen direction
    * @param police The object of the Magic Police class
    * @param items The object of the Item class
    */
    public void playRoundWithoutPortals(Player player, Room portals, int selectedIndex, MagicPolice police, Items items)
    {
        if (player.getJumps() > 0)
        {
            handleNoPortalAndPoliceEncounters(player, portals, selectedIndex, police, items, policeEncounter);
        }
        else
        {
            jumps(player, portals, selectedIndex);
        }
    }
    /**
    * Handles player actions if no portals are available
    * and what happens if magic police are encountered.
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param selectedIndex The number representing the players chosen direction
    * @param police The object of the Magic Police class
    * @param items The object of the Item class
    * @param policeEncounter The boolean representing if magic police have been
    * encountered or not
    */
    public void handleNoPortalAndPoliceEncounters(Player player, Room portals, int selectedIndex, MagicPolice police, Items items, boolean policeEncounter)
    {
        if (noPortalsAvailable)
        {
               System.out.println("No portals available!");
                if (!(jumps(player, portals, selectedIndex)))
                {
                    return;
                }
        }
        if (policeEncounter)
        {
            handlePoliceEncounters(player, portals, selectedIndex, police, items);
        }
    }
    /**
    * Handles players actions they can take if caught by the magic police
    * depending on the state of the game at that time
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param selectedIndex The number representing the players chosen direction
    * @param police The object of the Magic Police class
    * @param items The object of the Item class
    */
    public void handlePoliceEncounters(Player player, Room portals, int selectedIndex, MagicPolice police, Items items)
    {
        Scanner console = new Scanner(System.in);
        System.out.println("You have been caught by the magic police!");
        if (items.getInventorySize() > 0)
        {
            System.out.println("Do you want to use an invisibility cloak? (yes/no)");
            String cloakChoice = console.nextLine().trim().toLowerCase();
            if (cloakChoice.equals("yes") || cloakChoice.equals("y"))
            {
                policeEncounter = false;
                items.useInvisibilityCloak();
                System.out.println("You have used a cloak and hidden from the magic police!");
                portals.generatePortals();
                selectedIndex = portals.choosePortalDirection();
            }
            else
            {
                bribePolice(player, portals, selectedIndex, police, items);
            }
        }
        else
        {
            bribePolice(player, portals, selectedIndex, police, items);
        }
    }
    /**
    * Allows player option to bribe magic police if nothing stored in inventory
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param selectedIndex The number representing the players chosen direction
    * @param police The object of the Magic Police class
    * @param items The object of the Item class
    */
    public void bribePolice(Player player, Room portals, int selectedIndex, MagicPolice police, Items items)
    {
        Scanner console = new Scanner(System.in);
        System.out.println("You don't have any invisibility cloaks in your inventory!");
        System.out.println("Do you want to bribe the magic police? (yes/no)");
        String bribeChoice = console.nextLine().trim().toLowerCase();
        if (bribeChoice.equals("yes") || bribeChoice.equals("y"))
        {
            bribeAmount(player, portals, selectedIndex, police, items);
        }
        else
        {
            System.out.println("You have chosen not to bribe the magic police and been sent to jail!");
            jumps(player, portals, selectedIndex);
        }
    }

    /**
    * Determines bribe amount magic police request from player using the method
    * located in the Exit class.
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param selectedIndex The number representing the players chosen direction
    * @param police The object of the Magic Police class
    * @param items The object of the Item class
    * @return The bribe amount requested by the magic police
    */
    public int bribeAmount(Player player, Room portals, int selectedIndex, MagicPolice police, Items items)
    {
        int bribeAmount = police.getBribeAmount(items.getCoins());
        System.out.print("The magic police demand " + bribeAmount + " coins as a bribe!");
        System.out.println(" You have " + items.getCoins() + " coins available.");
        
        if (items.getCoins() >= bribeAmount)
        {
            Scanner console = new Scanner(System.in);
            System.out.println("Do you want to pay this bribe of " + bribeAmount + " coins? (yes/no)");
            String bribeConfirm = console.nextLine().trim().toLowerCase();
            if (bribeConfirm.equals("yes") || bribeConfirm.equals("y"))
            {
                items.useCoins(bribeAmount);
                System.out.println("You have successfully bribed the police!");
                System.out.println("You have " + items.getCoins() + " coins remaining.");
                policeEncounter = false;
                portals.generatePortals();
                selectedIndex = portals.choosePortalDirection();
            }
            else
            {
                System.out.println("You have chosen not to pay the bribe and been sent to jail.");
                jumps(player, portals, selectedIndex);
            }
        }
        else
        {
            System.out.println("You don't have enough coins to bribe the magic police!");
            System.out.println("You have been sent to jail!");
            jumps(player, portals, selectedIndex);
        }
        return bribeAmount;
    }

    /**
    * Handles player jumps if no portals are available or player is caught
    * by magic police.
    * @param player The object of the Player class
    * @param portals The object of the Room class
    * @param selectedIndex The number representing the players chosen direction
    */
    public boolean jumps(Player player, Room portals, int selectedIndex)
    {
        if (player.getJumps() > 0)
        {
            Scanner console = new Scanner(System.in);
            System.out.println("Do you want to jump backwards? (yes/no)");
            String jumpInput = console.nextLine().trim().toLowerCase();

            if (jumpInput.equals("yes") || jumpInput.equals("y"))
            {
                boolean jumpUsed = player.useJump();
                if (jumpUsed)
                {
                    // Generate a new set of exits and police encounters
                    portals.generatePortals();
                    selectedIndex = portals.choosePortalDirection();
                    return true;
                }
            }
            else
            {
                System.out.println("Jump not used.");
                lostGame(player);
            }
        }
        else
        {
            lostGame(player);
        }
        return false;
    }

    /**
    * Ends game and writes to Outcome.txt if player caught or out of jumps.
    * @param player The object of the Player class
    */
    public void lostGame(Player player)
    {
        try
        {
            PrintWriter writer = new PrintWriter(new File("Outcome.txt"));
            player.setGameEnd(true);
            System.out.println("You have no more jumps remaining!");
            System.out.println("Game concluded. See Outcome.txt file for result.");
            String outcome = "You have no more jumps remaining. Game over.";
            writer.println(outcome);
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Error in writing to file! Exiting...");
        }
    }
    /**
    * Main method for the program. Initialises the game, reads exits.txt, creates
    * the necessary objects from other classes, and begins the game.
    * @params command line arguments. Unnecessary for this program.
    */
    public static void main(String[] args)
    {
        direction = new String[4];
        probabilities = new double[4][3];
        Exit[] exits = new Exit[4];
        try
        {
            //Read Exits.txt file
            FileReader reader = new FileReader(FILE_NAME);
            try
            {
                Scanner fileInput = new Scanner(reader);
                int line = 0;
                while (fileInput.hasNextLine())
                {
                    //Read contents with split() method (,)
                    String[] row = fileInput.nextLine().split(",");
                    try
                    {
                        for(int i = 0; i < row.length; i++)
                        {
                            if (i == 0)
                            {
                                direction[line] = row[i];
                            }
                            else
                            {
                                probabilities[line][i-1] = (Double.parseDouble(row[i]) / 100.0);
                            }
                        }
                        line++;
                      }
                    catch (Exception e)
                    {
                        System.out.println("Error in reading probabilities on line" + line + ": " + e.getMessage());
                        continue;
                    }
                }
                }
            finally
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.out.println("Error reading file. Exiting...");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error reading file. Exiting...");
        }
        
        //Configure Exits
        for (int i = 0; i < 4; i++)
        {
            exits[i] = new Exit(direction[i], probabilities[i][2]);
        }

        //Create objects
        Game game = new Game();
        Items items = new Items();
        items.setGame(game);
        items.setProbabilities(probabilities);
        Room portals = new Room(game, items);
        Exit exit = new Exit(game);
        MagicPolice police = new MagicPolice(game);
        Player player = new Player();
        
        player.promptPlayerName();
        player.displayGameInstructions();
        
        game.playRound(player, portals, exit, police, items);
    }
}
