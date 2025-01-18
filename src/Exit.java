import java.util.*;

/**
* Class represents an exit in the game. Contains methods to update exit chance.
* and check if exit is available based on the probability contained in
* exits.txt read in the Game class.
* @author rainflop000
* @version 1.0
*/
public class Exit
{
    private Game game;
    private String direction;
    private double exitProbability;

    /**
    * Parametised constructor to construct a game exit.
    * @param Game reading file associated with the exit probability.
    */
    public Exit(Game game)
    {
        this.game = game;
    }

    /**
    * Parametised constructor of an exit using the (portal) direction and the exit
    * probability located in exits.txt.
    * @param direction the direction of the exit
    * @param exitProbability the probability of an exit being available
    */

    public Exit(String direction, double exitProbability)
    {
        this.direction = direction;
        this.exitProbability = exitProbability;
    }

    /**
    * Checks if an exit is available, based on its probability according to exits.txt
    * @param selectedIndex the index of the selected direction
    * @return true if exit available, otherwise false
    */
    public boolean checkForExit(int selectedIndex)
    {
        double exitChance = game.getProbabilities()[selectedIndex][1];
        double random = new Random().nextDouble();
        return random <= exitChance;
    }

    /**
    * Randomly increases or decreases exit chance by 1% to 5%
    * @param selectedIndex selected direction chosen by the user, stored as an array of integers
    */
    public void updateExitChance(int selectedIndex)
    {
        double probabilities[][] = game.getProbabilities();
        Random random = new Random();
        double exitChange = (random.nextBoolean() ? -1 : 1) * (1 + random.nextInt(5));
        double newExitChance = probabilities[selectedIndex][1] + exitChange / 100;
        probabilities[selectedIndex][1] = Math.max(0, Math.min(1, newExitChance));
    }
}
