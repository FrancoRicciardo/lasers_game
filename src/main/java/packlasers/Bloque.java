package packlasers;

public interface Bloque {
    // Metodo que define cómo interactúa el bloque con un láser
    void interactuarLaser(Laser laser);

    // Metodo que indica si el bloque es móvil o no
    boolean esMovible();

    // Metodo para obtener el nombre del bloque
    String getId();
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

    @Override
    public String getId() {
        return "C";
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

    @Override
    public String getId() {
        return "G";
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

    @Override
    public String getId() {
        return "B";
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

    @Override
    public String getId() {
        return "F";
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

    @Override
    public String getId() {
        return "R";
    }
}
