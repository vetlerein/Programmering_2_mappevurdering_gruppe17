package no.ntnu.idatt2003.fileManagement.boardFiles;

import java.io.IOException;

import no.ntnu.idatt2003.Board;

public interface BoardFileReader {
    Board readBoardFromFile(String jsonString) throws IOException;
}