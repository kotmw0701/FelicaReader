package io.github.kotmw0701;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Label student_id;
    public Label idm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.autoLoadingRunnable = new AutoLoadingRunnable(idm, student_id);
        Main.autoLoadingRunnable.start();
    }
}
