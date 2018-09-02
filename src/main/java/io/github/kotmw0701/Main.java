package io.github.kotmw0701;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static AutoLoadingRunnable autoLoadingRunnable;

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("学籍番号読み取りマン");
        primaryStage.setScene(new Scene(FXMLLoader.load(ClassLoader.getSystemResource("Main.fxml")), 400, 200));
        primaryStage.setOnCloseRequest(event -> {
            if (autoLoadingRunnable.running)
                autoLoadingRunnable.cancel();
        });
        primaryStage.show();
    }
}
