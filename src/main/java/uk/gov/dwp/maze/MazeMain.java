package uk.gov.dwp.maze;

import uk.gov.dwp.maze.enums.Facing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class MazeMain {

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("Maze1.txt").toURI()));

            Explorer explorer = new Explorer(Facing.NORTH, lines);
            explorer.turnRight();
            explorer.moveExplorerForward();
            explorer.moveExplorerForward();
            System.out.println(explorer.whereHaveYouBeen());

        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Could not load the maze file", e);
        }

    }

}
