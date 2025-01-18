import java.util.*;

/**
* Class represents the different items in the game. Contains methods on finding and
* opening magic boxes, adding to inventory and using the items in the game.
* @author William Rosenboom
* @version 1.0
*/
public class Items
{
    public static final int MAX_INVENTORY = 3;

    private boolean magicBox;
    private boolean magicPoliceAlarm;
    private int coins;
    private int foundCoins;
    private int invisibilityCloak;
    private int coal;
    private int inventorySize;
    private double[][] probabilities;
    private Game game;

    /**
    * Default constructor for Items class. Includes 10 coins the player begins the
    * game with.
    */
    public Items()
    {
        magicBox = false;
        magicPoliceAlarm = false;
        coins = 10;
        foundCoins = 0;
        invisibilityCloak = -1;
        coal = -1;
        inventorySize = 0;
    }

    /**
    * Parametised constructor.
    * @param game The game instance to which the items belong.
    */
    public Items(Game game)
    {
        this.game = game;
    }

    public int getCoins()
    {
        return coins;
    }

    public int getFoundCoins()
    {
        return foundCoins;
    }

    public int getInventorySize()
    {
        return inventorySize;
    }
    
    public int getInvisibilityCloak()
    {
        return invisibilityCloak;
    }

    public int getCoal()
    {
        return coal;
    }

    public void setGame(Game game)
    {
        this.game= game;
    }

    public void setProbabilities(double[][] probabilities)
    {
        this.probabilities = probabilities;
    }

    /**
    * Generates the 50/50 chance of a magic box being found in a room. Doesn't
    * generate magic box if exit is found.
    */
    public void findMagicBox()
    {
        if (game.isFoundExit())
        {
            return;
        }
        boolean foundMagicBox = new Random().nextDouble() < 0.5;
        if (foundMagicBox)
        {
            promptOpenMagicBox();
        }
    }

    /**
    * Prompts user for input to open magic box, or not, if one is found.
    */
    public void promptOpenMagicBox()
    {
        Scanner console = new Scanner(System.in);
        System.out.println("You have found a magic box!");
        System.out.println("Do you want to open it? (yes/no)");
        String boxOpenChoice = console.nextLine().trim().toLowerCase();
        if (boxOpenChoice.equals("yes") || boxOpenChoice.equals("y"))
        {
            openMagicBox();
        }
        else
        {
            System.out.println("Magic box not opened.");
        }
    }

    /**
    * Sets chances of each item being found in the magic box and performs actions
    * based on the item found.
    */
    public void openMagicBox()
    {
        String[] magicBoxItems = {"coins", "Magic police alarm", "Invisibility Cloak", "Coal"};
        double[] itemProbabilities = {0.3, 0.25, 0.15, 0.3};
        int magicBoxIndex = chooseItem(magicBoxItems, itemProbabilities);
        {
            switch (magicBoxIndex)
            {
                default:
                    System.out.println("Invalid action");
                    break;
                case 0:
                    foundCoins = new Random().nextInt(26) + 10;
                    coins += foundCoins;
                    System.out.print("You found " + foundCoins + " coins!");
                    System.out.println(" You now have " + coins + " coins.");
                    break;
                case 1:
                    System.out.println("Oh no! You found a magic police alarm!");
                    increasePoliceEncounterChance();
                    System.out.println("Probability of encountering magic police raised 3% in all directions!");
                    break;
                case 2:
                    System.out.println("You found an invisibility cloak!");
                    addToInventory("Invisibility Cloak");
                    break;
                case 3:
                    System.out.println("You found coal. It does nothing.");
                    break;
            }
        }
    }

    /**
    * Randomly chooses which item is found in the box, using the itemProbabilities
    * array.
    * @param items An array of each item name.
    * @param itemProbabilities An array of probabilities for each item storing their
    * chances of appearing.
    * @return The index of the chosen item, identifying which item will appear
    * in the box.
    */
    public int chooseItem(String[] items, double[] itemProbabilities)
    {
        double randomValue = new Random().nextDouble();
        double sum = 0;
        for (int i = 0; i < itemProbabilities.length; i++)
        {
            sum += itemProbabilities[i];
            if (randomValue <= sum)
            {
                return i;
            }
        }
    return -1;
    }

    /**
    * Prompts player to choose whether to add qualifying items to their inventory, if
    * max inventory limit not already succeeded.
    * @param itemName The name of the item a player can add to the inventory.
    */
    public void addToInventory(String itemName)
    {
        Scanner console = new Scanner(System.in);
        String inventoryChoice;
        if (inventorySize < MAX_INVENTORY && itemName.equals("Invisibility Cloak"))
        {
            System.out.println("Do you want to add the item to your inventory? (yes/no)");
            inventoryChoice = console.nextLine().trim().toLowerCase();
            if (inventoryChoice.equals("yes") || inventoryChoice.equals("y"))
            {
                inventorySize++;
                System.out.println("Item added to inventory!");
                if (inventorySize > 1)
                {
                    System.out.println("You now have " + inventorySize + " items in your inventory");
                }
                else
                {
                    System.out.println("You now have " + inventorySize + " item in your inventory");
                }
            }
            else
            {
                System.out.println("Item not added to inventory.");
            }
        }
        else
        {
            System.out.println("Inventory already carrying 3 items. Item unable to be added to inventory");
        }
    }

    /**
    * Increases probabiltiy of encountering magic police encounter by 3%. Called
    * if magic police alarm is found in a magic box.
    */
    public void increasePoliceEncounterChance()
    {
        double[][] probabilities = game.getProbabilities();
        for (int i = 0; i < probabilities.length; i++)
        {
            probabilities[i][2] = Math.min(1, probabilities[i][2] + 0.03);
        }
    }

    /**
    * Decreases number of invisbility cloaks in inventory if one is used.
    */
    public void useInvisibilityCloak()
    {
        invisibilityCloak--;
        inventorySize--;
    }
    
    /**
    * Decreases number of coins a player has after paying bribe
    * @param bribeAmount The amount of coins requested by the magic police
    */
    public void useCoins(int bribeAmount)
    {
        coins -= bribeAmount;
    }
}
