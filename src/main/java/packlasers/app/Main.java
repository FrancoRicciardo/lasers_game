package packlasers.app;

import javafx.application.Application;
import javafx.stage.Stage;
import packlasers.Game;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Game game = new Game();
        GameView gameView = new GameView(stage, game);

        // Cargar el FXML del nivel inicial
        gameView.cargarNivel(1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

