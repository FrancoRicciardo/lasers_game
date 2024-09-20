package packlasers;

public class Main {

    public static void main(String[] args) {

        Tablero nivel1 = new Tablero("level1.dat");
        Game.addLevel(nivel1);

        Tablero nivel2 = new Tablero("level2.dat");
        Game.addLevel(nivel2);

        Tablero nivel3 = new Tablero("level3.dat");
        Game.addLevel(nivel3);

        Tablero nivel4 = new Tablero("level4.dat");
        Game.addLevel(nivel4);

        Tablero nivel5 = new Tablero("level5.dat");
        Game.addLevel(nivel5);

        Tablero nivel6 = new Tablero("level6.dat");
        Game.addLevel(nivel6);

        System.out.println("Que nivel desea jugar? (Elija del 1 al 6)");
        int i = 1; //leer de pantalla;
        while (!Game.getSalir()) {
            Game.jugar(Game.getLevel(i-1));
        }
    }
}
