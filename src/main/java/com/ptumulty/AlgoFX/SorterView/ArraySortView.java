package com.ptumulty.AlgoFX.SorterView;

import com.ptumulty.AlgoFX.Sorter.ArraySorter;
import com.ptumulty.ceramic.components.BoundIntegerSpinnerComponent;
import com.ptumulty.ceramic.components.BoundSliderComponent;
import com.ptumulty.ceramic.components.ChoiceComponent;
import com.ptumulty.ceramic.utility.ThreadUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ArraySortView
{
    private final ArraySorter sorter;
    private final HBox hBox;

    private final BorderPane borderPane;
    private ArrayComponent<Integer> arrayComponent;
    private Button sortArrayButton;

    public ArraySortView(ArraySorter sorter)
    {
        this.sorter = sorter;

        borderPane = new BorderPane();

        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);

        if (sorter.getSortingAlgorithmChoiceModel().isPresent())
        {
            ChoiceComponent<String> sortingAlgorithmChoiceComponent = new ChoiceComponent<>(sorter.getSortingAlgorithmChoiceModel().get());
            borderPane.setTop(sortingAlgorithmChoiceComponent.getRenderer());
            BorderPane.setAlignment(sortingAlgorithmChoiceComponent.getRenderer(), Pos.CENTER);
        }

        BoundSliderComponent timeDelaySliderComponent = new BoundSliderComponent(sorter.getCurrentTimeControlledSorter().getTimeStepIntegerModel());
        timeDelaySliderComponent.getRenderer().setPrefWidth(250);
        timeDelaySliderComponent.setSpacing(10);

        timeDelaySliderComponent.getLabelComponent().setSuffix("ms");
        hBox.getChildren().add(timeDelaySliderComponent.getRenderer());

        configureArraySizeSpinner(sorter);

        configureSortArrayButton();

        configureGenerateArrayButton();
    }

    private void configureArraySizeSpinner(ArraySorter sorter)
    {
        BoundIntegerSpinnerComponent spinnerComponent = new BoundIntegerSpinnerComponent(sorter.getArraySizeModel());
        hBox.getChildren().add(spinnerComponent.getRenderer());
    }

    private void configureSortArrayButton()
    {
        sortArrayButton = new Button("Sort");
        sortArrayButton.disableProperty().set(true);
        sortArrayButton.setOnAction(event -> ThreadUtils.run((sorter::sort)));
        hBox.getChildren().add(sortArrayButton);
    }

    private void configureGenerateArrayButton()
    {
        Button generateArray = new Button("Generate");
        generateArray.setOnAction(event ->
        {
            sorter.generateNewArray();
            sorter.scrambleArray();
            if (sorter.getArrayModel().isPresent())
            {
                if (arrayComponent != null)
                {
                    sorter.getArrayModel().get().removeListener(arrayComponent);
                }
                arrayComponent = new ArrayComponent<>(sorter.getArrayModel().get());
                arrayComponent.setArrayWidth(600);
                borderPane.setCenter(arrayComponent.getRenderer());
                BorderPane.setAlignment(arrayComponent.getRenderer(), Pos.CENTER);
            }
            sortArrayButton.disableProperty().set(false);
        });
        hBox.getChildren().add(generateArray);
    }

    public Pane getView()
    {
        return borderPane;
    }
}
