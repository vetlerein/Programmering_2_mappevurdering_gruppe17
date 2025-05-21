package no.ntnu.idatt2003.model;

import java.util.ArrayList;

import no.ntnu.idatt2003.model.tile.Tile;

/**
 * This class represents the board.
 */
public class Board {

  /**
   * The ArrayList of tiles that is the board.
   */
  private final ArrayList<Tile> gameboard;

  /**
   * The name of the board.
   */
  private final String name;

  /**
   * The description of the board.
   */
  private final String description;

  /**
   * The number of rows on the board.
   */
  private final int rows;

  /**
   * The number of columns on the board.
   */
  private final int cols;

  /**
   * Creates a board object.
   *
   * @param gameboard:   Arraylist of all the tiles that belongs on the board.
   * @param name:        Name of the board.
   * @param description: Description for the board.
   * @param rows:        Number of rows on the board.
   * @param cols:        Number of columns on the board.
   */
  public Board(ArrayList<Tile> gameboard, String name, String description, int rows, int cols) {
    this.gameboard = gameboard;
    this.name = name;
    this.description = description;
    this.rows = rows;
    this.cols = cols;
  }

  /**
   * returns the gameboard.
   *
   * @return Returns an ArrayList of the tiles within the board.
   */
  public ArrayList<Tile> getGameboard() {
    return gameboard;
  }

  /**
   * returns the name of the board.
   *
   * @return returns the board name.
   */
  public String getName() {
    return name;
  }

  /**
   * getter for the description of the board.
   *
   * @return returns the board description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * getter for the number of rows on the board.
   *
   * @return returns the amount of rows on the board.
   */
  public int getRows() {
    return rows;
  }

  /**
   * getter for the number of columns on the board.
   *
   * @return returns the amount of columns on the board.
   */
  public int getCols() {
    return cols;
  }

  /**
   * Calculates the coordinates based on the position and a given number of rows and columns.
   *
   * @param location the location of the tile to get coordinates for.
   * @return the coordinates of the tile as an array of integers, where the first element is the
   * x-coordinate and the second element is the y-coordinate.
   */
  public int[] getCoordinates(int location) {
    int[] coordinates = new int[2];
    int bottomRow = (location - 1) / cols;
    coordinates[1] = rows - 1 - bottomRow;

    //Checks if the next row is going right to left or left to right
    if (bottomRow % 2 == 0) {
      //If the row is even, the coordinates are calculated from right to left
      coordinates[0] = (location - 1) % cols;
    } else {
      //if the row is odd, the coordinates are calculated from left to right
      coordinates[0] = cols - 1 - ((location - 1) % cols);
    }

    return coordinates;
  }

  /**
   * Calculates the coordiantes based on the location for the monopoly board.
   *
   * @param location the location of the tile to get coordinates for.
   * @return the location based on inputed coordinates.
   */
  public int[] getCoordinatesMonopoly(int location) {
    int[] coordinates = new int[2];
    if (location >= 1 && location <= 11) {
      coordinates[0] = 0;
      coordinates[1] = 11 - location;
    } else if (location >= 12 && location <= 20) {
      coordinates[0] = location - 11;
      coordinates[1] = 0;
    } else if (location >= 21 && location <= 31) {
      coordinates[0] = cols - 1;
      coordinates[1] = location - 21;
    } else if (location >= 32 && location <= 40) {
      coordinates[1] = cols - 1;
      coordinates[0] = (location - 41) * -1;
    }
    return coordinates;
  }

  /**
   * Calculates the coordinates based on the position and a given number of rows and columns.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return the location based on inputed coordinates.
   */
  public int getLocation(int x, int y) {
    int location;
    int bottomRow = rows - 1 - y;

    //Checks if the next row is going right to left or left to right
    if (bottomRow % 2 == 0) {
      //If the row is even, the coordinates are calculated from right to left
      location = (bottomRow * cols) + x + 1;
    } else {
      //if the row is odd, the coordinates are calculated from left to right
      location = (bottomRow * cols) + (cols - 1 - x) + 1;
    }

    return location;
  }
}
