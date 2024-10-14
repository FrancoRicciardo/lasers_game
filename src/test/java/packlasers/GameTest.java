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
        int nivel = 2;
        Tablero tablero = game.getNivel(nivel-1);
        game.setTableroActual(tablero);

        // Veamos si tablero actual es igual al nivel 2
        assertEquals(game.getTableroActual(), tablero);

        // Verificaciones de q los Targets sean bien cargados
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

        // El 2do target deberia estar en la pos (4,5)
        assertEquals(4, target2.getPosicion().getCoordX());
        assertEquals(5, target2.getPosicion().getCoordY());

        // El 3er target deberia estar en la pos (4,6)
        assertEquals(4, target3.getPosicion().getCoordX());
        assertEquals(6, target3.getPosicion().getCoordY());

        // Verificaciones sobre el laser
        Laser laser = game.getTableroActual().getLasers().getFirst();
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (0,5)
        assertEquals(0, laser.currentPosition().getCoordX());
        assertEquals(5, laser.currentPosition().getCoordY());
        // El laser tiene direccion SouthEast
        assertEquals(Direccion.SE, laser.getDireccion());


        tablero.moverLaser(laser);
        var list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (1,6)
        assertEquals(1, list.get(1).getCoordX());
        assertEquals(6, list.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (2,7)
        assertEquals(2, list.get(2).getCoordX());
        assertEquals(7, list.get(2).getCoordY());

        // El laser tiene que haber pasado por la pos (3,8)
        assertEquals(3, list.get(3).getCoordX());
        assertEquals(8, list.get(3).getCoordY());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE
        // PROBEMOS MOVIENDO UN BLOQUE Y OBSERVANDO SU TRAYECTORIA DE NUEVO

        // Muevo el bloque espejo de la celda (0,4) a (1,4) (de la UI)
        // Osea de (0,8) (1,8) (0,9) (1,9) a (2,8) (3,8) (2,9) (3,9)
        Posicion origen = new Posicion(0, 4);
        Posicion destino = new Posicion(1, 4);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (0,5)
        assertEquals(0, list.get(0).getCoordX());
        assertEquals(5, list.get(0).getCoordY());

        // El laser tiene que haber pasado por la pos (1,6)
        assertEquals(1, list.get(1).getCoordX());
        assertEquals(6, list.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (2,7)
        assertEquals(2, list.get(2).getCoordX());
        assertEquals(7, list.get(2).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (3,8) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (3,7)

        assertEquals(3, list.get(3).getCoordX());
        assertEquals(7, list.get(3).getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion()); //En este frame la direc es NE

        // El laser tiene que haber pasado por la pos (4,6)
        assertEquals(4, list.get(4).getCoordX());
        assertEquals(6, list.get(4).getCoordY());

        // El laser tiene que haber pasado por la pos (5,5)
        assertEquals(5, list.get(5).getCoordX());
        assertEquals(5, list.get(5).getCoordY());

        // El laser tiene que haber pasado por la pos (6,4)
        assertEquals(6, list.get(6).getCoordX());
        assertEquals(4, list.get(6).getCoordY());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE UNA VEZ MAS
        // PROBEMOS MOVIENDO OTRO BLOQUE Y OBSERVANDO SU NUEVA TRAYECTORIA

        // Muevo el bloque espejo de la celda (3,4) a (3,2) (de la UI)
        // Osea de (6,8) (7,8) (6,9) (7,9) a (6,4) (7,4) (6,5) (7,5)
        origen = new Posicion(3, 4);
        destino = new Posicion(3, 2);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (0,5)
        assertEquals(0, list.getFirst().getCoordX());
        assertEquals(5, list.getFirst().getCoordY());

        // El laser tiene que haber pasado por la pos (1,6)
        assertEquals(1, list.get(1).getCoordX());
        assertEquals(6, list.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (2,7)
        assertEquals(2, list.get(2).getCoordX());
        assertEquals(7, list.get(2).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (3,8) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (3,7)

        assertEquals(3, list.get(3).getCoordX());
        assertEquals(7, list.get(3).getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion()); //En este frame la direc es NE

        // El laser tiene que haber pasado por la pos (4,6)
        assertEquals(4, list.get(4).getCoordX());
        assertEquals(6, list.get(4).getCoordY());

        // El laser tiene que haber pasado por la pos (5,5)
        assertEquals(5, list.get(5).getCoordX());
        assertEquals(5, list.get(5).getCoordY()); // <= NO PASO POR EL TARGET (4,5)

        // Pero porq movimos un bloque, sabemos que en (6,4) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (5,4)
        assertEquals(5, list.get(6).getCoordX());
        assertEquals(4, list.get(6).getCoordY());
        //En este frame la direc es NW

        // El laser tiene que haber pasado por la pos (4,3)
        assertEquals(4, list.get(7).getCoordX());
        assertEquals(3, list.get(7).getCoordY()); // <= PASO POR UN TARGET

        // El laser tiene que haber pasado por la pos (3,2)
        assertEquals(3, list.get(8).getCoordX());
        assertEquals(2, list.get(8).getCoordY());

        // El laser tiene que haber pasado por la pos (2,1)
        assertEquals(2, list.get(9).getCoordX());
        assertEquals(1, list.get(9).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (0,0) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (2,0)
        assertEquals(2, list.get(10).getCoordX());
        assertEquals(0, list.get(10).getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion()); //En este frame la direc es NE

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE UNA VEZ MAS
        // PROBEMOS MOVIENDO OTRO BLOQUE Y OBSERVANDO SU NUEVA TRAYECTORIA

        // Muevo el bloque espejo de la celda (0,0) a (1,0) (de la UI)
        // Osea de (0,0) (1,0) (0,1) (1,1) a (2,0) (3,0) (2,1) (3,1)
        origen = new Posicion(0, 0);
        destino = new Posicion(1, 0);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (0,5)
        assertEquals(0, list.getFirst().getCoordX());
        assertEquals(5, list.getFirst().getCoordY());

        // Siguiendo toda la logica para no repetir el codigo... =>
        // El laser tiene que haber pasado por la pos (3,2)
        assertEquals(3, list.get(8).getCoordX());
        assertEquals(2, list.get(8).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (1,0) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SW y su pos en (2,2)
        assertEquals(2, list.get(9).getCoordX());
        assertEquals(2, list.get(9).getCoordY());
        assertEquals(Direccion.SW, laser.getDireccion()); //En este frame la direc es SW

        // El laser tiene que saber pasado por la pos (1,3)
        assertEquals(1, list.get(10).getCoordX());
        assertEquals(3, list.get(10).getCoordY());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE UNA VEZ MAS
        // PROBEMOS MOVIENDO OTRO BLOQUE Y OBSERVANDO SU NUEVA TRAYECTORIA

        // Muevo el bloque espejo de la celda (3,0) a (0,1) (de la UI)
        // Osea de (6,0) (6,1) (7,0) (7,1) a (0,2) (1,2) (0,3) (1,3)
        origen = new Posicion(3, 0);
        destino = new Posicion(0, 1);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (0,5)
        assertEquals(0, list.getFirst().getCoordX());
        assertEquals(5, list.getFirst().getCoordY());

        // Siguiendo toda la logica para no repetir el codigo... =>
        // El laser tiene que haber pasado por la pos (3,2)
        assertEquals(3, list.get(8).getCoordX());
        assertEquals(2, list.get(8).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (1,0) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SW y su pos en (2,2)
        assertEquals(2, list.get(9).getCoordX());
        assertEquals(2, list.get(9).getCoordY());
        //En este frame la direc es SW

        // Pero porq movimos un bloque, sabemos que en (0,1) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SE y su pos en (2,3)
        assertEquals(2, list.get(10).getCoordX());
        assertEquals(3, list.get(10).getCoordY());
        assertEquals(Direccion.SE, laser.getDireccion()); //En este frame la direc es SE

        // El laser tiene que haber pasado por la pos (3,4)
        assertEquals(3, list.get(11).getCoordX());
        assertEquals(4, list.get(11).getCoordY());

        // El laser tiene que haber pasado por la pos (4,5)
        assertEquals(4, list.get(12).getCoordX());
        assertEquals(5, list.get(12).getCoordY());

        // LLEGUE AL TARGET (4,5) Q ERA EL ULTIMO OSEA WIN!!
        assertTrue(tablero.chequearVictoria());

        // El laser tiene que haber pasado por la pos (5,4)
        assertEquals(5, list.get(13).getCoordX());
        assertEquals(6, list.get(13).getCoordY());
    }

    @Test
    void checkLevel3(){
        Game game;
        game = new Game();
        int nivel = 3;
        Tablero tablero = game.getNivel(nivel-1);
        game.setTableroActual(tablero);

        // Veamos si tablero actual es igual al nivel 3
        assertEquals(game.getTableroActual(), tablero);

        // Verificaciones de q los Targets sean bien cargados
        Target target1 = game.getTableroActual().getTarget().getFirst();
        // Que target de este nivel sea null
        assertNotNull(target1);

        // El 1er target deberia estar en la pos (4,0)
        assertEquals(4, target1.getPosicion().getCoordX());
        assertEquals(0, target1.getPosicion().getCoordY());

        // Verificaciones sobre el laser
        Laser laser = game.getTableroActual().getLasers().getFirst();
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (1, 0)
        assertEquals(1, laser.currentPosition().getCoordX());
        assertEquals(0, laser.currentPosition().getCoordY());
        // El laser tiene direccion SouthEast
        assertEquals(Direccion.SE, laser.getDireccion());


        tablero.moverLaser(laser);
        var list = laser.getTrayectoria();
        // En este nivel, el laser apenas se mueve tiene un bloque opaco movil
        // es decir, su trayectoria deberia tener una sola posicion (la inicial)
        assertEquals(1, list.size());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE
        // PROBEMOS MOVIENDO UN BLOQUE Y OBSERVANDO SU TRAYECTORIA DE NUEVO

        // Muevo el bloque opaco movil de la celda (1,1) a (1,2) (de la UI)
        // Osea de (2,2) (3,2) (2,3) (3,3) a (2,4) (3,4) (2,5) (3,5)
        Posicion origen = new Posicion(1, 1);
        Posicion destino = new Posicion(1, 2);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (1,0)
        assertEquals(1, list.getFirst().getCoordX());
        assertEquals(0, list.getFirst().getCoordY());

        // En este nivel, el laser apenas se mueve tiene un bloque opaco movil
        // es decir, su trayectoria deberia tener una sola posicion (la inicial)
        assertEquals(1, list.size());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE
        // PROBEMOS MOVIENDO UN BLOQUE Y OBSERVANDO SU TRAYECTORIA DE NUEVO

        // Muevo el bloque espejo de la celda (0,1) a (1,1) (de la UI)
        // Osea de (0,2) (1,2) (0,3) (1,3) a (2,2) (3,2) (2,3) (3,3)
        origen = new Posicion(0, 1);
        destino = new Posicion(1, 1);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (1,0)
        assertEquals(1, list.getFirst().getCoordX());
        assertEquals(0, list.getFirst().getCoordY());

        // En este nivel, el laser apenas se mueve tiene un bloque opaco movil
        // es decir, su trayectoria deberia tener una sola posicion (la inicial)
        assertEquals(1, list.size());

        // Muevo el bloque opaco movil de la celda (1,0) a (0,1) (de la UI)
        // Osea de (2,0) (3,0) (2,1) (3,1) a (0,2) (1,2) (0,3) (1,3)
        origen = new Posicion(1, 0);
        destino = new Posicion(0, 1);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        list = laser.getTrayectoria();
        // El laser tiene que haber pasado por la pos (1,0)
        assertEquals(1, list.getFirst().getCoordX());
        assertEquals(0, list.getFirst().getCoordY());

        // El laser tiene que haber pasado por la pos (2,1)
        assertEquals(2, list.get(1).getCoordX());
        assertEquals(1, list.get(1).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (1,1) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (3,1)
        assertEquals(3, list.get(2).getCoordX());
        assertEquals(1, list.get(2).getCoordY());
        assertEquals(Direccion.NE, laser.getDireccion()); //En este frame la direc es NE

        // El laser tiene que haber pasado por la pos (4,0)
        assertEquals(4, list.get(3).getCoordX());
        assertEquals(0, list.get(3).getCoordY()); // <= SE LLEGO AL TARGET

        // SI ESTA BIEN TODOO, SE ALCANZARON TODOS LOS TARGETS
        assertTrue(tablero.chequearVictoria());

    }

    @Test
    void checkLevel4(){
        Game game;
        game = new Game();
        int nivel = 4;
        Tablero tablero = game.getNivel(nivel-1);
        game.setTableroActual(tablero);

        // Veamos si tablero actual es igual al nivel 4
        assertEquals(game.getTableroActual(), tablero);

        // Verificaciones de q los Targets sean bien cargados
        Target target1 = game.getTableroActual().getTarget().getFirst();
        Target target2 = game.getTableroActual().getTarget().get(1);
        Target target3 = game.getTableroActual().getTarget().get(2);
        Target target4 = game.getTableroActual().getTarget().get(3);
        Target target5 = game.getTableroActual().getTarget().get(4);
        // Que ningun target de este nivel sea null
        assertNotNull(target1);
        assertNotNull(target2);
        assertNotNull(target3);
        assertNotNull(target4);
        assertNotNull(target5);

        // El 1er target deberia estar en la pos (2,3)
        assertEquals(2, target1.getPosicion().getCoordX());
        assertEquals(3, target1.getPosicion().getCoordY());

        // El 2do target deberia estar en la pos (3,4)
        assertEquals(3, target2.getPosicion().getCoordX());
        assertEquals(4, target2.getPosicion().getCoordY());

        // El 3er target deberia estar en la pos (4,5)
        assertEquals(4, target3.getPosicion().getCoordX());
        assertEquals(5, target3.getPosicion().getCoordY());

        // El 4to target deberia estar en la pos (5,6)
        assertEquals(5, target4.getPosicion().getCoordX());
        assertEquals(6, target4.getPosicion().getCoordY());

        // El 4to target deberia estar en la pos (6,7)
        assertEquals(6, target5.getPosicion().getCoordX());
        assertEquals(7, target5.getPosicion().getCoordY());

        // Verificaciones sobre el laser
        Laser laser = game.getTableroActual().getLasers().getFirst();
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (8,7)
        assertEquals(8, laser.currentPosition().getCoordX());
        assertEquals(7, laser.currentPosition().getCoordY());
        // El laser tiene direccion NW
        assertEquals(Direccion.NW, laser.getDireccion());

        // Una vez verificadas las generaciones, movemos el laser
        tablero.moverLaser(laser);
        // Obtengo sus trayectoria
        var trayectoria1 = laser.getTrayectoria();

        // El laser tiene que haber pasado por la pos (7,6)
        assertEquals(7, trayectoria1.get(1).getCoordX());
        assertEquals(6, trayectoria1.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (6,5)
        assertEquals(6, trayectoria1.get(2).getCoordX());
        assertEquals(5, trayectoria1.get(2).getCoordY());

        // El laser tiene que haber pasado por la pos (5,4)
        assertEquals(5, trayectoria1.get(3).getCoordX());
        assertEquals(4, trayectoria1.get(3).getCoordY());

        // El laser tiene que haber pasado por la pos (4,3)
        assertEquals(4, trayectoria1.get(4).getCoordX());
        assertEquals(3, trayectoria1.get(4).getCoordY());

        // El laser tiene que haber pasado por la pos (3,2)
        assertEquals(3, trayectoria1.get(5).getCoordX());
        assertEquals(2, trayectoria1.get(5).getCoordY());

        // El laser tiene que haber pasado por la pos (4,3)
        assertEquals(2, trayectoria1.get(6).getCoordX());
        assertEquals(1, trayectoria1.get(6).getCoordY());

        // El laser tiene que haber pasado por la pos (1,0)
        assertEquals(1, trayectoria1.get(7).getCoordX());
        assertEquals(0, trayectoria1.get(7).getCoordY());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE
        // PROBEMOS MOVIENDO UN BLOQUE Y OBSERVANDO SU TRAYECTORIA DE NUEVO

        // Muevo el bloque espejo de la celda (2,0) a (1,0) (de la UI)
        // Osea de (4,0) (5,0) (4,1) (5,1) a (2,0) (3,0) (2,1) (3,1)
        Posicion origen = new Posicion(2, 0);
        Posicion destino = new Posicion(1, 0);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        trayectoria1 = laser.getTrayectoria();

        // AHORA LA TRAYECTORIA DEL LASER VA A SER IDENTICAMENTE LA MISMA EXCEPTO SOBRE EL FINAL

        // El laser tiene que haber pasado por la pos (7,6)
        assertEquals(7, trayectoria1.get(1).getCoordX());
        assertEquals(6, trayectoria1.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (6,5)
        assertEquals(6, trayectoria1.get(2).getCoordX());
        assertEquals(5, trayectoria1.get(2).getCoordY());

        // El laser tiene que haber pasado por la pos (5,4)
        assertEquals(5, trayectoria1.get(3).getCoordX());
        assertEquals(4, trayectoria1.get(3).getCoordY());

        // El laser tiene que haber pasado por la pos (4,3)
        assertEquals(4, trayectoria1.get(4).getCoordX());
        assertEquals(3, trayectoria1.get(4).getCoordY());

        // El laser tiene que haber pasado por la pos (3,2)
        assertEquals(3, trayectoria1.get(5).getCoordX());
        assertEquals(2, trayectoria1.get(5).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (1,0) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SW y su pos en (2,2)
        assertEquals(2, trayectoria1.get(6).getCoordX());
        assertEquals(2, trayectoria1.get(6).getCoordY());
        assertEquals(Direccion.SW, laser.getDireccion()); //En este frame la direc es SW

        // El laser tiene que haber pasado por la pos (1,3)
        assertEquals(1, trayectoria1.get(7).getCoordX());
        assertEquals(3, trayectoria1.get(7).getCoordY());

        // El laser tiene que haber pasado por la pos (0,4)
        assertEquals(0, trayectoria1.get(8).getCoordX());
        assertEquals(4, trayectoria1.get(8).getCoordY());

        // EN ESTE PUNTO EL LASER VIAJO HASTA EL FINAL CORRECTAMENTE
        // PROBEMOS MOVIENDO UN BLOQUE Y OBSERVANDO SU TRAYECTORIA DE NUEVO

        // Muevo el bloque espejo de la celda (3,1) a (0,1) (de la UI)
        // Osea de (6,2) (7,2) (6,3) (7,3) a (0,2) (1,2) (0,3) (1,3)
        origen = new Posicion(3, 1);
        destino = new Posicion(0, 1);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        trayectoria1 = laser.getTrayectoria();

        // AHORA LA TRAYECTORIA DEL LASER VA A SER IDENTICAMENTE LA MISMA EXCEPTO SOBRE EL FINAL

        // El laser tiene que haber pasado por la pos (7,6)
        assertEquals(7, trayectoria1.get(1).getCoordX());
        assertEquals(6, trayectoria1.get(1).getCoordY());

        // El laser tiene que haber pasado por la pos (3,2)
        assertEquals(3, trayectoria1.get(5).getCoordX());
        assertEquals(2, trayectoria1.get(5).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (1,0) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SW y su pos en (2,2)
        assertEquals(2, trayectoria1.get(6).getCoordX());
        assertEquals(2, trayectoria1.get(6).getCoordY());
        //En este frame la direc es SW

        // Pero porq movimos un bloque, sabemos que en (0,1) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SE y su pos en (2,3) <=== PASO POR TARGET (2,3)
        assertEquals(2, trayectoria1.get(7).getCoordX());
        assertEquals(3, trayectoria1.get(7).getCoordY());
        assertEquals(Direccion.SE, laser.getDireccion()); // En este frame la direc es SE

        // El laser tiene que haber pasado por la pos (3,4) <= PASO POR TARGET (3,4)
        assertEquals(3, trayectoria1.get(8).getCoordX());
        assertEquals(4, trayectoria1.get(8).getCoordY());

        // El laser tiene que haber pasado por la pos (4,5) <= PASO POR TARGET (4,5)
        assertEquals(4, trayectoria1.get(9).getCoordX());
        assertEquals(5, trayectoria1.get(9).getCoordY());

        // El laser tiene que haber pasado por la pos (5,6) <= PASO POR TARGET (5,6)
        assertEquals(5, trayectoria1.get(10).getCoordX());
        assertEquals(6, trayectoria1.get(10).getCoordY());

        // A ESTE PUNTO VIENE PERFECTO, SIN EMBARGO, HAY Q PROBAR EL DIFRACTAR LASER
        // PROBEMOS MOVIENDO EL BLOQUE DE VIDRIO Y OBSERVANDO SU TRAYECTORIA DE NUEVO

        // Muevo el bloque de vidrio de la celda (3,0) a (3,2) (de la UI)
        // Osea de (6,0) (7,0) (6,1) (7,1) a (6,4) (7,4) (6,5) (7,5)
        origen = new Posicion(3, 0);
        destino = new Posicion(3, 2);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio el laser
        laser.reiniciarTrayectoria();
        tablero.moverLaser(laser);

        // TODA LA TRAYECTORIA DE ESTE LASER DEBERIA SER LA MISMA
        // CON LA SALVEDAD DE QUE SE TENDRIA QUE HABER CREADO UN LASER NUEVO!!

        assertEquals(2, tablero.getLasers().size()); // <= TENGO 3 LASERES? WAT

        Laser laser2 = game.getTableroActual().getLasers().get(1);
        // El laser NO es null
        assertNotNull(laser2);

        // El laser es correctamente cargado en pos (7,6)
        assertEquals(7, laser2.currentPosition().getCoordX());
        assertEquals(6, laser2.currentPosition().getCoordY());
        // El laser se tendria que haber creado con direccion SW
        assertEquals(Direccion.SW, laser2.getDireccion());

        // Una vez verificada la generacion del nuevo laser, lo movemos
        tablero.moverLaser(laser2);

        // Obtengo sus trayectorias
        var trayectoria2 = laser2.getTrayectoria();

        // COMENCEMOS TESTEANDO LA TRAYECTORIA DEL 1ER LASER PARA IR ORDENADOS: TODO

        // El laser tiene que haber pasado por la pos (6,7) <= PASO POR TARGET (6,7)
        assertEquals(6, trayectoria2.get(1).getCoordX());
        assertEquals(7, trayectoria2.get(1).getCoordY());

        // SI ESTA BIEN TODOO, SE ALCANZARON TODOS LOS TARGETS
        assertTrue(tablero.chequearVictoria());

    }

    @Test
    void checkLevel6(){
        Game game;
        game = new Game();
        int nivel = 6;
        Tablero tablero = game.getNivel(nivel-1);
        game.setTableroActual(tablero);

        // Veamos si tablero actual es igual al nivel 6
        assertEquals(game.getTableroActual(), tablero);

        // Verificaciones de q los Targets sean bien cargados
        Target target1 = game.getTableroActual().getTarget().getFirst();
        Target target2 = game.getTableroActual().getTarget().get(1);
        Target target3 = game.getTableroActual().getTarget().get(2);
        Target target4 = game.getTableroActual().getTarget().get(3);
        // Que ningun target de este nivel sea null
        assertNotNull(target1);
        assertNotNull(target2);
        assertNotNull(target3);
        assertNotNull(target4);

        // El 1er target deberia estar en la pos (1,6) ahora 0,6
        assertEquals(0, target1.getPosicion().getCoordX());
        assertEquals(6, target1.getPosicion().getCoordY());

        // El 2do target deberia estar en la pos (3,4)
        assertEquals(3, target2.getPosicion().getCoordX());
        assertEquals(4, target2.getPosicion().getCoordY());

        // El 3er target deberia estar en la pos (5,4) ahora 5,3
        assertEquals(5, target3.getPosicion().getCoordX());
        assertEquals(3, target3.getPosicion().getCoordY());

        // El 4to target deberia estar en la pos (8,5) ahora 7,4
        assertEquals(7, target4.getPosicion().getCoordX());
        assertEquals(4, target4.getPosicion().getCoordY());

        // Verificaciones sobre el 1er laser
        Laser laser = game.getTableroActual().getLasers().getFirst();
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (0,3)
        assertEquals(0, laser.currentPosition().getCoordX());
        assertEquals(3, laser.currentPosition().getCoordY());
        // El laser tiene direccion SouthEast
        assertEquals(Direccion.SE, laser.getDireccion());

        // Verificaciones sobre el 2do laser
        Laser laser2 = game.getTableroActual().getLasers().get(1);
        // El laser NO es null
        assertNotNull(laser);

        // El laser es correctamente cargado en pos (7,1)
        assertEquals(7, laser2.currentPosition().getCoordX());
        assertEquals(1, laser2.currentPosition().getCoordY());
        // El laser tiene direccion SouthEast
        assertEquals(Direccion.SW, laser2.getDireccion());

        // Una vez verificadas sus generaciones, los movemos
        tablero.moverLaser(laser);
        tablero.moverLaser(laser2);

        // Obtengo sus trayectorias
        var trayectoria1 = laser.getTrayectoria();
        var trayectoria2 = laser2.getTrayectoria();

        // COMENCEMOS TESTEANDO LA TRAYECTORIA DEL 1ER LASER PARA IR ORDENADOS:

        // El 1er laser tiene que haber pasado por la pos (1,4)
        assertEquals(1, trayectoria1.get(1).getCoordX());
        assertEquals(4, trayectoria1.get(1).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (2,5)
        assertEquals(2, trayectoria1.get(2).getCoordX());
        assertEquals(5, trayectoria1.get(2).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (3,6)
        assertEquals(3, trayectoria1.get(3).getCoordX());
        assertEquals(6, trayectoria1.get(3).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (4,7)
        assertEquals(4, trayectoria1.get(4).getCoordX());
        assertEquals(7, trayectoria1.get(4).getCoordY());

        // LLEGO AL FINAL EL 1ER LASER, TESTIEMOS EL 2DO

        // El 2do laser tiene que haber pasado por la pos (6,2)
        assertEquals(6, trayectoria2.get(1).getCoordX());
        assertEquals(2, trayectoria2.get(1).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (5,3)
        assertEquals(5, trayectoria2.get(2).getCoordX());
        assertEquals(3, trayectoria2.get(2).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (4,4)
        assertEquals(4, trayectoria2.get(3).getCoordX());
        assertEquals(4, trayectoria2.get(3).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (3,5)
        assertEquals(3, trayectoria2.get(4).getCoordX());
        assertEquals(5, trayectoria2.get(4).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (2,6)
        assertEquals(2, trayectoria2.get(5).getCoordX());
        assertEquals(6, trayectoria2.get(5).getCoordY());

        // Pero sabemos que en (0,3) de le UI, hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SE y su pos en (2,7)

        // El 2do laser tiene que haber pasado por la pos (2,7)
        assertEquals(2, trayectoria2.get(6).getCoordX());
        assertEquals(7, trayectoria2.get(6).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (3,8) <= A CHEQUEAR (FUERA DE MAPA)
        assertEquals(3, trayectoria2.get(7).getCoordX());
        assertEquals(8, trayectoria2.get(7).getCoordY());

        // EN ESTE PUNTO LOS LASERS VIAJARON HASTA EL FINAL CORRECTAMENTE,
        // PROBEMOS MOVIENDO UN BLOQUE Y OBSERVANDO SUS NUEVAS TRAYECTORIAS

        // Muevo el bloque espejo de la celda (3,0) a (2,0) (de la UI)
        // Osea de (6,0) (7,0) (6,1) (6,1) a (4,0) (5,0) (4,1) (5,1)
        Posicion origen = new Posicion(3, 0);
        Posicion destino = new Posicion(2, 0);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio ambos lasers
        laser.reiniciarTrayectoria();
        laser2.reiniciarTrayectoria();
        tablero.moverLaser(laser);
        tablero.moverLaser(laser2);

        // Y obtengo sus nuevas trayectorias
        trayectoria1 = laser.getTrayectoria();
        trayectoria2 = laser2.getTrayectoria();

        // PERO ESTE MOVIMIENTO DE BLOQUE NO DEBERIA INFLUIR EN NADA
        // ASIQUE COPY PASTE DE LA TRAYECTORIA ANTERIOR Y DEBERIA FUNCIONAR IGUAL

        // COMENCEMOS TESTEANDO LA TRAYECTORIA DEL 1ER LASER PARA IR ORDENADOS:

        // El 1er laser tiene que haber pasado por la pos (1,4)
        assertEquals(1, trayectoria1.get(1).getCoordX());
        assertEquals(4, trayectoria1.get(1).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (2,7)
        assertEquals(2, trayectoria1.get(2).getCoordX());
        assertEquals(5, trayectoria1.get(2).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (3,6)
        assertEquals(3, trayectoria1.get(3).getCoordX());
        assertEquals(6, trayectoria1.get(3).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (4,7)
        assertEquals(4, trayectoria1.get(4).getCoordX());
        assertEquals(7, trayectoria1.get(4).getCoordY());

        // LLEGO AL FINAL EL 1ER LASER, TESTIEMOS EL 2DO

        // El 2do laser tiene que haber pasado por la pos (6,2)
        assertEquals(6, trayectoria2.get(1).getCoordX());
        assertEquals(2, trayectoria2.get(1).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (5,3)
        assertEquals(5, trayectoria2.get(2).getCoordX());
        assertEquals(3, trayectoria2.get(2).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (4,4)
        assertEquals(4, trayectoria2.get(3).getCoordX());
        assertEquals(4, trayectoria2.get(3).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (3,5)
        assertEquals(3, trayectoria2.get(4).getCoordX());
        assertEquals(5, trayectoria2.get(4).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (2,6)
        assertEquals(2, trayectoria2.get(5).getCoordX());
        assertEquals(6, trayectoria2.get(5).getCoordY());

        // Pero sabemos que en (0,3) de le UI, hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SE y su pos en (2,7)

        // El 2do laser tiene que haber pasado por la pos (2,7)
        assertEquals(2, trayectoria2.get(6).getCoordX());
        assertEquals(7, trayectoria2.get(6).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (3,8) <= A CHEQUEAR (FUERA DE MAPA)
        assertEquals(3, trayectoria2.get(7).getCoordX());
        assertEquals(8, trayectoria2.get(7).getCoordY());

        // EN ESTE PUNTO LOS LASERS VIAJARON HASTA EL FINAL CORRECTAMENTE,
        // PROBEMOS MOVIENDO OTRO BLOQUE MAS Y OBSERVANDO SUS NUEVAS TRAYECTORIAS

        // Muevo el bloque espejo de la celda (3,3) a (2,2) (de la UI)
        // Osea de (6,6) (7,6) (6,7) (7,7) a (4,4) (5,4) (4,5) (5,5)
        origen = new Posicion(3, 3);
        destino = new Posicion(2, 2);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio ambos lasers
        laser.reiniciarTrayectoria();
        laser2.reiniciarTrayectoria();
        tablero.moverLaser(laser);
        tablero.moverLaser(laser2);

        // Y obtengo sus nuevas trayectorias
        trayectoria1 = laser.getTrayectoria();
        trayectoria2 = laser2.getTrayectoria();

        // PERO ESTE MOVIMIENTO DE BLOQUE NO DEBERIA INFLUIR EN NADA AL 1ER LASER
        // ASIQUE COPY PASTE DE LA TRAYECTORIA ANTERIOR Y SOLO TESTEO AL 2DO LASER

        // El 1er laser tiene que haber pasado por la pos (1,4)
        assertEquals(1, trayectoria1.get(1).getCoordX());
        assertEquals(4, trayectoria1.get(1).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (2,7)
        assertEquals(2, trayectoria1.get(2).getCoordX());
        assertEquals(5, trayectoria1.get(2).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (3,6)
        assertEquals(3, trayectoria1.get(3).getCoordX());
        assertEquals(6, trayectoria1.get(3).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (4,7)
        assertEquals(4, trayectoria1.get(4).getCoordX());
        assertEquals(7, trayectoria1.get(4).getCoordY());

        // LLEGO AL FINAL EL 1ER LASER, TESTIEMOS EL 2DO

        // El 2do laser tiene que haber pasado por la pos (6,2)
        assertEquals(6, trayectoria2.get(1).getCoordX());
        assertEquals(2, trayectoria2.get(1).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (5,3)
        assertEquals(5, trayectoria2.get(2).getCoordX());
        assertEquals(3, trayectoria2.get(2).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (2,2) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NW y su pos en (4,3)
        assertEquals(4, trayectoria2.get(3).getCoordX());
        assertEquals(3, trayectoria2.get(3).getCoordY());
        //En este frame la direc es NW

        // El 2do laser tiene que haber pasado por la pos (3,2)
        assertEquals(3, trayectoria2.get(4).getCoordX());
        assertEquals(2, trayectoria2.get(4).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (2,1)
        assertEquals(2, trayectoria2.get(5).getCoordX());
        assertEquals(1, trayectoria2.get(5).getCoordY());

        // Pero sabemos que en (0,0) hay un Bloque Espejo por el cargado del nivel:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (2,0)

        // El 2do laser tiene que haber pasado por la pos (2,0)
        assertEquals(2, trayectoria2.get(6).getCoordX());
        assertEquals(0, trayectoria2.get(6).getCoordY());
        assertEquals(Direccion.NE, laser2.getDireccion()); //En este frame la direc es NE

        // EN ESTE PUNTO LOS LASERS VIAJARON HASTA EL FINAL CORRECTAMENTE NUEVAMENTE,
        // PROBEMOS MOVIENDO OTRO BLOQUE MAS Y OBSERVANDO SUS NUEVAS TRAYECTORIAS

        // Muevo el bloque espejo de la celda (0,3) a (1,3) (de la UI)
        // Osea de (0,6) (1,6) (0,7) (1,7) a (4,4) (5,4) (4,5) (5,5)
        origen = new Posicion(0, 3);
        destino = new Posicion(1, 3);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio ambos lasers
        laser.reiniciarTrayectoria();
        laser2.reiniciarTrayectoria();
        tablero.moverLaser(laser);
        tablero.moverLaser(laser2);

        // Y obtengo sus nuevas trayectorias
        trayectoria1 = laser.getTrayectoria();
        //trayectoria2 = laser2.getTrayectoria();

        // PERO ESTE MOVIMIENTO DE BLOQUE NO DEBERIA INFLUIR EN NADA NUEVO CON RESPECTO
        // AL ANTERIOR TREYECTO DE 2DO LASER, ASIQUE COPY PASTE DE LA TRAYECTORIA ANTERIOR
        // Y SOLO TESTEO LA NUEVA TRAYECTORIA DEL 1ER LASER

        // El 1er laser tiene que haber pasado por la pos (1,4)
        assertEquals(1, trayectoria1.get(1).getCoordX());
        assertEquals(4, trayectoria1.get(1).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (2,5)
        assertEquals(2, trayectoria1.get(2).getCoordX());
        assertEquals(5, trayectoria1.get(2).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (1,3) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (3,5)

        // El 1er laser tiene que haber pasado por la pos (3,5)
        assertEquals(3, trayectoria1.get(3).getCoordX());
        assertEquals(5, trayectoria1.get(3).getCoordY());
        //En este frame la direc es NE

        // Pero porq movimos un bloque, sabemos que en (2,2) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NW y su pos en (3,5)

        // El 1er laser tiene que haber pasado por la pos (3,4)
        assertEquals(3, trayectoria1.get(4).getCoordX());
        assertEquals(4, trayectoria1.get(4).getCoordY());
        //En este frame la direc es NW

        // El 1er laser tiene que haber pasado por la pos (2,3)
        assertEquals(2, trayectoria1.get(5).getCoordX());
        assertEquals(3, trayectoria1.get(5).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (1,2)
        assertEquals(1, trayectoria1.get(6).getCoordX());
        assertEquals(2, trayectoria1.get(6).getCoordY());

        // Pero sabemos que en (0,0) hay un Bloque Espejo por el cargado del nivel:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SW y su pos en (0,2)

        // El 1er laser tiene que haber pasado por la pos (0,2)
        assertEquals(0, trayectoria1.get(7).getCoordX());
        assertEquals(2, trayectoria1.get(7).getCoordY());
        assertEquals(Direccion.SW, laser.getDireccion()); //En este frame la direc es SW

        // Y COMO DIJIMOS EL 2DO LASER TIENE LA MISMA TRAYECTORIA YA TESTADA
        // OSEA EN ESTE PUNTO LOS LASERS VIAJARON HASTA EL FINAL CORRECTAMENTE NUEVAMENTE,
        // PROBEMOS MOVIENDO OTRO BLOQUE MAS Y OBSERVANDO SUS NUEVAS TRAYECTORIAS

        // Muevo el bloque espejo de la celda (0,0) a (1,1) (de la UI)
        // Osea de (0,0) (1,0) (0,1) (1,1) a (2,2) (3,2) (2,3) (3,3)
        origen = new Posicion(0, 0);
        destino = new Posicion(1, 1);
        tablero.moverBloque(origen, destino);

        // Una vez el bloque fue movido correctamente, reinicio ambos lasers
        laser.reiniciarTrayectoria();
        laser2.reiniciarTrayectoria();
        tablero.moverLaser(laser);
        tablero.moverLaser(laser2);

        // Y obtengo sus nuevas trayectorias
        trayectoria1 = laser.getTrayectoria();
        trayectoria2 = laser2.getTrayectoria();

        // COMENCEMOS POR EL 1ER LASER

        // El 1er laser tiene que haber pasado por la pos (1,4)
        assertEquals(1, trayectoria1.get(1).getCoordX());
        assertEquals(4, trayectoria1.get(1).getCoordY());

        // El 1er laser tiene que haber pasado por la pos (2,5)
        assertEquals(2, trayectoria1.get(2).getCoordX());
        assertEquals(5, trayectoria1.get(2).getCoordY());

        // Pero porq movimos un bloque, sabemos que en (1,3) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (3,5)

        // El 1er laser tiene que haber pasado por la pos (3,5)
        assertEquals(3, trayectoria1.get(3).getCoordX());
        assertEquals(5, trayectoria1.get(3).getCoordY());
        //En este frame la direc es NE

        // Pero porq movimos un bloque, sabemos que en (2,2) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NW y su pos en (3,5)

        // El 1er laser tiene que haber pasado por la pos (3,4) <= PASO POR TARGET (3,4)
        assertEquals(3, trayectoria1.get(4).getCoordX());
        assertEquals(4, trayectoria1.get(4).getCoordY());
        //En este frame la direc es NW

        // Pero porq movimos un bloque, sabemos que en (1,1) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SW y su pos en (2,4)

        // El 1er laser tiene que haber pasado por la pos (2,4)
        assertEquals(2, trayectoria1.get(5).getCoordX());
        assertEquals(4, trayectoria1.get(5).getCoordY());
        assertEquals(Direccion.SW, laser.getDireccion()); //En este frame la direc es SW

        // El 1er laser tiene que haber pasado por la pos (1,5)
        assertEquals(1, trayectoria1.get(6).getCoordX());
        assertEquals(5, trayectoria1.get(6).getCoordY());

        // NO PASO POR EL TARGET (1,6)

        // El 1er laser tiene que haber pasado por la pos (0,6)
        assertEquals(0, trayectoria1.get(7).getCoordX());
        assertEquals(6, trayectoria1.get(7).getCoordY()); // <= ahora PASO POR EL TARGET (0,6)

        // LLEGO AL FINAL EL 1ER LASER, TESTIEMOS EL 2DO

        // El 2do laser tiene que haber pasado por la pos (6,2)
        assertEquals(6, trayectoria2.get(1).getCoordX());
        assertEquals(2, trayectoria2.get(1).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (5,3)
        assertEquals(5, trayectoria2.get(2).getCoordX());
        assertEquals(3, trayectoria2.get(2).getCoordY()); // <=  ahora PASO POR TARGET (5,3)

        // NO PASO POR TARGET (5,4)

        // Pero porq movimos un bloque, sabemos que en (2,2) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NW y su pos en (4,3)
        assertEquals(4, trayectoria2.get(3).getCoordX());
        assertEquals(3, trayectoria2.get(3).getCoordY());
        //En este frame la direc es NW

        // Pero porq movimos un bloque, sabemos que en (1,1) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en NE y su pos en (4,2)
        assertEquals(4, trayectoria2.get(4).getCoordX());
        assertEquals(2, trayectoria2.get(4).getCoordY());
        //En este frame la direc es NE

        // Pero porq movimos un bloque, sabemos que en (2,0) hay un Bloque Espejo:
        // entonces, mira que en la sig celda esta ese bloque y se refleja
        // convirtiendo su direccion en SE y su pos en (5,2)
        assertEquals(5, trayectoria2.get(5).getCoordX());
        assertEquals(2, trayectoria2.get(5).getCoordY());
        assertEquals(Direccion.SE, laser2.getDireccion()); //En este frame la direc es SE

        // El 2do laser tiene que haber pasado por la pos (6,3)
        assertEquals(6, trayectoria2.get(6).getCoordX());
        assertEquals(3, trayectoria2.get(6).getCoordY());

        // El 2do laser tiene que haber pasado por la pos (7,4)
        assertEquals(7, trayectoria2.get(7).getCoordX());
        assertEquals(4, trayectoria2.get(7).getCoordY());
        // LLEGUE AL TARGET (7,4) Q ERA EL ULTIMO OSEA WIN??
        // SI ESTA BIEN TODOO, SE ALCANZARON TODOS LOS TARGETS
        assertTrue(tablero.chequearVictoria());

        /* El 2do laser tiene que haber pasado por la pos (8,5) <= VEAMOS, ESTA FUERA DE MAPA (COMO EL TARGET)
        assertEquals(8, trayectoria2.get(8).getCoordX());
        assertEquals(5, trayectoria2.get(8).getCoordY());

        // LLEGUE AL TARGET (8,5) Q ERA EL ULTIMO OSEA WIN??
        // SI ESTA BIEN TODOO, SE ALCANZARON TODOS LOS TARGETS
        assertTrue(tablero.chequearVictoria()); */

    }

}