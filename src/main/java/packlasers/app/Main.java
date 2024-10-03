package packlasers.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import packlasers.Game;
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Game game = new Game();
        GameView gameView = new GameView(stage, game);

        // Cargar el FXML del nivel inicial
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistaNivel1.fxml"));
        Parent root = loader.load();

        LevelController controller = loader.getController();
        controller.setGame(game);        // Establecer el objeto Game
        controller.setGameView(gameView); // Establecer el objeto GameView

        stage.setScene(new Scene(root));
        stage.setTitle("Lasers");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

