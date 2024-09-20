package packlasers;

import java.util.ArrayList;

public class Game {
    private static boolean salir;
    private static ArrayList<Tablero> niveles;

    public Game() {
        niveles = new ArrayList<>();
    }

    public static boolean getSalir() {
        return salir;
    }

    public static void setSalir(boolean salir) {
        Game.salir = salir;
    }

    public static Tablero getLevel(int posicion){
        return niveles.get(posicion);
    }

    public static void addLevel(Tablero nivel){
        niveles.add(nivel);
    }

    public static void jugar(Tablero nivel){
        // TODO: probablamente sera jugar un turno de moverBloque

        // si llegue a todos los targets:
            setSalir(true);
            return;
    }
}

