package packlasers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TableroTest {

    @Test
    void loadLevel() {
        Tablero tablero = new Tablero("level1.dat");
        // Comprueba que la celda (0, 2) tenga un bloque
        assertTrue(tablero.getCelda(0, 2).tieneBloque()); // Debería haber un bloque espejo

        // Comprueba que la celda (3, 2) tenga un bloque
        assertTrue(tablero.getCelda(3, 2).tieneBloque()); // Debería haber un bloque opaco fijo

        // Comprueba que la celda (0, 0) no tenga bloque
        assertFalse(tablero.getCelda(0, 0).tieneBloque());

        assertEquals(1, tablero.getLaser().size()); // Verifica que se cargó un emisor
        assertEquals(0, tablero.getLaser().getFirst().getCoordX()); // Comprobar coordenadas
        assertEquals(1, tablero.getLaser().getFirst().getCoordY());
        assertEquals(Direccion.SE, tablero.getLaser().getFirst().getDireccion()); // Comprobar dirección

        // Comprueba que el objetivo se ha agregado correctamente
        assertEquals(1, tablero.getTarget().size()); // Verifica que se cargó un objetivo
        assertEquals(8, tablero.getTarget().getFirst().getCoordX()); // Comprobar coordenadas
        assertEquals(9, tablero.getTarget().getFirst().getCoordY());
    }

    @Test
    public void testMoverBloqueMovible() {
        Tablero tablero = new Tablero("level1.dat");

        // Verificar que el bloque espejo está en la posición inicial
        assertTrue(tablero.getCelda(0, 2).getBloque() instanceof BloqueEspejo);
        // Mover el bloque espejo de (0,2) a (2,2)
        tablero.moverBloque(0, 2, 2, 2);
        // Verificar que el bloque ahora está en la nueva posición
        assertTrue(tablero.getCelda(2, 2).getBloque() instanceof BloqueEspejo);
        // Verificar que la posición anterior está vacía
        assertNull(tablero.getCelda(0, 2).getBloque());

        // Verificar que el bloque opaco fijo está en la posicion inicial
        assertTrue(tablero.getCelda(3, 2).getBloque() instanceof BloqueOpacoFijo);
        // Testeo si no se mueve
        tablero.moverBloque(3, 2, 0, 0);
        assertNull(tablero.getCelda(0, 0).getBloque());
    }
}

