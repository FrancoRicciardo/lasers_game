package packlasers.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
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

    /**
     * Cargar un nivel por índice
     */
    public void cargarNivel(int nivel) {
        String nivelFXML = game.getNivelFXML(nivel);
        if (nivelFXML != null) {
            try {
                URL location = getClass().getResource(nivelFXML);
                FXMLLoader loader = new FXMLLoader(location);
                Parent root = loader.load();

                /* Con esto establecemos el controller del nivel */
                GameController controller = loader.getController();
                controller.setGame(game);
                controller.setGameView(this); // Establece gameView

                /* Con esto seleccionamos el botón correspondiente al nivel actual */
                ToggleButton selectedButton = controller.getButtonForLevel(nivel);
                if (selectedButton != null) {
                    controller.getToggleGroup().selectToggle(selectedButton);
                }

                controller.inicializarJuego(root);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
