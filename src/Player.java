import java.util.*;

/**
* Class represents a player in the game. Contains methods to obtain player name,
* display game instructions and use/display jumps.
* @author rainflop000
* @version 1.0
*/
public class Player
{
    private boolean gameWon;
    private boolean gameEnd;
    private boolean caughtByPolice;
    private int jumps;
    private String playerName;

    /**
    * Default constructor to initialise default values, including a
    * jump count of 3.
    */
    public Player()
    {
        gameWon = false;
        gameEnd = false;
        caughtByPolice = false;
        jumps = 3;
        playerName = "Unknown";
    }

    public int getJumps()
    {
        return jumps;
    }

    public boolean isCaughtByPolice()
    {
        return caughtByPolice;
    }

    public boolean isGameEnd()
    {
        return gameEnd;
    }

    public boolean isGameWon()
    {
        return gameWon;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setCaughtByPolice(boolean caughtByPolice)
    {
        this.caughtByPolice = caughtByPolice;
    }

    public void setGameEnd(boolean gameEnd)
    {
        this.gameEnd = gameEnd;
    }

    public void setGameWon(boolean gameWon)
    {
        this.gameWon = gameWon;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    /**
    * Prompts player to enter name of 3-12 characters inclusive
    * @return playerName The name entered by user
    */
    public String promptPlayerName()
    {   
        Scanner console = new Scanner(System.in);
        do
        {
            System.out.println("Please enter your name (3-12 characters only): ");
            playerName = console.nextLine().trim();
        }
        while (playerName.length() < 3 || playerName.length() > 12);
        return playerName;
    }

    /**
    * Displays game instructions to user, using the name entered by player.
    */
    public void displayGameInstructions()
    {
        System.out.println(" \n" + playerName + "! The new king has outlawed magic. You must escape Javalice as soon as possible!");
        System.out.println("Escape Javalice by using the magical portals to find an exit to another realm.");
        System.out.println("Warning, you must avoid the magic police. Good luck, " + playerName + "!\n");
    }

    /**
    * Uses jump if player has any of their three remaining
    * @return True if jump uses, otherwise false
    */
    public boolean useJump()
    {
        if (jumps > 0)
        {
            jumps--;
            displayRemainingJumps();
            return true;
        }
        return false;
    }

    /**
    * Displays number of jumps remaining to player.
    */
    public void displayRemainingJumps()
    {
        if (jumps > 1 || jumps == 0)
        {
            System.out.println("You have " + jumps + " jumps remaining!");
        }
        else if (jumps == 1)
        {
            System.out.println("You have " + jumps + " jump remaining!");
        }
    }
}