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
                        grilla[row][col] = new Celda(true);
                        grilla[row][col].ponerBloque(new BloqueOpacoFijo());
                        break;
                    case 'B':
                        grilla[row][col] = new Celda(true);
                        grilla[row][col].ponerBloque(new BloqueOpacoMovil());
                        break;
                    case 'R':
                        grilla[row][col] = new Celda(true);
                        grilla[row][col].ponerBloque(new BloqueEspejo());
                        break;
                    case 'G':
                        grilla[row][col] = new Celda(true);
                        grilla[row][col].ponerBloque(new BloqueDeVidrio());
                        break;
                    case 'C':
                        grilla[row][col] = new Celda(true);
                        grilla[row][col].ponerBloque(new BloqueDeCristal());
                        break;
                    case '.':
                        grilla[row][col] = new Celda(true); // Piso vacío
                        break;
                    default:
                        grilla[row][col] = new Celda(false); // Sin piso
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

    public void moverBloque(int fromX, int fromY, int toX, int toY) {
        Bloque block = this.grilla[fromX][fromY].getBloque();
        if (block != null && block.esMovible()) {
            this.grilla[toX][toY].ponerBloque(block);  // Mueve el bloque
            this.grilla[fromX][fromY].quitarBloque();  // Limpia la celda original
        } else System.out.println("El bloque no se puede mover."); // Esta linea se borrará
    }

    public boolean chequearVictoria() {
        for (Target target : targets) {
            if (!target.getMeAlcanzaron())
                return false; // Si algún objetivo no ha sido alcanzado, el nivel no está completo
        }
        return true; // Si todos los objetivos han sido alcanzados, el nivel está completo
    }

    public void moveLaser(Laser laser) {
        while (laser.isActive()) {
            int nextX = laser.getCoordX();
            int nextY = laser.getCoordY();

            laser.mover(); // Actualizar la posición del láser
            Bloque block = grilla[nextX][nextY].getBloque();

            // Si hay un bloque, interactuar con el láser
            if (block != null) block.interactuarLaser(laser);

            // Verificar si el láser ha alcanzado un objetivo
            for (Target target : targets) {
                if (target.getCoordX() == laser.getCoordX() && target.getCoordY() == laser.getCoordY())
                    target.fuiAlcanzado(); // Marcar el objetivo como alcanzado
                else target.noFuiAlcanzado();
            }
        }
    }
}
