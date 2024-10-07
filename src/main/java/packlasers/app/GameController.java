package packlasers.app;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import packlasers.*;

import java.util.ArrayList;

public class GameController {
    private GameView gameView;

    @FXML
    private ToggleButton buttonLevel1, buttonLevel2,
            buttonLevel3, buttonLevel4, buttonLevel5, buttonLevel6;

    @FXML
    private GridPane grilla;

    private ToggleGroup toggleGroup;
    private Game game;
    private boolean juegoInicializado = false;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        configurarToggleButtons();
    }

    private void configurarToggleButtons() {
        buttonLevel1.setToggleGroup(toggleGroup);
        buttonLevel2.setToggleGroup(toggleGroup);
        buttonLevel3.setToggleGroup(toggleGroup);
        buttonLevel4.setToggleGroup(toggleGroup);
        buttonLevel5.setToggleGroup(toggleGroup);
        buttonLevel6.setToggleGroup(toggleGroup);

        // Configurar los eventos de acción para cada botón
        buttonLevel1.setOnAction(_ -> seleccionarNivel(1));
        buttonLevel2.setOnAction(_ -> seleccionarNivel(2));
        buttonLevel3.setOnAction(_ -> seleccionarNivel(3));
        buttonLevel4.setOnAction(_ -> seleccionarNivel(4));
        buttonLevel5.setOnAction(_ -> seleccionarNivel(5));
        buttonLevel6.setOnAction(_ -> seleccionarNivel(6));
    }

    private void seleccionarNivel(int nivel) {
        game.reiniciarNivel(nivel);
        game.setTableroActual(game.getNivel(nivel - 1));
        gameView.cargarNivel(nivel);

    }

    public void inicializarJuego(GridPane grilla) {
        if (juegoInicializado) {
            mostrarLaser(grilla);
            configurarEventosBloques(grilla);
            return;
        }

        System.out.println("Inicializando juego...");
        mostrarLaser(grilla);
        configurarEventosBloques(grilla);
        juegoInicializado = true;
    }

    private void configurarEventosBloques(GridPane grilla) {
        System.out.println("Configurando eventos para bloques...");
        grilla.setOnMousePressed(event -> {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode != null && GridPane.getRowIndex(clickedNode) != null &&
                    GridPane.getColumnIndex(clickedNode) != null && clickedNode instanceof Rectangle) {
                int row = GridPane.getRowIndex(clickedNode);
                int column = GridPane.getColumnIndex(clickedNode);

                System.out.println(column + " " + row);

                Tablero tablero = game.getTableroActual();
                // Aquí puedes obtener el bloque en la posición (row, column)
                Celda selectedBlock = tablero.getCelda(column, row);

                if (selectedBlock.getBloque() != null && selectedBlock.getBloque().esMovible()) {
                    configurarArrastre(clickedNode, grilla);
                }
            }
            event.consume();
        });
    }

    private void configurarArrastre(Node bloque, GridPane grilla) {
        Tablero tablero = game.getTableroActual();
        // Evento que detecta el inicio del arrastre
        bloque.setOnDragDetected(event -> {
            System.out.println("Iniciando arrastre del bloque: " + bloque);

            // Iniciar arrastre con el bloque seleccionado
            Dragboard dragboard = bloque.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("bloque"); // Asignar un identificador al contenido
            dragboard.setContent(content);
            dragboard.setDragView(bloque.snapshot(null, null)); // Tomar un snapshot del bloque

            event.consume(); // Consumir el evento
        });

        grilla.setOnDragOver(event -> {
            if (event.getGestureSource() != grilla && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Evento que se dispara cuando el bloque es soltado
        grilla.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if(dragboard.hasString()) {
                // Obtener la posición donde se suelta el bloque
                // Obtener la posición donde se suelta el bloque
                Node targetCell = getNodeByCoordinates(grilla, event.getX(), event.getY());
                if (targetCell != null) {
                    Integer row = GridPane.getRowIndex(targetCell);
                    Integer column = GridPane.getColumnIndex(targetCell);

                    if (row != null && column != null && !tablero.getCelda(column, row).tieneBloque() && tablero.getCelda(column, row).getPiso()) {
                        int originalRow = GridPane.getRowIndex(bloque);
                        int originalCol = GridPane.getColumnIndex(bloque);

                        // Mover el bloque a la nueva celda
                        GridPane.setRowIndex(bloque, row);
                        GridPane.setColumnIndex(bloque, column);

                        // E intercambio el rectangle en la celda original
                        GridPane.setRowIndex(targetCell, originalRow);
                        GridPane.setColumnIndex(targetCell, originalCol);

                        Posicion origen = new Posicion(originalCol, originalRow);
                        Posicion destino = new Posicion(column, row);
                        tablero.moverBloque(origen, destino);

                        success = true;
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    // Este metodo encuentra el nodo en la GridPane basado en las coordenadas del mouse
    private Node getNodeByCoordinates(GridPane gridPane, double x, double y) {
        for (Node node : gridPane.getChildren()) {
            if (node.getBoundsInParent().contains(x, y)) {
                return node;
            }
        }
        return null;
    }

    private void mostrarLaser(GridPane grilla){
        /*
        // Recorro la GridPane eliminando solo las instancias de Line para reinicar la trayectoria del laser
        var copiaChildren = new ArrayList<>(grilla.getChildren());
        for (Node child : copiaChildren) {
            // Aquí puedes modificar la lista original sin problemas
            if (child instanceof Line) {
                grilla.getChildren().remove(child);
            }
        }
        for (Laser laser : game.getTableroActual().getLasers()) {
            laser.reiniciarTrayectoria();
            game.getTableroActual().moverLaser(laser);
            Direccion direccion = laser.getDireccion();

            for(Posicion pos: laser.getTrayectoria()){
                Posicion sigPos = new Posicion(pos.getCoordX(), pos.getCoordY());
                sigPos.move(direccion);
                Line laserLine = new Line(pos.getCoordX(), pos.getCoordY(),
                        sigPos.getCoordX(),
                        sigPos.getCoordY());
                laserLine.setStroke(Color.RED);
                laserLine.setStrokeWidth(2);
                grilla.getChildren().add(laserLine);
            }
        }
        */
    }

    public ToggleButton getButtonForLevel(int nivel) {
        return switch (nivel) {
            case 1 -> buttonLevel1;
            case 2 -> buttonLevel2;
            case 3 -> buttonLevel3;
            case 4 -> buttonLevel4;
            case 5 -> buttonLevel5;
            case 6 -> buttonLevel6;
            default -> null;
        };
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }
}
