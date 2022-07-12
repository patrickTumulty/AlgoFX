package com.ptumulty.AlgoFX;

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
        ArraySorter arraySorter = new ArraySorter();
        ArraySortView arraySortView = new ArraySortView(arraySorter);
        arraySortView.getView().setPrefSize(1000, 500);
        Scene scene = new Scene(arraySortView.getView());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
