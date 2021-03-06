package uk.gov.dwp.maze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dwp.maze.enums.Facing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


class MazeMain {

    private static final Logger LOG = LoggerFactory.getLogger(Explorer.class);

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("Maze1.txt").toURI()));

            Maze maze = new Maze(lines);
            Explorer explorer = new Explorer(Facing.NORTH, maze);
            explorer.turnRight();
            explorer.moveExplorerForward();
            explorer.moveExplorerForward();
            System.out.println(explorer.whereHaveYouBeen());

        } catch (IOException | URISyntaxException e) {
            LOG.error("Could not load the maze file", e);
        }
        catch (IllegalStateException e) {
            LOG.error("Could not parse the maze file", e);
        }

    }

}
