package packlasers;

public class Laser {
    private int x;
    private int y;
    private Direccion direccion;
    private boolean estoyActivo;

    public Laser(int x, int y, Direccion direccion) {
        this.x = x;
        this.y = y;
        this.direccion = direccion;
        this.estoyActivo = true;
    }

    public Direccion getDireccion(){
        return direccion;
    }

    public int getCoordX() {
        return this.x;
    }

    public int getCoordY() {
        return this.y;
    }

    public void fuiAbsorbido() {
        this.estoyActivo = false;
    }

    public void puedoContinuar() {
        this.estoyActivo = true;
    }

    public boolean isActive() {
        return this.estoyActivo;
    }

    public void mover(){
        // Mueve el laser al extremo opuesto del bloquee
        if(estoyActivo) {
            switch (direccion) {
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
        }
    }

    public Laser reflejarLaser() {
        // Refleja el laser dependiendo su direccion actual
        Direccion nuevaDireccion = switch (direccion) {
            case NE -> Direccion.NW;
            case NW -> Direccion.NE;
            case SE -> Direccion.SW;
            case SW -> Direccion.SE;
        };

        // Devuelve el laser con la nueva direccion
        return new Laser(this.x, this.y, nuevaDireccion);
    }

    public void refractarLaser() {
        // Refracta el laser dependiendo su direccion actual
        switch (direccion){
            case NE, SE:
                if (this.x % 2 == 0) // Si coord x es par, "sigo de largo" en x
                    this.x += 2;
                else                 // Si coord y es par, "sigo de largo" en y
                    this.y += 2;
                break;
            case NW, SW:
                if (this.x % 2 == 0) // Si coord x es par, "sigo de largo" en x
                    this.x -= 2;
                else                 // Si coord y es par, "sigo de largo" en y
                    this.y += 2;
                break;
        }
    }
}

enum Direccion {
    SE, SW, NE, NW
}