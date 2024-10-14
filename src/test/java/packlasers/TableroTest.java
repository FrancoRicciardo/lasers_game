package packlasers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    void cargarBloquesYPisos() {
        Tablero tablero = new Tablero("level1.dat");

        // Chequea q las celdas (4,0) (5,0) (4,1) (5,1) tengan un bloque espejo:
        // ya que estas son las que representan el (0,2) en la UI
        assertTrue(tablero.getCelda(4, 0).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(4, 0).getBloque());

        assertTrue(tablero.getCelda(5, 0).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(5, 0).getBloque());

        assertTrue(tablero.getCelda(4, 1).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(4, 1).getBloque());

        assertTrue(tablero.getCelda(5, 1).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(5, 1).getBloque());

        // Chequea que la celda (0,0) (0,1) (1,0) (1,1) esté vacía
        // ya que estas son las que representan el (0,0) en la UI
        assertFalse(tablero.getCelda(0, 0).tieneBloque());
        assertFalse(tablero.getCelda(0, 1).tieneBloque());
        assertFalse(tablero.getCelda(1, 0).tieneBloque());
        assertFalse(tablero.getCelda(1, 1).tieneBloque());

        // Chequea que la celda (2,4) (3,4) (2,5) (3,5) tengan un bloque espejo:
        // ya que estas son las que representan el (2,1) en la UI
        assertTrue(tablero.getCelda(2, 4).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(2, 4).getBloque());

        assertTrue(tablero.getCelda(3, 4).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(3, 4).getBloque());

        assertTrue(tablero.getCelda(2, 5).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(2, 5).getBloque());

        assertTrue(tablero.getCelda(3, 5).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(3, 5).getBloque());

        // Chequea que la celda (4,6) (5,6) (4,7) (5,7) tengan un bloque opaco fijo:
        // ya que estas son las que representan el (2,3) en la UI
        assertTrue(tablero.getCelda(4, 6).tieneBloque());
        assertInstanceOf(BloqueOpacoFijo.class, tablero.getCelda(4, 6).getBloque());

        assertTrue(tablero.getCelda(5, 6).tieneBloque());
        assertInstanceOf(BloqueOpacoFijo.class, tablero.getCelda(5, 6).getBloque());

        assertTrue(tablero.getCelda(4, 7).tieneBloque());
        assertInstanceOf(BloqueOpacoFijo.class, tablero.getCelda(4, 7).getBloque());

        assertTrue(tablero.getCelda(5, 7).tieneBloque());
        assertInstanceOf(BloqueOpacoFijo.class, tablero.getCelda(5, 7).getBloque());

        // Chequea que la celda (8,2) (9,2) (8,3) (9,3) este vacia:
        // ya que estas son las que representan el (4,1) en la UI
        assertNull(tablero.getCelda(8, 2));
        assertNull(tablero.getCelda(9, 2));
        assertNull(tablero.getCelda(8, 3));
        assertNull(tablero.getCelda(9, 3));

        // Chequea que la celda (2,10) (3,10) (2,11) (3,11) tengan un bloque espejo:
        // ya que estas son las que representan el (1,5) en la UI
        assertTrue(tablero.getCelda(2, 10).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(2, 10).getBloque());

        assertTrue(tablero.getCelda(3, 10).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(3, 10).getBloque());

        assertTrue(tablero.getCelda(2, 11).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(2, 11).getBloque());

        assertTrue(tablero.getCelda(3, 11).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(3, 11).getBloque());

        // Chequea que la celda (6,4) (7,4) (6,5) (7,5) tengan un bloque espejo:
        // ya que estas son las que representan el (3,2) en la UI
        assertTrue(tablero.getCelda(6, 4).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(6, 4).getBloque());

        assertTrue(tablero.getCelda(7, 4).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(7, 4).getBloque());

        assertTrue(tablero.getCelda(6, 5).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(6, 5).getBloque());

        assertTrue(tablero.getCelda(7, 5).tieneBloque());
        assertInstanceOf(BloqueEspejo.class, tablero.getCelda(7, 5).getBloque());
    }

    @Test
    void cargarEmisoresYObjetivos() {
        Tablero tablero = new Tablero("level1.dat");

        Laser laser = tablero.getLasers().getFirst();
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