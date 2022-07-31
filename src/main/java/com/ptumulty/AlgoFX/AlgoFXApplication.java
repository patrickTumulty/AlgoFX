package com.ptumulty.AlgoFX;

import com.ptumulty.AlgoFX.AlgoView.AlgoViewLauncherView;
import com.ptumulty.ceramic.utility.FxUtils;
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

        FxUtils.setStaticRegionSize(launcherView.getView(), 1000, 600);
        Scene scene = new Scene(launcherView.getView());
//        scene.getStylesheets().add("css/sleek.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
