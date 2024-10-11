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
        if(isOutOfBounds(columna, fila)){
            return null;
        } else {
            return grilla[columna][fila];
        }
    }

    public boolean isOutOfBounds(int x, int y){
        if (x >= 0 && x < grilla.length && y >= 0 && y < grilla[0].length) {
            return false;
        } else {
            return true;
        }
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
        this.grilla = new Celda[(lineas.getFirst().length())*2][filas*2];

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

                Posicion pos00 = new Posicion(2*col, 2*row);
                this.grilla[2*col][2*row] = crearCelda(caracter, pos00);

                Posicion pos10 = new Posicion((2*col)+1, 2*row);
                this.grilla[(2*col)+1][2*row] = crearCelda(caracter, pos10);

                Posicion pos01 = new Posicion(2*col, (2*row)+1);
                this.grilla[2*col][(2*row)+1] = crearCelda(caracter, pos01);

                Posicion pos11 = new Posicion((2*col)+1, (2*row)+1);
                this.grilla[(2*col)+1][(2*row)+1] = crearCelda(caracter, pos11);
            }
        }
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
                Posicion pos = new Posicion(col, fila);
                addLaser(new Laser(pos, direction));
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


    public void moverBloque(Posicion fromPos, Posicion toPos) {
        Celda fromCelda00 = getCelda(2*fromPos.getCoordX(), 2*fromPos.getCoordY());
        Celda toCelda00 = getCelda(2*toPos.getCoordX(), 2*toPos.getCoordY());

        Celda fromCelda10 = getCelda((2*fromPos.getCoordX())+1, 2*fromPos.getCoordY());
        Celda toCelda10 = getCelda((2*toPos.getCoordX())+1, 2*toPos.getCoordY());

        Celda fromCelda01 = getCelda(2*fromPos.getCoordX(), (2*fromPos.getCoordY())+1);
        Celda toCelda01 = getCelda(2*toPos.getCoordX(), (2*toPos.getCoordY())+1);

        Celda fromCelda11 = getCelda((2*fromPos.getCoordX())+1, (2*fromPos.getCoordY())+1);
        Celda toCelda11 = getCelda((2*toPos.getCoordX())+1, (2*toPos.getCoordY())+1);

        // Verifica si el bloque se puede mover
        if (fromCelda00.tieneBloque() && toCelda00.getPiso() && !toCelda00.tieneBloque()) {

            Bloque bloque00 = fromCelda00.getBloque();
            fromCelda00.quitarBloque(); // Quitar bloque de la celda original en 2x,2y
            toCelda00.ponerBloque(bloque00); // Poner bloque en la nueva celda

            Bloque bloque10 = fromCelda10.getBloque();
            fromCelda10.quitarBloque(); // Quitar bloque de la celda original en 2x+1,2y
            toCelda10.ponerBloque(bloque10); // Poner bloque en la nueva celda

            Bloque bloque01 = fromCelda01.getBloque();
            fromCelda01.quitarBloque(); // Quitar bloque de la celda original en 2x,2y+1
            toCelda01.ponerBloque(bloque01); // Poner bloque en la nueva celda

            Bloque bloque11 = fromCelda11.getBloque();
            fromCelda11.quitarBloque(); // Quitar bloque de la celda original en 2x+1,2y+1
            toCelda11.ponerBloque(bloque11); // Poner bloque en la nueva celda

        }
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

        // Si la siguiente posicion, esta fuera, no se avanza el laser
        if(isOutOfBounds(nextPos.getCoordX(), nextPos.getCoordY())) return;

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
