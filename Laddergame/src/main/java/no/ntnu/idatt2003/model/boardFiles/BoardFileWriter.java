package no.ntnu.idatt2003.model.boardFiles;

import java.io.IOException;

import no.ntnu.idatt2003.model.Board;

public interface BoardFileWriter {
    void writeBoardToFile(Board board, String jsonString) throws IOException;
}
