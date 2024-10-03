package packlasers;

public class Game {
    private static boolean salir;

    public Game() {
        salir = false;
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

    public static boolean getSalir() {
        return salir;
    }

    public static void setSalir(boolean salir) {
        Game.salir = salir;
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

