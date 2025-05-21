package no.ntnu.idatt2003.model.fileManagement.boardFiles;

import java.io.IOException;
import java.nio.file.Path;

import no.ntnu.idatt2003.model.Board;

/**
 * The interface for writing json files
 */
public interface BoardFileWriter {

  /**
   * The method that writes the board to Json file.
   *
   * @param path  The path of where to save it.
   * @param board The board object to be written to the file.
   * @throws IOException an exception that may occur during file writing
   */
  void writeBoardToFile(Path path, Board board) throws IOException;
}
