package no.ntnu.idatt2003.fileManagement.boardFiles;

import java.io.IOException;
import com.google.gson.JsonObject;
import no.ntnu.idatt2003.Board;

public class BoardFileReaderGson implements BoardFileReader {

    @Override
    public Board readBoardFromFile(String filePath) throws IOException {
        // TODO Auto-generated method stub
        return null;
        JsonObject gameObject = new JsonParser().parse(new FileReader(filePath)).getAsJsonObject();
    }
}
