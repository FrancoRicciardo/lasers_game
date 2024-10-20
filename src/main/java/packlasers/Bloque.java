package packlasers;

public interface Bloque {
    /**
     * Metodo que define cómo interactúa el bloque con un láser
     */
    void interactuarLaser(Laser laser, Tablero tablero);

    /*
     Metodo que indica si el bloque es móvil o no
     */
    boolean esMovible();
}

class BloqueDeCristal implements Bloque {
    /*
    Refracta el laser, continuando en linea recta por el extremo opuesto
    */

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

class BloqueDeVidrio implements Bloque {
    /*
    Difracta el láser en dos direcciones y el "nuevo" laser que fue
    retornado lo agregamos a la coleccion de laseres del tablero
    */

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

class BloqueOpacoMovil implements Bloque {
    /*
    Absorbe el laser
     */

    @Override
    public void interactuarLaser(Laser laser, Tablero tablero) {
        laser.fuiAbsorbido();
    }

    @Override
    public boolean esMovible() {
        return true;
    }

}

class BloqueOpacoFijo implements Bloque {
    /*
    Absorbe el laser. No se puede mover.
     */

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

class BloqueEspejo implements Bloque {
    /*
    Refleja los lasers como un espejo.
     */

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
