package packlasers;

public class Posicion {
    private int x;
    private int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getCoordX() {
        return x;
    }

    public void setCoordX(int x) {
        this.x = x;
    }

    public int getCoordY() {
        return y;
    }

    public void setCoordY(int y) {
        this.y = y;
    }

    // Metodo útil para mover la posición en base a una dirección
    public void move(Direccion direction) {
        switch (direction) {
            case NE:
                this.x++;
                this.y--;
                break;
            case NW:
                this.x--;
                this.y--;
                break;
            case SE:
                this.x++;
                this.y++;
                break;
            case SW:
                this.x--;
                this.y++;
                break;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posicion position = (Posicion) obj;
        return (x == position.getCoordX() && y == position.getCoordY());
    }
}
