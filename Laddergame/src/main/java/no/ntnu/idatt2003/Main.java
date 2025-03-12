package no.ntnu.idatt2003;

import java.io.IOException;

import no.ntnu.idatt2003.fileManagement.boardFiles.BoardFileReaderGson;
import no.ntnu.idatt2003.tile.Tile;

public class Main {
    public static void main(String[] args) throws IOException {


        BoardFileReaderGson boardReader = new BoardFileReaderGson();
        Board board = boardReader.readBoardFromFile("resources/boards/laddergame1.json");
        
        System.out.println("Name: "+board.getName());
        System.out.println("Description: "+board.getDescription());

        for (Tile tile : board.getGameboard()) {
            System.out.println(tile.getLocation());
        }
    }
}