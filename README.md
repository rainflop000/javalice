This is a text-based interface game called Javalice.

This game was originally written as part of my Masters of Computer Science course, and subsequently refractored before making the repository public.

## Aim of the Game
In Javalice, the player takes on the role of a wielder of forbidden magical powers. The new king of Javalice has outlawed magic and constructed a series of mazes to trap those who practice it. The player must escape these mazes by locating and unlocking a magical portal leading to another realm.

The goal is to find the final exit portal and leave Javalice before being caught by the king's forces or ending up in jail.

Here's how the game works:

### Setup

1. **Load Exit Data**:
- The game reads portal information from exits.txt at startup.
- This file contains:
    - Portal direction (North, South, East, West).
    - Probability of the portal being open, finding the exit, and encountering magical police.
- Exits are configured once at the start.

2. **Player Setup**:
- Enter a name (3–12 characters).
- Start with 10 coins.

### Gameplay

1. **Rooms and Portals**:
- Each room has up to four portals (North, South, East, West).
- Portal probabilities (open, exit, police) are preloaded from exits.txt.

2. **Portal Interaction**:
- If a portal is chosen but no exit is found:
    - Exit and police probabilities for that direction are adjusted randomly by ±1% to 5% (range: 0–100%).

3. **Player Actions**:
- View available portals and their probabilities for finding the exit and encountering the magical police.
- Select a direction to move.

4. **Magic Boxes**:
- Each room has a 50% chance of containing a magic box with one of the following:
    - Coins: 10–35 coins (30% chance).
    - Magic Police Alarm: Increases police encounter probabilities by 3% in all directions (25%).
    - Invisibility Cloak: Escapes magical police encounters (15%).
    - Coal: No effect (30%).
- Players can open the box and decide whether to keep the item. Inventory limit: 3 items.

5. **Encounters**:
- Magical Police:
    - Use an Invisibility Cloak or bribe them (cost: 0.5x–1.5x current coins).
    - If neither option is possible, the player is caught.
- Jail:
    - Jump back up to three times to a new room. No reversed probabilities.

### Winning and Losing

- Win: Locate the final exit portal (probability determined by exits.txt).
- Lose:
    - Caught by magical police with no means of escape.
    - Exhaust all backward jumps after landing in jail.

### Endgame
- The outcome is logged in Outcome.txt.

Good luck escaping Javalice!