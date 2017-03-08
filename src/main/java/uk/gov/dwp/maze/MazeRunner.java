package uk.gov.dwp.maze;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class MazeRunner {
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("Maze1.txt").toURI()));
            Maze maze = new Maze(lines);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Could not load the maze file", e);
        }

    }
}
