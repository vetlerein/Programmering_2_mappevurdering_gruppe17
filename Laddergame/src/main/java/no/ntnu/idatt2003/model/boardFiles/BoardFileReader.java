package no.ntnu.idatt2003.model.boardFiles;

import java.io.IOException;

import no.ntnu.idatt2003.model.Board;

/**
 * The interface for reading json files
 */
public interface BoardFileReader {
    Board readBoardFromFile(String jsonString) throws IOException;
}