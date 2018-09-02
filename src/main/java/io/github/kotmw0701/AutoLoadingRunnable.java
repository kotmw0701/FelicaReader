package io.github.kotmw0701;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

public class AutoLoadingRunnable extends Thread {

    boolean running = true;

    private Label idm;
    private Label student_id;

    public AutoLoadingRunnable(Label idm, Label student_id) {
        this.idm = idm;
        this.student_id = student_id;
    }

    @Override
    public void run() {
        try (CardReader reader = new CardReader()) {
            reader.open();
            String prev_id = "";
            while (running) {
                Thread.sleep(500);
                String idm = reader.getIDM();
                if (!prev_id.equalsIgnoreCase(idm)) {
                    String student_id = reader.getStudentID();
                    if (!student_id.trim().equalsIgnoreCase("")) {
                        AudioClip audioClip = new AudioClip(ClassLoader.getSystemResource("decision22.mp3").toString());
                        audioClip.play();
                    }
                    Platform.runLater(() -> {
                        this.idm.setText(idm.equalsIgnoreCase("")
                                ? "IDM" : idm);
                        this.student_id.setText(student_id.equalsIgnoreCase("")
                                ? "学籍番号" : student_id);
                    });
                }
                prev_id = idm;
            }
        } catch (Exception e) {
            e.printStackTrace();
            running = false;
        }
    }

    public void cancel() {
        running = false;
    }

    private void ErrorAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("エラー");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
