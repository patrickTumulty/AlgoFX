package com.ptumulty.AlgoFX;

import com.ptumulty.AlgoFX.Sorter.ArraySorter;
import com.ptumulty.AlgoFX.SorterView.ArraySortView;
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
        ArraySorter arraySorter = new ArraySorter();
        ArraySortView arraySortView = new ArraySortView(arraySorter);
        FxUtils.setStaticPaneSize(arraySortView.getView(), 1000, 600);
        Scene scene = new Scene(arraySortView.getView());
        scene.getStylesheets().add("css/sleek.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
