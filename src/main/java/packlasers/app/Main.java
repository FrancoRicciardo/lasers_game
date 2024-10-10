package packlasers.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
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

        GameController controller = loader.getController();
        controller.setGame(game);        // Establecer el objeto Game
        controller.setGameView(gameView);
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

        stage.setScene(new Scene(root));
        stage.setTitle("Lasers");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

