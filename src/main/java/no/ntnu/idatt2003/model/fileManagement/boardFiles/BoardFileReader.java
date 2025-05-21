package no.ntnu.idatt2003.model.fileManagement.boardFiles;

import java.io.IOException;

import no.ntnu.idatt2003.model.Board;

/**
 * The interface for reading json files
 */
public interface BoardFileReader {
 
  /**
   * The method that reads the Json file.
   *
   * @param jsonString The input variable. Input the path to the Json-file.
   * @return Returns a board object.
   * @throws IOException an exception that may occur during file reading
   */
  Board readBoardFromFile(String jsonString) throws IOException;
}