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

    public Celda getCelda(int columna, int fila) {
        if (columna >= 0 && columna < grilla.length && fila >= 0 && fila < grilla[0].length) {
            return grilla[columna][fila];
        }
        return null;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public ArrayList<Target> getTarget() {
        return targets;
    }

    // Carga el nivel entero (osea la grilla)
    public void loadLevel(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new FileNotFoundException(filePath);
        }

        List<String> lineas = leerLineasDelArchivo(inputStream);
        int filas = 0;

        // Cuento las filas hasta que se encuentre una línea vacía
        while (filas < lineas.size() && !lineas.get(filas).isEmpty()) {
            filas++;
        }

        // Inicializo la grilla con el número de filas y el tamaño de la primera línea
        this.grilla = new Celda[lineas.getFirst().length()][filas];

        // Cargar la primera seccion de bloques y pisos
        cargarBloquesYPisos(lineas.subList(0, filas));

        // Cargar la seccion de emisores y objetivos
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
            for (int col = 0; col < linea.length(); col++) {
                char caracter = linea.charAt(col);
                Posicion posicion = new Posicion(col, row);
                this.grilla[col][row] = crearCelda(caracter, posicion);
            }
        }
    }

    public Celda[][] getGrilla(){
        return grilla;
    }

    private Celda crearCelda(char caracter, Posicion posicion) {
        Celda celda;
        switch (caracter) {
            case 'F':
                celda = new Celda(true, posicion);
                celda.ponerBloque(new BloqueOpacoFijo());
                break;
            case 'B':
                celda = new Celda(true, posicion);
                celda.ponerBloque(new BloqueOpacoMovil());
                break;
            case 'R':
                celda = new Celda(true, posicion);
                celda.ponerBloque(new BloqueEspejo());
                break;
            case 'G':
                celda = new Celda(true, posicion);
                celda.ponerBloque(new BloqueDeVidrio());
                break;
            case 'C':
                celda = new Celda(true, posicion);
                celda.ponerBloque(new BloqueDeCristal());
                break;
            case '.':
                celda = new Celda(true, posicion); // Celda vacía con piso
                break;
            default:
                celda = new Celda(false, posicion); // Celda sin piso
                break;
        }
        return celda;
    }

    private void cargarEmisoresYObjetivos(List<String> lineas) {
        for(String linea : lineas) {
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


    public boolean moverBloque(Posicion fromPos, Posicion toPos) {
        Celda fromCelda = getCelda(fromPos.getCoordX(), fromPos.getCoordY());
        Celda toCelda = getCelda(toPos.getCoordX(), toPos.getCoordY());

        // Verifica si el bloque se puede mover
        if (fromCelda.tieneBloque() && toCelda.getPiso() && !toCelda.tieneBloque()) {
            Bloque bloque = fromCelda.getBloque();
            fromCelda.quitarBloque(); // Quitar bloque de la celda original
            toCelda.ponerBloque(bloque); // Poner bloque en la nueva celda
            return true;
        }
        return false;
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
        Posicion nextPos = new Posicion(laser.currentPosition().getCoordX(), laser.currentPosition().getCoordY());
        nextPos.move(laser.getDireccion());

        // Verifica si la celda siguiente existe
        Celda nextCelda = getCelda(nextPos.getCoordX(), nextPos.getCoordY());
        if (nextCelda == null) return;

        // Mientras el láser esté activo y haya piso en la siguiente posición
        while (laser.isActive() && nextCelda.getPiso()) {

            // Actualiza la posición del láser
            laser.moverPosicion();

            Bloque block = getCelda(laser.currentPosition().getCoordX(), laser.currentPosition().getCoordY()).getBloque();
            if (block != null) {
                // Interactuar con el bloque si existe bloque
                block.interactuarLaser(laser);
            }

            // Verificar si el láser ha alcanzado un objetivo
            for (Target target : targets) {
                if (laser.currentPosition().equals(target.getPosicion())) {
                    target.fuiAlcanzado();
                } else {
                    target.noFuiAlcanzado();
                }
            }

            // Prever la próxima celda (actualizar nextPos para continuar)
            nextPos = new Posicion(laser.currentPosition().getCoordX(), laser.currentPosition().getCoordY());
            nextPos.move(laser.getDireccion());
            nextCelda = getCelda(nextPos.getCoordX(), nextPos.getCoordY());

            // Salir si la celda siguiente es nula (fuera de los límites)
            if (nextCelda == null) return;
        }
    }
}
