package packlasers;

import java.util.ArrayList;

public class Laser {
    private Posicion startPosition;
    private Direccion startDirec;
    private Direccion direccion;
    private final ArrayList<Posicion> trayectoria;
    private boolean estoyActivo;

    public Laser(Posicion startPos, Direccion direccion) {
        this.startPosition = startPos;
        this.startDirec = direccion;
        this.direccion = direccion;
        this.estoyActivo = true;
        this.trayectoria = new ArrayList<>();
        this.trayectoria.add(startPosition);
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

    public Direccion getDireccion() {
        return this.direccion;
    }

    public ArrayList<Posicion> getTrayectoria() {
        return this.trayectoria;
    }

    public Posicion currentPosition(){
        return this.trayectoria.getLast();
    }

    public void moverPosicion(){
        // Mueve el laser una posicion
        if(estoyActivo) {
            Posicion newPos = new Posicion(currentPosition().getCoordX(), currentPosition().getCoordY());
            newPos.move(direccion);
            trayectoria.add(newPos);
        }
    }

    /*public void reflejarLaser() {
        // Refleja el laser dependiendo su direccion actual y la actualiza
        Posicion newPos = new Posicion(currentPosition().getCoordX(), currentPosition().getCoordY());
        this.direccion = switch (direccion) {
            case NE -> {
                if (newPos.getCoordX() % 2 == 0) // Si coord x es par, el laser "pego del costado izquierdo"
                    yield Direccion.NW;
                else
                    yield Direccion.SE; // Si coord y es par, el laser "pego de abajo"
            }
            case NW -> {
                if (newPos.getCoordX() % 2 == 0) // Si coord x es par, el laser "pego del costado derecho"
                    yield Direccion.NE;
                else
                    yield Direccion.SW; // Si coord y es par, el laser "pego de abajo"
            }
            case SE -> {
                if (newPos.getCoordX() % 2 == 0) // Si coord x es par, el laser "pego del costado izquierdo"
                    yield Direccion.SW;
                else
                    yield Direccion.NE; // Si coord y es par, el laser "pego de arriba"
            }
            case SW -> {
                if (newPos.getCoordX() % 2 == 0) // Si coord x es par, el laser "pego del costado derecho"
                    yield Direccion.SE;
                else
                    yield Direccion.NW; // Si coord y es par, el laser "pego de abajo"
            }
        };

        // Avanzo una posicion en esa direccion nueva
        moverPosicion();
    } */

    public void reflejarLaser() {
        // Refracta el laser dependiendo su direccion actual
        Posicion newPos = new Posicion(currentPosition().getCoordX(), currentPosition().getCoordY());
        switch (direccion) {
            case NE:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego de abajo"
                    this.direccion = Direccion.SE;
                    newPos.incX();
                }
                else {
                    this.direccion = Direccion.NW; // Si coord y es par, el laser "pego del costado izquierdo"
                    newPos.decY();
                }
                break;
            case NW:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego del costado derecho"
                    this.direccion = Direccion.NE;
                    newPos.decY();
                }
                else {
                    this.direccion = Direccion.SW; // Si coord y es par, el laser "pego de abajo"
                    newPos.decX();
                }
                break;
            case SE:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego de arriba"
                    this.direccion = Direccion.NE;
                    newPos.incX();
                }
                else {
                    this.direccion = Direccion.SW; // Si coord y es par, el laser "pego del costado izquierdo"
                    newPos.incY();
                }
                break;
            case SW:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego del costado derecho"
                    this.direccion = Direccion.SE;
                    newPos.incY();
                }
                else {
                    this.direccion = Direccion.NW; // Si coord y es par, el laser "pego de arriba"
                    newPos.decX();
                }
                break;
        }
        // Agrego esa posicion a la trayectoria
        trayectoria.add(newPos);

        // y luego avanzo una posicion en esa direccion nueva
        //moverPosicion();

        // Avanzo una posicion en esa direccion nueva
        //Posicion newPos2 = new Posicion(newPos.getCoordX(), newPos.getCoordY());
        //newPos2.move(direccion);
        //trayectoria.add(newPos2);
    }

    public void refractarLaser() {
        /* Refracta el laser dependiendo su direccion actual */
        Posicion newPos = new Posicion(currentPosition().getCoordX(), currentPosition().getCoordY());
        switch (direccion){
            case NE, SE:
                if (newPos.getCoordX() % 2 == 0) // Si coord x es par, "sigo de largo" en x
                    newPos.setCoordX(newPos.getCoordX() + 2);
                else   /* Si coord y es par, "sigo de largo" en y */
                    newPos.setCoordY(newPos.getCoordY() + 2);
                break;
            case NW, SW:
                if (newPos.getCoordX() % 2 == 0) // Si coord x es par, "sigo de largo" en x
                    newPos.setCoordX(newPos.getCoordX() - 2);
                else   /* Si coord y es par, "sigo de largo" en y */
                    newPos.setCoordY(newPos.getCoordY() + 2);
                break;
        }

        // Agrego esa posicion a la trayectoria
        trayectoria.add(newPos);

        /* Y avanzo normalmente una posicion en esa direccion */
        Posicion newPos2 = new Posicion (newPos.getCoordX(), newPos.getCoordY());
        newPos2.move(direccion);
        trayectoria.add(newPos2);
    }

    public void reiniciarTrayectoria(){
        this.estoyActivo = true;
        Posicion startPos = new Posicion(startPosition.getCoordX(), startPosition.getCoordY());
        this.trayectoria.clear();
        this.startPosition = startPos;
        this.trayectoria.add(startPos);
        this.direccion = startDirec;
    }
}

