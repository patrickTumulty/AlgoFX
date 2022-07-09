package com.ptumulty.AlgoFX;

import com.ptumulty.ceramic.components.BooleanComponent;
import com.ptumulty.ceramic.models.BooleanModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
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

        BooleanComponent component = new BooleanComponent(new BooleanModel(false));

        Pane pane = new Pane(component.getRenderer());
        primaryStage.setScene(new Scene(pane));

        primaryStage.show();

    }
}
