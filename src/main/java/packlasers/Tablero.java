package packlasers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tablero {
    private Celda[][] grilla;
    private ArrayList<Laser> lasers;
    private ArrayList<Target> targets;

    public Tablero(String filePath) {
        this.lasers = new ArrayList<>();
        this.targets = new ArrayList<>();

        try {
            loadLevel(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Carga el nivel entero (osea la grilla)
    public void loadLevel(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String linea;
        int row = 0;

        // Leo la primera sección (bloques y pisos)
        while ((linea = br.readLine()) != null && !linea.isEmpty()) {
            for (int col = 0; col < linea.length(); col++) {
                char caracter = linea.charAt(col);
                switch (caracter) {
                    case 'F':
                        this.grilla[row][col] = new Celda(true);
                        this.grilla[row][col].ponerBloque(new BloqueOpacoFijo());
                        break;
                    case 'B':
                        this.grilla[row][col] = new Celda(true);
                        this.grilla[row][col].ponerBloque(new BloqueOpacoMovil());
                        break;
                    case 'R':
                        this.grilla[row][col] = new Celda(true);
                        this.grilla[row][col].ponerBloque(new BloqueEspejo());
                        break;
                    case 'G':
                        this.grilla[row][col] = new Celda(true);
                        this.grilla[row][col].ponerBloque(new BloqueDeVidrio());
                        break;
                    case 'C':
                        this.grilla[row][col] = new Celda(true);
                        this.grilla[row][col].ponerBloque(new BloqueDeCristal());
                        break;
                    case '.':
                        this.grilla[row][col] = new Celda(true); // Piso vacío
                        break;
                    default:
                        this.grilla[row][col] = new Celda(false); // Sin piso
                        break;
                }
            }
            row++;
        }

        // Leo la segunda sección (emisores y objetivos)
        while ((linea = br.readLine()) != null) {
            String[] tokens = linea.split(" ");
            if (tokens[0].equals("E")) {
                int col = Integer.parseInt(tokens[1]);
                int fila = Integer.parseInt(tokens[2]);
                Direccion direction = Direccion.valueOf(tokens[3]);
                addLaser(new Laser(col, fila, direction));
            } else if (tokens[0].equals("G")) {
                int col = Integer.parseInt(tokens[1]);
                int fila = Integer.parseInt(tokens[2]);
                addTarget(new Target(col, fila));
            }
        }
        br.close();
    }

    private void addLaser(Laser laser) {
        this.lasers.add(laser);
    }

    private void addTarget(Target target) {
        this.targets.add(target);
    }

    public Celda getCelda(int fila, int columna) {
        if (fila >= 0 && fila < grilla.length && columna >= 0 && columna < grilla[0].length) {
            return grilla[fila][columna];
        }
        return null;
    }

    public void moverBloque(Posicion fromPos, Posicion toPos) {
        Celda fromCelda = getCelda(fromPos.getCoordX(), fromPos.getCoordY());
        Celda toCelda = getCelda(toPos.getCoordX(), toPos.getCoordY());

        if(fromCelda == null || toCelda == null) return;
        if(!fromCelda.tieneBloque()) return;

        Bloque block = fromCelda.getBloque();
        if (block.esMovible() && toCelda.getPiso() && !toCelda.tieneBloque()) {
            toCelda.ponerBloque(block);  // Mueve el bloque
            fromCelda.quitarBloque();  // Limpia la celda original
        } else System.out.println("El bloque no se puede mover."); // Esta linea probablemente no sea un print, quizas un return
    }

    public boolean chequearVictoria() {
        for (Target target : targets) {
            if (!target.getMeAlcanzaron())
                return false; // Si algún objetivo no ha sido alcanzado, el nivel no está completo
        }
        return true; // Si todos los objetivos han sido alcanzados, el nivel está completo
    }

    public void moverLaser(Laser laser) {
        /* "Avanzo a mano" a la sig posicion para verificar que haya piso */
        Posicion nextPos = laser.currentPosition();
        nextPos.move(laser.getDireccion());
        Celda nextCelda = getCelda(nextPos.getCoordX(), nextPos.getCoordY());
        if(nextCelda == null) return;

        /* Mientras el laser este activo y haya piso en la siguiente posicion... */
        while (laser.isActive() && nextCelda.getPiso()) {

            laser.moverPosicion(); // Actualizar la posición del láser
            Bloque block = getCelda(laser.currentPosition().getCoordX(), laser.currentPosition().getCoordY()).getBloque();

            // Si hay un bloque, interactuar con el láser
            if (block != null) block.interactuarLaser(laser);

            // Verificar si el láser ha alcanzado un objetivo
            for (Target target : targets) {
                if (laser.currentPosition().equals(target.getPosicion()))
                    target.fuiAlcanzado(); // Marcar el objetivo como alcanzado
                else target.noFuiAlcanzado();
            }
        }
    }
}
