package com.ptumulty.AlgoFX;

import com.ptumulty.AlgoFX.AlgoView.AlgoViewLauncherView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlgoFXApplication extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        AlgoViewLauncherView launcherView = new AlgoViewLauncherView(3);

        launcherView.getView().getStyleClass().add("panel-primary");
        launcherView.getView().setMinSize(1000, 600);

        Scene scene = new Scene(launcherView.getView());
        scene.getStylesheets().add("css/sleek.css");
        scene.getStylesheets().add("css/algo_view.css");
        scene.getStylesheets().add("css/rectangle.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("AlgoFX");
        primaryStage.show();
    }
}
