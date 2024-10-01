package packlasers;

import java.util.ArrayList;

public class Laser {
    private final Posicion startPosition;
    private Direccion direccion;
    private ArrayList<Posicion> trayectoria;
    private boolean estoyActivo;

    public Laser(int x, int y, Direccion direccion) {
        this.startPosition = new Posicion(x, y);
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
            Posicion currentPos = currentPosition();
            currentPos.move(direccion);
            trayectoria.add(currentPos);
        }
    }

    public void reflejarLaser() {
        /* Refleja el laser dependiendo su direccion actual y la actualiza */
        this.direccion = switch (direccion) {
            case NE -> Direccion.NW;
            case NW -> Direccion.NE;
            case SE -> Direccion.SW;
            case SW -> Direccion.SE;
        };

        /* Avanzo una posicion en esa direccion nueva */
        moverPosicion();
    }

    public void refractarLaser() {
        /* Refracta el laser dependiendo su direccion actual */
        Posicion currentPos = currentPosition();
        switch (direccion){
            case NE, SE:
                if (currentPos.getCoordX() % 2 == 0) // Si coord x es par, "sigo de largo" en x
                    currentPos.setCoordX(currentPos.getCoordX() + 2);
                else   /* Si coord y es par, "sigo de largo" en y */
                    currentPos.setCoordY(currentPos.getCoordY() + 2);
                break;
            case NW, SW:
                if (currentPos.getCoordX() % 2 == 0) // Si coord x es par, "sigo de largo" en x
                    currentPos.setCoordX(currentPos.getCoordX() - 2);
                else   /* Si coord y es par, "sigo de largo" en y */
                    currentPos.setCoordY(currentPos.getCoordY() + 2);
                break;
        }

        /* Avanzo una posicion en esa direccion nueva */
        currentPos.move(direccion);
        trayectoria.add(currentPos);
    }
}

enum Direccion {
    SE, SW, NE, NW
}