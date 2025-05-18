package no.ntnu.idatt2003.model.fileManagement.boardFiles;

import java.io.IOException;
import java.nio.file.Path;

import no.ntnu.idatt2003.model.Board;

/**
 * The interface for writing json files
 */
public interface BoardFileWriter {

  void writeBoardToFile(Path path, Board board) throws IOException;
}
