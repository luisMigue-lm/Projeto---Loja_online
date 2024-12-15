import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/view/TelaLogin.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Morcegão | Login");
        primaryStage.getIcons().add(new Image("file:src/resources/imgs/Logo - Laranja.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
