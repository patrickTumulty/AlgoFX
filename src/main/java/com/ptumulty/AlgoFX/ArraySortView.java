package com.ptumulty.AlgoFX;

import com.ptumulty.ceramic.components.BoundIntegerSpinnerComponent;
import com.ptumulty.ceramic.components.IntegerSpinnerComponent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ArraySortView
{
    private final ArraySorter sorter;

    private BorderPane borderPane;
    private ArrayComponent<Integer> arrayComponent;

    ArraySortView(ArraySorter sorter)
    {
        this.sorter = sorter;

        borderPane = new BorderPane();

        StackPane arraySortPane = new StackPane();
        borderPane.setCenter(arraySortPane);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        BoundIntegerSpinnerComponent spinnerComponent = new BoundIntegerSpinnerComponent(sorter.getArraySizeModel());
        hBox.getChildren().add(spinnerComponent.getRenderer());
        Button sortArray = new Button("Sort");
        sortArray.disableProperty().set(true);
        sortArray.setOnAction(event ->
        {

        });
        Button generateArray = new Button("Generate");
        generateArray.setOnAction(event ->
        {
            arraySortPane.getChildren().clear();
            sorter.generateNewArray();
            sorter.scrambleArray();
            if (sorter.getArrayModel().isPresent())
            {
                arrayComponent = new ArrayComponent<>(sorter.getArrayModel().get());
                arraySortPane.getChildren().add(arrayComponent.getRenderer());
                StackPane.setAlignment(arrayComponent.getRenderer(), Pos.CENTER);
            }
            sortArray.disableProperty().set(false);
        });
        hBox.getChildren().add(generateArray);
        hBox.getChildren().add(sortArray);
        borderPane.setBottom(hBox);

    }

    public Pane getView()
    {
        return borderPane;
    }
}
