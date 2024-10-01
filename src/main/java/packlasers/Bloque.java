package packlasers;

public interface Bloque {
    // Metodo que define cómo interactúa el bloque con un láser
    void interactuarLaser(Laser laser);

    // Metodo que indica si el bloque es móvil o no
    boolean esMovible();
}

class BloqueDeCristal implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {

        laser.puedoContinuar();
        // Refracta el laser, continuando en linea recta por el extremo opuesto
        laser.refractarLaser();

    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

class BloqueDeVidrio implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // Difracta el láser en dos direcciones:

        laser.puedoContinuar();
        // 1) Refleja el láser como un espejo
        laser.reflejarLaser();

        // 2) Deja que el láser principal siga en la misma dirección
        laser.moverPosicion();
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

class BloqueOpacoMovil implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        laser.fuiAbsorbido();
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

class BloqueOpacoFijo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        laser.fuiAbsorbido();
    }

    @Override
    public boolean esMovible() {
        return false; // NO es movible
    }
}

class BloqueEspejo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {

        laser.puedoContinuar();
        // Refleja el láser como un espejo
        laser.reflejarLaser();
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}
