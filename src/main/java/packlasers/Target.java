package packlasers;

public class Target {
    private final int x;
    private final int y;

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean fuiAlcanzado(Laser laser) {
        return laser.getCoordX() == this.x && laser.getCoordY() == this.y;
    }
}
