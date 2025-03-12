package no.ntnu.idatt2003.fileManagement.boardFiles;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import no.ntnu.idatt2003.Board;
import no.ntnu.idatt2003.tile.LadderTile;
import no.ntnu.idatt2003.tile.Tile;

public class BoardFileReaderGson implements BoardFileReader {

    @Override
    public Board readBoardFromFile(String jsonString) throws IOException {
       
        String description = JsonParser.parseString(jsonString).getAsJsonObject().get("description").getAsString();
        String name = JsonParser.parseString(jsonString).getAsJsonObject().get("name").getAsString();

        JsonObject boardJson = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray tilesJson = boardJson.getAsJsonArray("tiles");

        ArrayList<Tile> gameboard = new ArrayList<>();
        for (JsonElement jsonElement : tilesJson) {
            String tileType = jsonElement.getAsJsonObject().get("tileType").getAsString();
            int location = jsonElement.getAsJsonObject().get("location").getAsInt();
            switch (tileType) {
                case "normal":

                    gameboard.add(new Tile(location));
                    break;

                case "ladderTile":
                    
                    int travelLocation = jsonElement.getAsJsonObject().get("travelLocation").getAsInt();  
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

        Board board = new Board(gameboard, name, description);
        return board;

    }
}
