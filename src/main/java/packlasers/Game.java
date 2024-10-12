package packlasers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Tablero> niveles;
    private Tablero tableroActual; //= new Tablero("level1.dat");

    public Game() {
        niveles = new ArrayList<>();
        cargarNiveles();
        tableroActual = niveles.getFirst();
    }

    private void cargarNiveles() {
        niveles.add(new Tablero("level1.dat"));
        niveles.add(new Tablero("level2.dat"));
        niveles.add(new Tablero("level3.dat"));
        niveles.add(new Tablero("level4.dat"));
        niveles.add(new Tablero("level5.dat"));
        niveles.add(new Tablero("level6.dat"));
    }

    private String getNivelDat(int nivel){
        return switch (nivel) {
            case 1 -> "level1.dat";
            case 2 -> "level2.dat";
            case 3 -> "level3.dat";
            case 4 -> "level4.dat";
            case 5 -> "level5.dat";
            case 6 -> "level6.dat";
            default -> null;
        };
    }

    // Obtener un Tablero dado el índice
    public Tablero getNivel(int index) {
        if (index >= 0 && index < niveles.size()) {
            return niveles.get(index);
        }
        return null; // Retorna null si el índice está fuera de rango
    }


    public String getNivelFXML(int nivel) {
        return switch (nivel) {
            case 1 -> "/vistaNivel1.fxml";
            case 2 -> "/vistaNivel2.fxml";
            case 3 -> "/vistaNivel3.fxml";
            case 4 -> "/vistaNivel4.fxml";
            case 5 -> "/vistaNivel5.fxml";
            case 6 -> "/vistaNivel6.fxml";
            default -> null;
        };
    }

    public void setTableroActual(Tablero tablero){
        tableroActual = tablero;
    }

    public Tablero getTableroActual(){
        return tableroActual;
    }

    public void reiniciarNivel(int nivel){
        try {
            niveles.get(nivel-1).loadLevel(getNivelDat(nivel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

