package packlasers;

public interface Bloque {
    /**
     * Metodo que define cómo interactúa el bloque con un láser
     */
    void interactuarLaser(Laser laser, Tablero tablero);

    /**
     * Metodo que indica si el bloque es móvil o no
     */
    boolean esMovible();
}

/**
 * Refracta el laser, continuando en linea recta por el extremo opuesto
 */
class BloqueDeCristal implements Bloque {
    @Override
    public void interactuarLaser(Laser laser, Tablero tablero) {
        laser.puedoContinuar();
        laser.refractarLaser();

    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

/**
 * Difracta el láser en dos direcciones y el "nuevo" laser que fue
 * retornado lo agregamos a la coleccion de laseres del tablero
 */
class BloqueDeVidrio implements Bloque {
    @Override
    public void interactuarLaser(Laser laser, Tablero tablero) {
        laser.puedoContinuar();
        Laser newLaser = laser.difractarLaser();
        tablero.addLaser(newLaser);
    }

    @Override
    public boolean esMovible() {
        return true;
    }
}

/**
 * Absorbe el laser
 */
class BloqueOpacoMovil implements Bloque {
    @Override
    public void interactuarLaser(Laser laser, Tablero tablero) {
        laser.fuiAbsorbido();
    }

    @Override
    public boolean esMovible() {
        return true;
    }
}

/**
 * Absorbe el laser. No se puede mover.
 */
class BloqueOpacoFijo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser, Tablero tablero) {
        laser.moverPosicion();
        laser.fuiAbsorbido();
    }

    @Override
    public boolean esMovible() {
        return false;
    }
}

/**
 * Refleja los lasers como un espejo.
 */
class BloqueEspejo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser, Tablero tablero) {

        laser.puedoContinuar();
        laser.reflejarLaser();
    }

    @Override
    public boolean esMovible() {
        return true;
    }
}
