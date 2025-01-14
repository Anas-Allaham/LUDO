# Ludo Game

This project is a Java implementation of the classic board level Ludo. The level allows multiple players to take turns rolling dice and moving their stones on the board to reach the target. The project includes both a console-based interface and a graphical user interface (GUI) for enhanced gameplay.

## Features

- **Multiplayer Gameplay:** Supports up to 4 players.
- **Graphical User Interface (GUI):** Provides an interactive Java Swing-based interface for rolling dice and viewing level progress.
- **Console-Based Interface:** Displays the level board and provides instructions in the terminal.
- **Game Logic:** Implements Ludo rules, including safe zones, capturing, and winning conditions.
- **Heuristics & AI:** Includes heuristic-based scoring and decision-making for automated player actions.

## Project Structure

### Main Classes

- `Main`: Entry point of the program. Manages player turns, dice rolls, and overall level flow.
- `Game`: Core level logic, including board management, player actions, and level state updates.
- `Board`: Represents the level board and its nodes (cells).
- `Player`: Represents a player, including their stones and current state.
- `Stone`: Represents individual stones, including position, state, and ownership.
- `Statics`: Utility class for colors, dice rolls, and board-related constants.
- `LudoBoardConsole`: Handles rendering the board in the console.
- `LUDOGameGui`: Implements the GUI for the level, including dice rolls and player interactions.

### GUI Features

The `LUDOGameGui` class enhances the user experience by providing:

- **Dice Roll Visualization:** Displays dice roll results in a `JTextArea`.
- **Interactive Buttons:** Includes a "Roll Dice" button for player interaction.
- **Real-Time Updates:** Dynamically updates level status in the GUI.

### Key Methods

- **Game Logic:**
    - `update(int idx, int val)`: Updates the position of a player's pawn based on a dice roll.
    - `IsGameOver()`: Checks if the level is over.
    - `rec(int depth)`: Implements recursive logic for AI decision-making using heuristics.

- **GUI Integration:**
    - `createAndShowGUI()`: Initializes and displays the Swing-based GUI.
    - `throw_dice()`: Simulates dice rolls and updates the GUI with results.

## How to Run

### Console-Based Version

1. **Clone the Repository:**
   ```bash
   git clone <repository-url>
   cd ludo-level
   ```

2. **Compile the Project:**
   Ensure you have Java installed. Compile the project using:
   ```bash
   javac -d bin src/com/company/*.java
   ```

3. **Run the Game (Console Version):**
   ```bash
   java -cp bin com.company.Main
   ```

4. **Follow the Instructions:**
   The level will display instructions in the console. Players can input their moves based on the prompts.

### GUI Version

1. **Compile the Project:**
   Compile the project to include the `LUDOGameGui` class:
   ```bash
   javac -d bin src/com/company/*.java
   ```

2. **Run the Game (GUI Version):**
   ```bash
   java -cp bin com.company.LUDOGameGui
   ```

3. **Interact with the GUI:**
    - Click the "Roll Dice" button to roll the dice.
    - View the dice results in the displayed text area.

## Game Instructions

1. Each player takes turns rolling the dice.
2. Choose a pawn to move based on the dice roll.
3. Stones move clockwise on the board, following the Ludo rules.
4. Capture opponent stones by landing on their position.
5. Move all your stones to the target to win the level.

## Customization

- **GUI Layout:** Modify the `createAndShowGUI` method in the `LUDOGameGui` class to customize the interface.
- **AI Depth:** Adjust the depth of AI decision-making by modifying the `rec` method in the `Game` class.
- **Board Size:** Configure the board dimensions and properties in the `Board` and `Statics` classes.

## Future Enhancements

- **Enhanced GUI Features:** Add support for full board visualization and player interactions.
- **Network Multiplayer:** Allow players to play over a network.
- **Save/Load Functionality:** Add the ability to save and resume games.
- **Advanced AI:** Implement more sophisticated AI algorithms for better decision-making.
- **Mobile Compatibility:** Port the level to mobile platforms using JavaFX or other frameworks.

## Requirements

- **Java Development Kit (JDK):** Version 8 or higher.

## License

This project is open-source and available under the [MIT License](LICENSE).

## Contact

For questions or feedback, please reach out to [Anas](mailto:anas.sahar.ahmad@gmail.com).

