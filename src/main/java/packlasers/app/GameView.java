package packlasers.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import packlasers.Game;


import java.io.IOException;
import java.net.URL;

public class GameView {
    private final Stage stage;
    private final Game game;

    public GameView(Stage stage, Game game) {
        this.stage = stage;
        this.game = game;
    }

    // Cargar un nivel por índice
    public void cargarNivel(int nivel) {
        String nivelFXML = game.getNivelFXML(nivel);
        if (nivelFXML != null) {
            try {
                URL location = getClass().getResource(nivelFXML);
                FXMLLoader loader = new FXMLLoader(location);
                Parent root = loader.load();

                // Establecer el controlador del nivel
                GameController controller = loader.getController();
                controller.setGame(game);
                controller.setGameView(this); // Establece gameView

                // Seleccionar el botón correspondiente al nivel actual
                ToggleButton selectedButton = controller.getButtonForLevel(nivel);
                if (selectedButton != null) {
                    controller.getToggleGroup().selectToggle(selectedButton);
                }

                GridPane grilla = (GridPane) root.lookup("#grilla");
                if (grilla == null) {
                    System.out.println("No se pudo encontrar la grilla.");
                    return;
                }

                var canvas = (Group) root.lookup("#canvas");
                if (canvas == null) {
                    System.out.println("No se pudo encontrar el canvas.");
                    return;
                }

                controller.inicializarJuego(grilla, canvas);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}