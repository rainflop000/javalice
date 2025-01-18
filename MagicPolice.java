import java.util.*;

/**
* Class represents the magic police in the game. Contains methods to update the chance
* of encounter with the magic police, check if magic police are encountered, and
* calculates the bribe amount.
* @author William Rosenboom
* @version 1.0
*/
public class MagicPolice
{
    private Game game;

    public MagicPolice(Game game)
    {
        this.game = game;
    }

    /**
    * Method to check whether magic police have been encountered, or not.
    * @param selectedIndex Selected direction chosen by the user, stored as
    * an array of integers
    * @return True if magic police have been encountered, false otherwise
    */
    public boolean checkForPoliceEncounter(int selectedIndex)
    {
        if (game.isFoundExit())
        {
            return false;
        }
        double policeEncounterChance = game.getProbabilities()[selectedIndex][2];
        double random = new Random().nextDouble();
        return random <= policeEncounterChance;
    }

    /**
    * Method to calculate the bribe amount demanded by the magic police.
    * @param coins The number of coins a player has at this time
    * @return The bribe amount as an integer
    */
    public int getBribeAmount(int coins)
    {
        int minBribe = (int)(coins * 0.5);
        int maxBribe = (int)(coins * 1.5);
        return new Random().nextInt(maxBribe - minBribe + 1) + minBribe;
    }

    /**
    * Randomly increases or decreases chance of encounter with magic police by 1% to 5%.
    * @param selectedIndex Selected direction chosen by the user, stored as
    * an array of integers
    */
    public void updatePoliceEncounterChance(int selectedIndex)
    {
        double[][] probabilities = game.getProbabilities();
        Random random = new Random();
        double policeChange = (random.nextBoolean() ? -1 : 1) * (1 + random.nextInt(5));
        double newPoliceChance = probabilities[selectedIndex][2] + policeChange / 100;
        probabilities[selectedIndex][2] = Math.max(0, Math.min(1, newPoliceChance));
    }
}
