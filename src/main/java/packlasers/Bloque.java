package packlasers;

public interface Bloque {
    /**
     * Metodo que define cómo interactúa el bloque con un láser
     * PD: si interactuar con un bloque implica generar un nuevo
     * laser, lo devolvemos. En caso contrario, retornamos null
     * y el tablero se encargara de manejar dicha situacion.
     */
    Laser interactuarLaser(Laser laser);

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
    public Laser interactuarLaser(Laser laser) {
        laser.puedoContinuar();
        laser.refractarLaser();
        return null;
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
    public Laser interactuarLaser(Laser laser) {
        laser.puedoContinuar();
        return laser.difractarLaser();
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
    public Laser interactuarLaser(Laser laser) {
        laser.fuiAbsorbido();
        return null;
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
    public Laser interactuarLaser(Laser laser) {
        laser.moverPosicion();
        laser.fuiAbsorbido();
        return null;
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
    public Laser interactuarLaser(Laser laser) {
        laser.puedoContinuar();
        laser.reflejarLaser();
        return null;
    }

    @Override
    public boolean esMovible() {
        return true;
    }
}
