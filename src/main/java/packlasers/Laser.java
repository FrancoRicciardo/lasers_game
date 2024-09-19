package packlasers;

public class Laser {
    private int x;
    private int y;
    private Direction direccion;

    public Laser(int x, int y, Direction direccion) {
        this.x = x;
        this.y = y;
        this.direccion = direccion;
    }

}

enum Direction {
    SE, SW, NE, NW
}