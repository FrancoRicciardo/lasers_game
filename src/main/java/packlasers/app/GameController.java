package packlasers.app;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.RED;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import packlasers.*;

import java.util.ArrayList;

public class GameController {
    private GameView gameView;
    private ToggleGroup toggleGroup;
    private Game game;
    private boolean SALIR = false;
    public static int NIVEL_ACTUAL = 1;
    public static int CELL_W = 40;
    public static int CELL_H = 40;

    @FXML
    private ToggleButton buttonLevel1, buttonLevel2,
            buttonLevel3, buttonLevel4, buttonLevel5, buttonLevel6;

    @FXML
    private GridPane grilla;

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
        SALIR = false;
        NIVEL_ACTUAL = nivel;
        gameView.cargarNivel(nivel);
    }

    public void inicializarJuego(Parent root) {
        System.out.println("Inicializando juego...");
        System.out.println("Dibujando laseres...");
        mostrarLaser(root);
        System.out.println("Configurando eventos para bloques...");
        configurarEventosBloques(root);
    }

    private void configurarEventosBloques(Parent root) {
        if(SALIR) return;
        grilla.setOnMousePressed(event -> {
            if(SALIR) {
                event.consume();
                return;
            }
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode != null && GridPane.getRowIndex(clickedNode) != null &&
                    GridPane.getColumnIndex(clickedNode) != null && clickedNode instanceof Rectangle) {
                int row = GridPane.getRowIndex(clickedNode);
                int column = GridPane.getColumnIndex(clickedNode);

                System.out.println("Bloque seleccionado: (" + column + " " + row + ")");
                Tablero tablero = game.getTableroActual();
                // Obtenenemos el bloque en la posición 2x, 2y del Modelo
                Celda selectedBlock = tablero.getCelda(2*column, 2*row);

                if (selectedBlock.getBloque() != null && selectedBlock.getBloque().esMovible()) {
                    configurarArrastre(clickedNode, root);
                }
            }
            event.consume();
        });
    }

    private void configurarArrastre(Node bloque, Parent root) {
        Tablero tablero = game.getTableroActual();
        GridPane grilla = (GridPane) root.lookup("#grilla");
        HBox hbox = (HBox) root.lookup("#rootHBox");

        bloque.setOnDragDetected(event -> {
            if(SALIR){
                event.consume();
                return;
            }
            Dragboard dragboard = bloque.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("bloque");
            dragboard.setContent(content);
            dragboard.setDragView(bloque.snapshot(null, null));

            event.consume();
        });

        grilla.setOnDragOver(event -> {
            if (event.getGestureSource() != grilla && event.getDragboard().hasString() && !SALIR) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        grilla.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if(dragboard.hasString()) {
                // Obtenemos la posición donde se suelta el bloque
                Node targetCell = getNodeByCoordinates(grilla, event.getX(), event.getY());
                if (targetCell != null) {
                    Integer row = GridPane.getRowIndex(targetCell);
                    Integer column = GridPane.getColumnIndex(targetCell);

                    if (row != null && column != null && !tablero.getCelda(2*column, 2*row).tieneBloque()
                            && tablero.getCelda(2*column, 2*row).getPiso()) {
                        int originalRow = GridPane.getRowIndex(bloque);
                        int originalCol = GridPane.getColumnIndex(bloque);

                        // Movemos el bloque a la nueva celda (en la UI)
                        GridPane.setRowIndex(bloque, row);
                        GridPane.setColumnIndex(bloque, column);

                        // E intercambiamos el rectangle en la celda original (en la UI)
                        GridPane.setRowIndex(targetCell, originalRow);
                        GridPane.setColumnIndex(targetCell, originalCol);

                        // Movemos el bloque a la nueva celda (en el Modelo)
                        Posicion origen = new Posicion(originalCol, originalRow);
                        Posicion destino = new Posicion(column, row);
                        tablero.moverBloque(origen, destino);

                        success = true;
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
            if(success){
                // Con esto hago que en el lvl4, se vayan borrando los laseres
                // creados por el Bloque de Vidrio que deben ser eliminados
                // por si se movio de celda el susodicho bloque
                if(NIVEL_ACTUAL == 4 && game.getTableroActual().getLasers().size() > 1){
                    tablero.reiniciarEmisores();
                }

                mostrarLaser(root);
                SALIR = tablero.chequearVictoria();
                if(SALIR){
                    TextArea texto = new TextArea("Felicitaciones!\nNivel completado :D\nVaya al siguiente!");
                    texto.setPrefHeight(60);
                    texto.setMaxHeight(60);
                    texto.setPrefWidth(140);
                    texto.setMaxWidth(140);
                    texto.setMouseTransparent(true);
                    hbox.getChildren().add(texto);
                }
            }
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

    private void mostrarLaser(Parent root){
        var pane = (Pane) root.lookup("#pane");

        /* Recorro la GridPane eliminando solo las instancias de Line para reinicar la trayectoria del laser */
        var copiaChildren = new ArrayList<>(pane.getChildren());
        for (Node child : copiaChildren) {
            if (child instanceof Line && ((Line) child).getStroke() == RED) {
                pane.getChildren().remove(child);
            }
        }

        for(Target target : game.getTableroActual().getTargets()){
            target.reiniciarTarget();
        }

        var copiaLasers = new ArrayList<>(game.getTableroActual().getLasers());
        for (Laser laser : copiaLasers) {
            laser.reiniciarTrayectoria();
            game.getTableroActual().moverLaser(laser);

            for(int i = 0; i < laser.getTrayectoria().size()-1; i++){
                dibujarLaser(pane, laser.getTrayectoria().get(i), laser.getTrayectoria().get(i+1));
            }

            /* Si entro a este if, significa que se creo un nuevo laser (Bloque de Vidrio) */
            if(game.getTableroActual().getLasers().size() > copiaLasers.size()){
                Laser laserCreado = game.getTableroActual().getLasers().get(1);
                laserCreado.reiniciarTrayectoria();
                game.getTableroActual().moverLaser(laserCreado);

                for(int i = 0; i < laserCreado.getTrayectoria().size()-1; i++){
                    dibujarLaser(pane, laserCreado.getTrayectoria().get(i), laserCreado.getTrayectoria().get(i+1));
                }
            }
        }
    }

    private void dibujarLaser(Pane pane, Posicion fromPos, Posicion toPos){
        Posicion fromPosView = convertirCoordAView(fromPos);
        Posicion toPosView = convertirCoordAView(toPos);

        Line laserLine = new Line(fromPosView.getCoordX(), fromPosView.getCoordY(),
                toPosView.getCoordX(), toPosView.getCoordY());
        laserLine.setStrokeWidth(2);
        laserLine.setStroke(Color.RED);
        pane.getChildren().add(laserLine);
    }

    // Convierte las coordenadas del Modelo a coordenadas de la Vista (píxeles)
    private Posicion convertirCoordAView(Posicion pos) {
        return new Posicion(pos.getCoordX() * (CELL_W/2), pos.getCoordY() * (CELL_H/2));
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
