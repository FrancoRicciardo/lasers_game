package packlasers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {
    @Test
    void recorridoLaser() {
        Tablero tablero = new Tablero("level1.dat");

        Laser laser = tablero.getLasers().getFirst();
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (0,1)
        assertEquals(0, laser.currentPosition().getCoordX());
        assertEquals(1, laser.currentPosition().getCoordY());

        // El laser tiene direccion SouthEast
        assertEquals(Direccion.SE, laser.getDireccion());

        laser.moverPosicion();
        // El laser deberia estar en la pos (1,2)
        assertEquals(1, laser.currentPosition().getCoordX());
        assertEquals(2, laser.currentPosition().getCoordY());

        laser.moverPosicion();
        // El laser deberia estar en la pos (2,3)
        assertEquals(2, laser.currentPosition().getCoordX());
        assertEquals(3, laser.currentPosition().getCoordY());

        // Se fija que en la sig celda hay bloque espejo asiq debe rebotar
        laser.reflejarLaser();
        // El laser deberia estar en la pos (3,3)
        assertEquals(3, laser.currentPosition().getCoordX());
        assertEquals(3, laser.currentPosition().getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion());

        laser.moverPosicion();
        // El laser deberia estar en la pos (4,2)
        assertEquals(4, laser.currentPosition().getCoordX());
        assertEquals(2, laser.currentPosition().getCoordY());

        // Se fija que en la sig celda hay bloque espejo asiq debe rebotar
        laser.reflejarLaser();
        // El laser deberia estar en la pos (5,2)
        assertEquals(5, laser.currentPosition().getCoordX());
        assertEquals(2, laser.currentPosition().getCoordY());
        assertEquals(Direccion.SE, laser.getDireccion());

        laser.moverPosicion();
        // El laser deberia estar en la pos (6,3)
        assertEquals(6, laser.currentPosition().getCoordX());
        assertEquals(3, laser.currentPosition().getCoordY());

        // Se fija que en la sig celda hay bloque espejo asiq debe rebotar
        laser.reflejarLaser();
        // El laser deberia estar en la pos (7,3)
        assertEquals(7, laser.currentPosition().getCoordX());
        assertEquals(3, laser.currentPosition().getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion());

    }
}