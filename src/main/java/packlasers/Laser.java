package packlasers;

import java.util.ArrayList;

public class Laser {
    private Posicion startPosition;
    private final Direccion startDirec;
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

    public Posicion getStartPosition(){
        return startPosition;
    }

    public Direccion getStartDirection(){
        return startDirec;
    }

    public void moverPosicion(){
        // Mueve el laser una posicion
        if(estoyActivo) {
            Posicion newPos = new Posicion(currentPosition().getCoordX(), currentPosition().getCoordY());
            newPos.move(direccion);
            trayectoria.add(newPos);
        }
    }

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
    }

    public void refractarLaser() {
        /* Refracta el laser dependiendo su direccion actual */
        Posicion newPos = new Posicion(currentPosition().getCoordX(), currentPosition().getCoordY());
        switch (direccion){
            case NE, SE:
                if (newPos.getCoordX() % 2 == 0) {
                    // Si la coordenada x es par, el láser sigue avanzando en y
                    newPos.setCoordY(newPos.getCoordY() + 2);
                } else {
                    // Si la coordenada x es impar, el láser sigue avanzando en x
                    newPos.setCoordX(newPos.getCoordX() + 2);
                }
                break;
            case NW, SW:
                if (newPos.getCoordX() % 2 == 0) {
                    // Si la coordenada x es par, el láser sigue avanzando en x
                    newPos.setCoordX(newPos.getCoordX() - 2);
                } else {
                    // Si la coordenada x es impar, el láser sigue avanzando en y
                    newPos.setCoordY(newPos.getCoordY() + 2);
                }
                break;
        }

        // Agrego esa posicion a la trayectoria
        trayectoria.add(newPos);

        // Y avanzo normalmente una posicion en esa direccion
        Posicion newPos2 = new Posicion (newPos.getCoordX(), newPos.getCoordY());
        newPos2.move(direccion);
        trayectoria.add(newPos2);
    }

    public void difractarLaser (Tablero tablero){
        // Difracta el láser en dos direcciones:
        Posicion newPos = new Posicion(currentPosition().getCoordX(), currentPosition().getCoordY());

        // 1) Refleja el láser creando un nuevo rayo reflejado en la dirección correspondiente
        Direccion direccionReflejada;
        switch (direccion) {
            case NE:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego de abajo"
                    direccionReflejada = Direccion.SE;
                }
                else {
                    direccionReflejada = Direccion.NW; // Si coord y es par, el laser "pego del costado izquierdo"
                }
                break;
            case NW:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego del costado derecho"
                    direccionReflejada = Direccion.NE;
                }
                else {
                    direccionReflejada = Direccion.SW; // Si coord y es par, el laser "pego de abajo"
                }
                break;
            case SE:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego de arriba"
                    direccionReflejada = Direccion.NE;
                }
                else {
                    direccionReflejada = Direccion.SW; // Si coord y es par, el laser "pego del costado izquierdo"
                }
                break;
            case SW:
                if (newPos.getCoordX() % 2 == 0) { // Si coord x es par, el laser "pego del costado derecho"
                    direccionReflejada = Direccion.SE;
                }
                else {
                    direccionReflejada = Direccion.NW; // Si coord y es par, el laser "pego de arriba"
                }
                break;
            default:
                return;
        }
        // Crea un nuevo láser reflejado y lo agregamos a la colección de láseres del tablero
        Laser laserReflejado = new Laser(newPos, direccionReflejada);
        tablero.addLaser(laserReflejado);

        // 2) Y deja correr normalmente el "laser original"
        moverPosicion();

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

