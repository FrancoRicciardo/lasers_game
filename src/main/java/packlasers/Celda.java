package packlasers;

public class Celda {
    private final boolean tienePiso;
    private Bloque bloque;

    public Celda(boolean hasFloor) {
        this.tienePiso = hasFloor;
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

    public boolean getPiso() {
        return this.tienePiso;
    }
}
