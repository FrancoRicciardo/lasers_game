package packlasers;

import java.util.ArrayList;

public class Tablero {
    private Celda[][] grilla;
    private ArrayList<Laser> lasers;
    private ArrayList<Target> targets;

    public Tablero(String filePath) {
        this.lasers = new ArrayList<>();
        this.targets = new ArrayList<>();
        loadLevel(filePath);
    }

    private void loadLevel(String filePath) {
        // TODO: carga el nivel entero (osea la grilla)
    }

    private void addLaser(Laser laser) {
        this.lasers.add(laser);
    }

    private void addTarget(Target target) {
        this.targets.add(target);
    }

    public void moverBloque(int fromX, int fromY, int toX, int toY) {
        Bloque block = this.grilla[fromX][fromY].getBloque();
        if (block != null && block.esMovible()) {
            this.grilla[toX][toY].ponerBloque(block);  // Mueve el bloque
            this.grilla[fromX][fromY].quitarBloque();  // Limpia la celda original
        } else {
            System.out.println("El bloque no se puede mover.");
        }
    }

    public void chequearVictoria() {
        //TODO: si todos los targets fueron alcanzados => WIN
    }
}
