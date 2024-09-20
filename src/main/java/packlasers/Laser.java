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

    public int getCoordX() {
        return this.x;
    }

    public int getCoordY() {
        return this.y;
    }

    public void mover() {
        // TODO: Cambiar las coordenadas (x, y) en base a la dirección
    }
}

enum Direction {
    SE, SW, NE, NW
}