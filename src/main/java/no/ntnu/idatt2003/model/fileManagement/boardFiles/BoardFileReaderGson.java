package no.ntnu.idatt2003.model.fileManagement.boardFiles;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.tile.FinishTile;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.PauseTile;
import no.ntnu.idatt2003.model.tile.PlayerSwapTile;
import no.ntnu.idatt2003.model.tile.Tile;

//How to read
//BoardFileReaderGson boardReader = new BoardFileReaderGson();
//Board board = boardReader.readBoardFromFile("src/main/resources/boards/laddergame1.json");


/**
 * This class reads jsonfiles
 */
public class BoardFileReaderGson implements BoardFileReader {

  /**
   * The method that reads the Json file.
   *
   * @param jsonPath The input variable. Input the path to the Json-file.
   * @return Returns a board object.
   * @throws IOException an exception that may occur during file reading
   */
  @Override
  public Board readBoardFromFile(String jsonPath) throws IOException {
    try (
        FileReader fileReader = new FileReader(jsonPath);
        JsonReader reader = new JsonReader(fileReader);) {
 
      JsonElement element = JsonParser.parseReader(reader);
      JsonObject boardJson = element.getAsJsonObject();
      //Extracts the board array from the json
      JsonArray board = boardJson.getAsJsonArray("board");
      String description = boardJson.get("description").getAsString();
      String name = boardJson.get("name").getAsString();
      int rows = boardJson.get("rows").getAsInt();
      int cols = boardJson.get("cols").getAsInt();

      ArrayList<Tile> gameboard = new ArrayList<>();

      for (JsonElement jsonElement : board) {
        String tileType = jsonElement.getAsJsonObject().get("tileType").getAsString();
        int location = jsonElement.getAsJsonObject().get("location").getAsInt();

        switch (tileType) {
          case "Tile":

            gameboard.add(new Tile(location));
            break;

          case "LadderTile":

            int travelLocation = jsonElement.getAsJsonObject().get("travellocation").getAsInt();
            gameboard.add(new LadderTile(location, travelLocation));
            break;

          case "PauseTile":

            gameboard.add(new PauseTile(location));
            break;

          case "PlayerSwapTile":

            gameboard.add(new PlayerSwapTile(location));

            break;

          case "FinishTile":
            gameboard.add(new FinishTile(location));
            break;

          default:
            System.err.println("Unknown tile type: " + tileType);
            // Handle unknown tile type if necessary
            break;
        }
      }

      return new Board(gameboard, name, description, rows, cols);

    } catch (IOException e) {
      System.err.println("Error reading JSON-file: " + e.getMessage());
      throw e;
    } catch (JsonSyntaxException | IllegalStateException e) {
      throw new IOException("Error in JSON-structure: " + jsonPath, e);
    }
  }
}