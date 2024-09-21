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

    public Laser moverAlExtremo(){
        // Mueve el laser al extremo opuesto del bloquee
        switch (this.direccion){
            case NE:
                this.x += 2;
                this.y -= 2;
                break;
            case NW:
                this.x -= 2;
                this.y -= 2;
                break;
            case SE:
                this.x += 2;
                this.y += 2;
                break;
            case SW:
                this.x -= 2;
                this.y += 2;
                break;
        }

        return this;
    }

    public void continuar() {
        // Actualiza coordenadas en funcion de la direccion
        switch (this.direccion) {
            case NE: // Noreste
                y--;
                x++;
                break;
            case NW: // Noroeste
                x--;
                y--;
                break;
            case SE: // Sureste
                x++;
                y++;
                break;
            case SW: // Suroeste
                x--;
                y++;
                break;
        }
    }

    public Laser reflejarLaser() {
        Direction nuevaDireccion = null;

        // Refleja el laser dependiendo su direccion actual
        switch (this.direccion) {
            case NE:
                nuevaDireccion = Direction.NW;
                break;
            case NW:
                nuevaDireccion = Direction.NE;
                break;
            case SE:
                nuevaDireccion = Direction.SW;
                break;
            case SW:
                nuevaDireccion = Direction.SE;
                break;
        }

        // Devuelve el laser con la nueva direccion
        return new Laser(this.x, this.y, nuevaDireccion);
    }
}

enum Direction {
    SE, SW, NE, NW
}