import java.util.*;

/**
* Class represents a player in the game. Contains methods to obtain player name,
* display game instructions and use/display jumps.
* @author William Rosenboom
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

/*
The below code forms part of the Test Strategy for the Player class.
It does not form part of actual Escape from Javalice program.
Have retained this code to demonstrate the Testing Strategy for this class - to be
read in conjunction with the uploaded "Test Strategy - Player Class" PDF.

public static void main(String[] args)
{
    Player objPlayer = new Player();
    objPlayer.test();
}

public void display()
{
    System.out.println("gameWon: " + gameWon);
    System.out.println("gameEnd: " + gameEnd);
    System.out.println("caughtByPolice: " + caughtByPolice);
    System.out.println("jumps: " + jumps);
    System.out.println("Player Name: " + playerName);
}

public void test()
{
    System.out.println("Create a Player object with the default constructor");
    Player testPlayer1 = new Player();
    testPlayer1.display();

    System.out.println("Test all get methods");
    Player testPlayer2 = new Player();
    testPlayer2.getJumps();
    System.out.println("getJumps: " + getJumps());
    testPlayer2.isCaughtByPolice();
    System.out.println("isCaughtByPolice: " + isCaughtByPolice());
    testPlayer2.isGameEnd();
    System.out.println("isGameEnd: " + isGameEnd());
    testPlayer2.isGameWon();
    System.out.println("isGameWon: " + isGameWon());
    testPlayer2.getPlayerName();
    System.out.println("getPlayerName: " + getPlayerName());

    System.out.println("Test setCaughtByPolice");
    Player testPlayer3 = new Player();
    testPlayer3.setCaughtByPolice("Hello");
    System.out.println("" + testPlayer3.caughtByPolice);
    testPlayer3.setCaughtByPolice(true);
    System.out.println("" + testPlayer3.caughtByPolice);

    System.out.println("Test setGameEnd");
    Player testPlayer3 = new Player();
    testPlayer3.setGameEnd(123);
    System.out.println("" + testPlayer3.gameEnd);
    testPlayer3.setGameEnd(true);
    System.out.println("" + testPlayer3.gameEnd);

    System.out.println("Test setGameWon");
    Player testPlayer3 = new Player();
    testPlayer3.setGameWon('s');
    System.out.println("" + testPlayer3.gameWon);
    testPlayer3.setGameWon(true);
    System.out.println("" + testPlayer3.gameWon);

    System.out.println("Test setPlayerName");
    Player testPlayer3 = new Player();
    testPlayer3.setPlayerName(true);
    System.out.println("" + testPlayer3.playerName);
    testPlayer3.setPlayerName("William");
    System.out.println("" + testPlayer3.playerName);

    System.out.println("Test promptPlayerName");
    Player testPlayer4 = new Player();
    testPlayer4.promptPlayerName();

    System.out.println("Test useJump");
    Player testPlayer5 = new Player();
    testPlayer5.useJump();
    testPlayer5.useJump();
    testPlayer5.useJump();
*/
