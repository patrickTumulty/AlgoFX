package com.ptumulty.AlgoFX;

import com.ptumulty.ceramic.utility.ThreadUtils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
        ArrayModel<Integer> model = new ArrayModelImpl<>(15);
        for (int i = 0; i < model.size(); i++)
        {
            model.set(i, i);
        }
        model.scramble();
        ArrayComponent<Integer> arrayComponent = new ArrayComponent<>(model);
        arrayComponent.setElementAlignment(Pos.BOTTOM_CENTER);

        BorderPane borderPane = new BorderPane();

        StackPane stackPane = new StackPane(arrayComponent.getRenderer());
        StackPane.setAlignment(arrayComponent.getRenderer(), Pos.CENTER);
        borderPane.setCenter(stackPane);

        Button button = new Button("Run");
        button.setOnAction(event ->
        {
            ThreadUtils.run(() ->
            {
                for (int i = 0; i < model.size(); i++)
                {
                    model.get(i);
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                arrayComponent.clearSelected();


                for (int i = 0; i < model.size(); i++)
                {
                    model.set(i, 4);
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
        borderPane.setBottom(button);


        borderPane.setPrefSize(1000, 500);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);

        primaryStage.show();

    }
}
