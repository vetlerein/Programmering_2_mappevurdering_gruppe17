package no.ntnu.idatt2003.model.boardFiles;

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
import no.ntnu.idatt2003.model.tile.LadderTile;
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
     * @throws IOException
     */
    @Override
    public Board readBoardFromFile(String jsonPath) throws IOException {
        try (
            FileReader fileReader = new FileReader(jsonPath);
            JsonReader reader = new JsonReader(fileReader);) {
            
            JsonElement element = JsonParser.parseReader(reader);
            JsonObject boardJson = element.getAsJsonObject();
            //Extracts the board array from the json
            JsonArray tilesJson = boardJson.getAsJsonArray("board");
            String description = boardJson.get("description").getAsString();
            String name = boardJson.get("name").getAsString();
            
            ArrayList<Tile> gameboard = new ArrayList<>();
            for (JsonElement jsonElement : tilesJson) {
                String tileType = jsonElement.getAsJsonObject().get("tileType").getAsString();
                int location = jsonElement.getAsJsonObject().get("location").getAsInt();
                //add coordinate
                switch (tileType) {
                    case "normal":

                        gameboard.add(new Tile(location));
                        break;

                    case "ladderTile":
                        
                        int travelLocation = jsonElement.getAsJsonObject().get("travellocation").getAsInt();  
                        gameboard.add(new LadderTile(travelLocation, travelLocation));
                        break;

                    case "pauseTile":

                        //idk what to do here
                        break;

                    case "playerSwapTile":
                        
                        //idk, finne location til en annen og gjøre noe greier

                        break;

                    default:
                        break;
                }
            }
            
            return new Board(gameboard, name, description);

        } catch (IOException e) {
            System.err.println("Feil ved lesing av JSON-fil: " + e.getMessage());
            throw e;  // Kaster feilen videre
        } catch (JsonSyntaxException | IllegalStateException e) {
            throw new IOException("Feil i JSON-struktur: " + jsonPath, e);
        }
    }
}
