package packlasers;

import java.util.ArrayList;

public class Game {
    private static boolean salir;
    private static ArrayList<Tablero> niveles = new ArrayList<>();

    public Game() {
        salir = false;
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

    public static void jugarTurno(Tablero nivel){
        // TODO: probablamente sera jugar un turno de moverBloque
        while(!salir){
            // hago cosas, juego el turno

            // si llegue a todos los targets:
            setSalir(true);
        }
    }
}

