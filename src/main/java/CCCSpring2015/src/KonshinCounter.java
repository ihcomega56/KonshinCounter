package main.java.CCCSpring2015.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KonshinCounter extends Application {

    public static void main(String... args) {
        launch(args);
    }

    /**
     * GUIを組み立てるメソッド
     * @param stage 表示ウィンドウ
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("KonshinCounter.fxml"));
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

}