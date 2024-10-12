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
                incX();
                decY();
                break;
            case NW:
                decX();
                decY();
                break;
            case SE:
                incX();
                incY();
                break;
            case SW:
                decX();
                incY();
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

    public void incX() {
        this.x++;
    }

    public void decX() {
        this.x--;
    }

    public void incY() {
        this.y++;
    }

    public void decY() {
        this.y--;
    }
}
