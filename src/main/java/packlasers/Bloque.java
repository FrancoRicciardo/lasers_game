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
        // TODO: Deja que el láser pase a través
        laser.moverAlExtremo();
        laser.continuar();
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

class BloqueDeVidrio implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // Difracta el láser en dos direcciones
        laser.continuar(); // El laser que continua

        Laser laserReflejado = laser.reflejarLaser(); // Reflejo el laser

        laserReflejado.continuar(); // El laser reflejado continua
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

class BloqueOpacoMovil implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // TODO: Absorbe el láser, no hace nada
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

class BloqueOpacoFijo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // TODO: Absorbe el láser, no hace nada
    }

    @Override
    public boolean esMovible() {
        return false; // NO es movible
    }
}

class BloqueEspejo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // TODO: Cambia la dirección del láser
        Laser laserReflejado = laser.reflejarLaser();

        laserReflejado.continuar();
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}
