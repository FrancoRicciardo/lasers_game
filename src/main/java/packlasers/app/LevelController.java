package packlasers.app;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import packlasers.Game;
import javafx.scene.control.ToggleGroup;


public class LevelController {
    private GameView gameView; // Referencia a GameView
    @FXML
    private ToggleButton buttonLevel1;
    @FXML
    private ToggleButton buttonLevel2;
    @FXML
    private ToggleButton buttonLevel3;
    @FXML
    private ToggleButton buttonLevel4;
    @FXML
    private ToggleButton buttonLevel5;
    @FXML
    private ToggleButton buttonLevel6;
    private ToggleGroup toggleGroup;
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView; // Establece la referencia aquí
    }

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();

        // Asignar el ToggleGroup a cada ToggleButton
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

    public ToggleButton getButtonForLevel(int nivel) {
        return switch (nivel) {
            case 1 -> buttonLevel1;
            case 2 -> buttonLevel2;
            case 3 -> buttonLevel3;
            case 4 -> buttonLevel4;
            case 5 -> buttonLevel5;
            case 6 -> buttonLevel6;
            default -> null; // En caso de un nivel inválido
        };
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    // Metodo para seleccionar el nivel y desmarcar otros botones
    private void seleccionarNivel( int nivel) {
        gameView.cargarNivel(nivel);
     }
}
