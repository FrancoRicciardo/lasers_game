package packlasers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void checkLevel1(){
        Game game;
        game = new Game();
        int nivel = 1;
        Tablero tablero = game.getNivel(nivel-1);

        // Veamos si el 1er nivel es igual al tablero actual apenas se ejecuta el modelo
        assertEquals(game.getTableroActual(), tablero);

        Laser laser = game.getTableroActual().getLasers().getFirst();
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (0,1)
        assertEquals(0, laser.currentPosition().getCoordX());
        assertEquals(1, laser.currentPosition().getCoordY());

        // El laser tiene direccion SouthEast
        assertEquals(Direccion.SE, laser.getDireccion());

        tablero.moverLaser(laser);
        var list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (0,1)
        assertEquals(0, list.get(0).getCoordX());
        assertEquals(1, list.get(0).getCoordY());

        // El laser tiene que haber pasado por la pos (1,2)
        assertEquals(1, list.get(1).getCoordX());
        assertEquals(2, list.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (2,3)
        assertEquals(2, list.get(2).getCoordX());
        assertEquals(3, list.get(2).getCoordY());

        // Pero por TableroTest sabemos que en (3,4) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (3,3)
        assertEquals(3, list.get(3).getCoordX());
        assertEquals(3, list.get(3).getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion());

        // El laser tiene que haber pasado por la pos (4,2)
        assertEquals(4, list.get(4).getCoordX());
        assertEquals(2, list.get(4).getCoordY());

        // Pero por TableroTest sabemos que en (5,1) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SE y su pos en (5,2)
        assertEquals(5, list.get(5).getCoordX());
        assertEquals(2, list.get(5).getCoordY());
        //En este frame la direc si es SE

        // El laser tiene que haber pasado por la pos (6,3)
        assertEquals(6, list.get(6).getCoordX());
        assertEquals(3, list.get(6).getCoordY());

        // Pero por TableroTest sabemos que en (7,4) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (7,3)
        assertEquals(7, list.get(7).getCoordX());
        assertEquals(3, list.get(7).getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE
        // PROBEMOS MOVIENDO UN BLOQUE Y OBSERVANDO SU TRAYECTORIA DE NUEVO

        // Muevo el bloque espejo de la celda (2,1) a (2,2) (de la UI)
        // Osea de (2,4) (3,4) (2,5) (3,5) a (4,4) (5,4) (4,5) (5,5)
        Posicion origen = new Posicion(1, 2);
        Posicion destino = new Posicion(2, 2);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reincio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (0,1)
        assertEquals(0, list.get(0).getCoordX());
        assertEquals(1, list.get(0).getCoordY());

        // El laser tiene que haber pasado por la pos (1,2)
        assertEquals(1, list.get(1).getCoordX());
        assertEquals(2, list.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (2,3)
        assertEquals(2, list.get(2).getCoordX());
        assertEquals(3, list.get(2).getCoordY());

        // El laser tiene que haber pasado por la pos (3,4)
        assertEquals(3, list.get(3).getCoordX());
        assertEquals(4, list.get(3).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (4,5) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (3,5)
        assertEquals(3, list.get(4).getCoordX());
        assertEquals(5, list.get(4).getCoordY());
        assertEquals(Direccion.SW, laser.getDireccion()); //En este frame la direc es SW

        // El laser tiene que haber pasado por la pos (2,6)
        assertEquals(2, list.get(5).getCoordX());
        assertEquals(6, list.get(5).getCoordY());

        // El laser tiene que haber pasado por la pos (1,7)
        assertEquals(1, list.get(6).getCoordX());
        assertEquals(7, list.get(6).getCoordY());

        // El laser tiene que haber pasado por la pos (0,8)
        assertEquals(0, list.get(7).getCoordX());
        assertEquals(8, list.get(7).getCoordY());

        // EL PRIMER NIVEL ES GANABLE AAA
    }


    @Test
    void checkLevel2(){
        Game game;
        game = new Game();
        Tablero tablero = game.getNivel(1);
        int nivel = 2;

        game.reiniciarNivel(nivel);
        game.setTableroActual(game.getNivel(nivel - 1));

        Laser laser = game.getTableroActual().getLasers().getFirst();
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (0,5)
        assertEquals(0, laser.currentPosition().getCoordX());
        assertEquals(5, laser.currentPosition().getCoordY());
        // El laser tiene direccion SouthEast
        assertEquals(Direccion.SE, laser.getDireccion());


        Target target1 = game.getTableroActual().getTarget().getFirst();
        Target target2 = game.getTableroActual().getTarget().get(1);
        Target target3 = game.getTableroActual().getTarget().get(2);
        // Que ningun target de este nivel sea null
        assertNotNull(target1);
        assertNotNull(target2);
        assertNotNull(target3);

        // El 1er target deberia estar en la pos (4,3)
        assertEquals(4, target1.getPosicion().getCoordX());
        assertEquals(3, target1.getPosicion().getCoordY());

        // El 1er target deberia estar en la pos (4,5)
        assertEquals(4, target2.getPosicion().getCoordX());
        assertEquals(5, target2.getPosicion().getCoordY());

        // El 1er target deberia estar en la pos (4,7)
        assertEquals(4, target3.getPosicion().getCoordX());
        assertEquals(7, target3.getPosicion().getCoordY());

        laser.moverPosicion();
        // El laser ahora deberia estar en la pos (1,6)
        assertEquals(1, laser.currentPosition().getCoordX());
        assertEquals(6, laser.currentPosition().getCoordY());

    }


}