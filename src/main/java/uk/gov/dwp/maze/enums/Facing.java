package uk.gov.dwp.maze.enums;


public enum Facing {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    private int xOffset;
    private int yOffset;

    Facing(int xOffset, int yOffset) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public Facing getLeft() {
        return Facing.values()[(ordinal() + 3) % 4];
    }

    public Facing getRight() {
        return Facing.values()[(ordinal() + 1) % 4];
    }

}
