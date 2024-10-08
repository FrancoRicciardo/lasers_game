package packlasers;

public class Celda {
    private final boolean tienePiso;
    private Bloque bloque;
    private final Posicion posicion;

    public Celda(boolean hasFloor, Posicion posicion) {
        this.tienePiso = hasFloor;
        this.posicion = posicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void ponerBloque(Bloque block) {
        this.bloque = block;
    }

    public Bloque getBloque() {
        return this.bloque;
    }

    public void quitarBloque() {
        this.bloque = null;
    }

    public boolean tieneBloque() {
        return bloque != null; // Devuelve true si hay un bloque, false si no
    }

    public boolean getPiso() {
        return this.tienePiso;
    }

}
