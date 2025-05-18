package no.ntnu.idatt2003.model.fileManagement.boardFiles;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.Tile;

/**
 * This class writes a board object to a json file.
 * It uses the Gson library to convert the board object to a json string and write it to a file.
 */
public class BoardFileWriterGson implements BoardFileWriter {
    
    /**
     * The method that reads the Json file.
     * @param path The path of where to save it.
     * @param board The board object to be written to the file.
     * @throws IOException
     */
    @Override
    public void writeBoardToFile(Path path, Board board)  throws IOException {
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(path.toString())) {
            JsonObject boardJsonObject = new JsonObject();
            JsonArray boardJsonArray = new JsonArray();

            for(Tile tile : board.getGameboard()){
                JsonObject tileJsonObject = new JsonObject();
                tileJsonObject.addProperty("tileType", tile.getClass().getSimpleName());
                tileJsonObject.addProperty("location", tile.getLocation());
                if (tile instanceof LadderTile) {
                    LadderTile ladderTile = (LadderTile) tile;
                    tileJsonObject.addProperty("travellocation", ladderTile.getTravelLocation());
                }
                boardJsonArray.add(tileJsonObject);
            }

            boardJsonObject.addProperty("name", board.getName());
            boardJsonObject.addProperty("description", board.getDescription());
            boardJsonObject.addProperty("rows", board.getRows());
            boardJsonObject.addProperty("cols", board.getCols());
            boardJsonObject.add("board", boardJsonArray);
            gson.toJson(boardJsonObject, fileWriter);
        }
    }
}
