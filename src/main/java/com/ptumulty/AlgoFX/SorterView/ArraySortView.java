package com.ptumulty.AlgoFX.SorterView;

import com.ptumulty.AlgoFX.Sorter.ArraySorter;
import com.ptumulty.ceramic.components.BoundIntegerSpinnerComponent;
import com.ptumulty.ceramic.components.BoundSliderComponent;
import com.ptumulty.ceramic.components.ChoiceComponent;
import com.ptumulty.ceramic.utility.ThreadUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

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
        setBackground();

        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        BorderPane.setMargin(hBox, new Insets(20, 0, 20, 0));

        configureSortingAlgorithmChoiceComponent();

        configureTimeDelaySlider(sorter);

        configureArraySizeSpinner(sorter);

        configureSortArrayButton();

        configureGenerateArrayButton();
    }

    private void configureTimeDelaySlider(ArraySorter sorter)
    {
        BoundSliderComponent timeDelaySliderComponent = new BoundSliderComponent(sorter.getCurrentTimeControlledSorter().getTimeStepIntegerModel());
        timeDelaySliderComponent.getRenderer().setPrefWidth(220);
        timeDelaySliderComponent.setSpacing(10);
        timeDelaySliderComponent.getLabelComponent().setSuffix("ms");
        hBox.getChildren().add(timeDelaySliderComponent.getRenderer());
    }

    private void configureSortingAlgorithmChoiceComponent()
    {
        ChoiceComponent<String> sortingAlgorithmChoiceComponent = new ChoiceComponent<>(sorter.getSortingAlgorithmChoiceModel());
        sortingAlgorithmChoiceComponent.getRenderer().setPrefWidth(200);
        borderPane.setTop(sortingAlgorithmChoiceComponent.getRenderer());
        BorderPane.setAlignment(sortingAlgorithmChoiceComponent.getRenderer(), Pos.CENTER);
        BorderPane.setMargin(sortingAlgorithmChoiceComponent.getRenderer(), new Insets(20, 0, 20, 0));
    }

    private void setBackground()
    {
        Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.DARKMAGENTA)};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        BackgroundFill backgroundFill = new BackgroundFill(gradient, null, null);
        borderPane.setBackground(new Background(backgroundFill));
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
                arrayComponent.setRectangleColor(Color.WHITE);
                arrayComponent.setSelectedRectangleColor(Color.LIGHTGREEN);
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
