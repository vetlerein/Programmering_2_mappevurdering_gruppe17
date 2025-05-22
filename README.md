# Programming 2 Final Project - Group 17
## Features
To see the full JavaDoc:   [click here](https://vetlerein.github.io/Programmering_2_mappevurdering_gruppe17/apidocs/index.html)

The main features of this application are:
- Add a new player
- Play Laddergame
- Play Monopoly

## Laddergame
When you enter the Ladder the "Start new game" button takes you to another window, where you select players and the board of your choosing. When you're in the game you can play like normal by throwing the dice yourself, or simulate the game. The youngest player starts. There are 5 types of tiles to be aware of. These are: 
- Pausetile: Makes you wait a round.
- Swaptile: Swaps you with the furthest of the other players.
- Laddertile: Brings you up or down.
- Finishtile. The winner is the first one to reach the finishtile. You need to land exactly on this one to win, if the sum of the dice is higher, you start going backwards.

## Monopoly
When entering Monopoly, the "Start new game" button will let you choose players. After start you can play monopoly like usual. You can throw the dice, buy properties, buy houses for the properties, get chancecards and perhaps trade with other players.

## Requirements
You need the following software installed and added to path.
- Java Development Kit 17 or later
- Apache Maven
- JavaFX (added as dependencies in pom.xml, no separate installation needed)
  
## How to run
Make sure you have Java and Maven installed (see **Requirements** above), then open a terminal in the project folder and run: 
bash
mvn clean javafx:run# Programmering_2_mappevurdering_gruppe17