package packlasers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @org.junit.jupiter.api.Test
    void cargarBloquesYPisos() {
        Tablero tablero = new Tablero("level1.dat");

        // Asegúrate de que la celda (0,2) tenga un bloque espejo
        assertTrue(tablero.getCelda(0, 2).tieneBloque());
        assertTrue(tablero.getCelda(0, 2).getBloque() instanceof BloqueEspejo);

        // Asegúrate de que la celda (0,0) esté vacía
        assertFalse(tablero.getCelda(0, 0).tieneBloque());

        // Asegúrate de que la celda (2,1) tenga un bloque espejo
        assertTrue(tablero.getCelda(2, 1).tieneBloque());
        assertTrue(tablero.getCelda(2, 1).getBloque() instanceof BloqueEspejo);

        // Asegúrate de que la celda (3,2) tenga un bloque opaco fijo
        assertTrue(tablero.getCelda(3, 2).tieneBloque());
        assertTrue(tablero.getCelda(3, 2).getBloque() instanceof BloqueOpacoFijo);

        // Asegúrate de que la celda (4,1) esté vacía
        assertFalse(tablero.getCelda(4, 1).tieneBloque());

        // Asegúrate de que la celda (4,3) tenga un bloque espejo
        assertTrue(tablero.getCelda(5, 1).tieneBloque());
        assertTrue(tablero.getCelda(5, 1).getBloque() instanceof BloqueEspejo);
    }

    @Test
    void cargarEmisoresYObjetivos() {
        Tablero tablero = new Tablero("level1.dat");

        Laser laser = tablero.getLasers().getFirst(); //Obtiene el primer laser
        assertNotNull(laser);
        assertEquals(0, laser.currentPosition().getCoordX());
        assertEquals(1, laser.currentPosition().getCoordY());
        assertEquals(Direccion.SE, laser.getDireccion());

        Target target = tablero.getTarget().getFirst();
        assertNotNull(target);
        assertEquals(8, target.getPosicion().getCoordX());
        assertEquals(9, target.getPosicion().getCoordY());
    }
}