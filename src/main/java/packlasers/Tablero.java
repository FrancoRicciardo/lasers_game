package packlasers;

import java.util.ArrayList;

public class Tablero {
    private Celda[][] grilla;
    private ArrayList<Laser> lasers;
    private ArrayList<Target> targets;

    public Tablero(int rows, int cols) {
        grilla = new Celda[rows][cols];
        lasers = new ArrayList<>();
        targets = new ArrayList<>();
    }

    public void addLaser(Laser laser) {
        lasers.add(laser);
    }

    public void addTarget(Target target) {
        targets.add(target);
    }

}
