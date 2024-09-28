package packlasers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Celda[][] grilla;
    private final ArrayList<Laser> lasers;
    private final ArrayList<Target> targets;

    public Tablero(String filePath) {
        this.lasers = new ArrayList<>();
        this.targets = new ArrayList<>();
        try {
            loadLevel(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Laser> getLaser() {
        return lasers;
    }

    public ArrayList<Target> getTarget() {
        return targets;
    }


    public Celda getCelda(int fila, int columna) {
        if (fila >= 0 && fila < grilla.length && columna >= 0 && columna < grilla[0].length) {
            return grilla[fila][columna];
        }
        return null;
    }

    // Carga el nivel entero (osea la grilla)
    public void loadLevel(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new FileNotFoundException(filePath);
        }

        List<String> lineas = leerLineasDelArchivo(inputStream);
        int filas = 0;

        // Contar filas hasta que se encuentre una línea vacía
        while (filas < lineas.size() && !lineas.get(filas).isEmpty()) {
            filas++;
        }

        // Inicializar la grilla con el número de filas y el tamaño de la primera línea
        this.grilla = new Celda[filas][lineas.getFirst().length()];

        // Cargar la primera sección de bloques y pisos
        cargarBloquesYPisos(lineas.subList(0, filas));

        // Cargar la sección de emisores y objetivos
        if (filas < lineas.size()) {
            cargarEmisoresYObjetivos(lineas.subList(filas + 1, lineas.size()));
        }
    }

    private List<String> leerLineasDelArchivo(InputStream inputStream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lineas = new ArrayList<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            return lineas;
        }
    }

    private void cargarBloquesYPisos(List<String> lineas) {
        for (int row = 0; row < lineas.size(); row++) {
            String linea = lineas.get(row);
            for (int col = 0; col < Math.min(linea.length(), this.grilla[row].length); col++) {
                char caracter = linea.charAt(col);
                this.grilla[row][col] = crearCelda(caracter);
            }
        }
    }

    private Celda crearCelda(char caracter) {
        Celda celda = new Celda(true);
        switch (caracter) {
            case 'F':
                celda.ponerBloque(new BloqueOpacoFijo());
                break;
            case 'B':
                celda.ponerBloque(new BloqueOpacoMovil());
                break;
            case 'R':
                celda.ponerBloque(new BloqueEspejo());
                break;
            case 'G':
                celda.ponerBloque(new BloqueDeVidrio());
                break;
            case 'C':
                celda.ponerBloque(new BloqueDeCristal());
                break;
            case '.':
                celda = new Celda(true); // Piso
                break;
            default:
                celda = new Celda(false); // Sin piso
                break;
        }
        return celda;
    }

    private void cargarEmisoresYObjetivos(List<String> lineas) {
        for (String linea : lineas) {
            if (linea.isEmpty()) continue;
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
            this.grilla[toX][toY].ponerBloque(block);
            this.grilla[fromX][fromY].quitarBloque();
        }
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
