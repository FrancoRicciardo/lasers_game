package packlasers;

public interface Bloque {
    // Metodo que define cómo interactúa el bloque con un láser
    void interactuarLaser(Laser laser);

    // Metodo que indica si el bloque es móvil o no
    boolean esMovible();
}

public class BloqueDeCristal implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // TODO: Deja que el láser pase a través
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

public class BloqueDeVidrio implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // Difracta el láser en dos direcciones
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

public class BloqueOpacoMovil implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // TODO: Absorbe el láser, no hace nada
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}

public class BloqueOpacoFijo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // TODO: Absorbe el láser, no hace nada
    }

    @Override
    public boolean esMovible() {
        return false; // NO es movible
    }
}

public class BloqueEspejo implements Bloque {
    @Override
    public void interactuarLaser(Laser laser) {
        // TODO: Cambia la dirección del láser
    }

    @Override
    public boolean esMovible() {
        return true; // Es movible
    }
}