package no.ntnu.idatt2003.fileManagement.boardFiles;

import java.io.IOException;

import no.ntnu.idatt2003.Board;

public interface BoardFileWriter {
    void writeBoardToFile(Board board, String filePath) throws IOException;
}
