import java.util.*;

/**
* Class represents a room/portal in the game. Contains methods generating available
* portals, choose portal direction and handle user input.
* @author rainflop000
* @version 1.0
*/
public class Room
{
    private boolean[] availablePortals = new boolean[4];
    private Game game;
    private int selectedIndex;
    private Items items;

    /**
    * Default constructor for Room class. selectedIndex value of -1 indicates no
    * portals are available.
    */
    public Room()
    {
        selectedIndex = -1;
    }

    /**
    * Parametised constructor for Room class.
    * @param game The current instance of the game being played
    * @param items The object of the Items class to represent the available items in
    * the room
    */
    public Room(Game game, Items items)
    {
        this.game = game;
        this.items = items;
    }

    /**
    * Accessor for the user's selected direction.
    * @return selectedIndex The portal direction chosen by the user, stored in an array
    */
    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    /**
    * Chooses portal direction based on user's input.
    * @return selectedIndex The index of the selected portal direction represented in
    * the array of available directions
    */
    public int choosePortalDirection()
    {
        String[] portals = getAvailablePortals();
        if (portals.length == 0)
        {
            return -1;
        }
        char direction = getUserInput(portals);
        selectedIndex = findSelectedIndex(direction);
        return selectedIndex;
    }

    /**
    * Prompts user for input to choose which portal direction they would like to
    * go in.
    * @param portals An array of Strings showing the available portal directions they can
    * choose from
    * @return direction A char of the direction input by the user
    */
    public char getUserInput(String[] portals)
    {
        Scanner console = new Scanner(System.in);
        char direction;
        boolean validDirection;
        do
        {
            System.out.println("Choose an available portal direction: " + Arrays.toString(portals));
            direction = console.nextLine().trim().toUpperCase().charAt(0);
            validDirection = false;
            for (String portal : portals)
            {
                if (portal.charAt(0) == direction)
                {
                    validDirection = true;
                    break;
                }
            }
        }
        while (!(Character.isLetter(direction) && (validDirection)));
        return direction;
    }

    /**
    * Finds selectedIndex based on the direction input by the user.
    * @param direction The selected direction as a character.
    * @return The index of the selected direction. Returns -1 if no valid
    * direction chosen
    */
    public int findSelectedIndex(char direction)
    {
        for (int i = 0; i < game.getDirection().length; i++)
        {
            if (game.getDirection()[i].charAt(0) == direction)
            {
                return i;
            }
        }
        return -1;
    }

    /**
    * Method generates available portals player can choose to go in from current room.
    * @return portals An array of boolean values indicating whether each portal is
    * available, or not.
    */
    public boolean[] generatePortals()
    {
        boolean[] portals = new boolean[4]; // Array to store available portals
        for (int i = 0; i < 4; i++)
        {
            double portalOpenChance = game.getProbabilities()[i][0];
            double random = new Random().nextDouble();
            portals[i] = random <= portalOpenChance;
        }
        return portals;
    }

    /**
    * Gets the available portal directions based on the generated portals.
    * @return available An array of available portal directions stored as Strings
    */
    public String[] getAvailablePortals()
    {
        availablePortals = generatePortals();
        List<String> available = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            if (availablePortals[i])
            {
                String direction = game.getDirection()[i];
                double exitChance = game.getProbabilities()[i][1] * 100; // Convert to percentage
                double policeEncounterChance = game.getProbabilities()[i][2] * 100; // Convert to percentage 
                String portalInfo = String.format("%s (Exit: %.2f%%, Police: %.2f%%)", direction, exitChance, policeEncounterChance);
                available.add(portalInfo);
            }
        }
        return available.toArray(new String[0]);
    }
}
