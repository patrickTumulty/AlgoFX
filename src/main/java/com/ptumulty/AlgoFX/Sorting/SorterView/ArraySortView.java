package com.ptumulty.AlgoFX.Sorting.SorterView;

import com.ptumulty.AlgoFX.ArrayGenerationMethod;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArraySorterController;
import com.ptumulty.ceramic.components.BoundIntegerSpinnerComponent;
import com.ptumulty.ceramic.components.BoundSliderComponent;
import com.ptumulty.ceramic.components.ChoiceComponent;
import com.ptumulty.ceramic.models.ChoiceModel;
import com.ptumulty.ceramic.utility.FxUtils;
import com.ptumulty.ceramic.utility.ThreadUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.List;

public class ArraySortView
{
    private final ArraySorterController sortController;
    private final HBox hBox;

    private final BorderPane borderPane;
    private final VBox vBox;
    private ArrayComponent<Integer> arrayComponent;
    private Button sortArrayButton;
    private Button scrambleButton;
    private ChoiceModel<ArrayAlignment> alignmentChoiceModel;

    public ArraySortView(ArraySorterController sortController)
    {
        this.sortController = sortController;

        borderPane = new BorderPane();
//        setBackground();

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        borderPane.setBottom(vBox);
        BorderPane.setAlignment(vBox, Pos.CENTER);
        BorderPane.setMargin(vBox, new Insets(20, 0, 40, 0));

        configureTimeDelaySlider();

        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        vBox.getChildren().add(hBox);

        configureSortingAlgorithmChoiceComponent();

        configureAlignmentChoiceComponent();

        configureArraySizeSpinner();

        configureArrayGeneratorChoiceComponent();

        configureGenerateArrayButton();

        configureScrambleButton();

        configureSortArrayButton();
    }

    private void configureAlignmentChoiceComponent()
    {
        alignmentChoiceModel = new ChoiceModel<>(ArrayAlignment.CENTER, List.of(ArrayAlignment.values()));
        alignmentChoiceModel.addListener(currentValue ->
        {
            if (arrayComponent != null)
            {
                FxUtils.run(() -> arrayComponent.setElementAlignment(alignmentChoiceModel.get().getAlignment()));
            }
        });
        ChoiceComponent<ArrayAlignment> alignmentChoices = new ChoiceComponent<>(alignmentChoiceModel);
        alignmentChoices.getRenderer().setPrefWidth(130);
        hBox.getChildren().add(alignmentChoices.getRenderer());
    }

    private void configureScrambleButton()
    {
        scrambleButton = new Button("Scramble");
        scrambleButton.setOnAction(event ->
                sortController.getArrayModel().ifPresent(ArrayModel::scramble));
        scrambleButton.disableProperty().set(true);
        hBox.getChildren().add(scrambleButton);
    }

    private void configureArrayGeneratorChoiceComponent()
    {
        ChoiceComponent<ArrayGenerationMethod> arrayGenerationChoiceComponent = new ChoiceComponent<>(sortController.getArrayGenerationChoiceModel());
        arrayGenerationChoiceComponent.getRenderer().setPrefWidth(150);
        hBox.getChildren().add(arrayGenerationChoiceComponent.getRenderer());
    }

    private void configureTimeDelaySlider()
    {
        BoundSliderComponent timeDelaySliderComponent = new BoundSliderComponent(sortController.getCurrentTimeControlledSorter().getTimeStepIntegerModel());
        sortController.getSortingAlgorithmChoiceModel().addListener(currentValue ->
                timeDelaySliderComponent.attachModel(sortController.getCurrentTimeControlledSorter().getTimeStepIntegerModel()));
        timeDelaySliderComponent.setLabelWidth(80);
        timeDelaySliderComponent.setWidth(400);
        timeDelaySliderComponent.setSpacing(10);
        timeDelaySliderComponent.getLabelComponent().setSuffix("ms");

        vBox.getChildren().add(timeDelaySliderComponent.getRenderer());
    }

    private void configureSortingAlgorithmChoiceComponent()
    {
        ChoiceComponent<String> sortingAlgorithmChoiceComponent = new ChoiceComponent<>(sortController.getSortingAlgorithmChoiceModel());
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

    private void configureArraySizeSpinner()
    {
        BoundIntegerSpinnerComponent arraySizeSpinner = new BoundIntegerSpinnerComponent(sortController.getArraySizeModel());
        arraySizeSpinner.getRenderer().setPrefWidth(100);
        hBox.getChildren().add(arraySizeSpinner.getRenderer());
    }

    private void configureSortArrayButton()
    {
        sortArrayButton = new Button("Sort");
        sortArrayButton.setMinWidth(200);
        sortArrayButton.setPrefWidth(200);
        sortArrayButton.disableProperty().set(true);
        sortArrayButton.setOnAction(event -> ThreadUtils.run((sortController::sort)));
        vBox.getChildren().add(sortArrayButton);
    }

    private void configureGenerateArrayButton()
    {
        Button generateArray = new Button("Generate");
        generateArray.setOnAction(event ->
        {
            sortController.generateNewArray();
            sortController.scrambleArray();
            if (sortController.getArrayModel().isPresent())
            {
                if (arrayComponent != null)
                {
                    sortController.getArrayModel().get().removeListener(arrayComponent);
                }
                arrayComponent = new ArrayComponent<>(sortController.getArrayModel().get());
                arrayComponent.setElementAlignment(alignmentChoiceModel.get().getAlignment());
                arrayComponent.setRectangleColor(Color.WHITE);
                arrayComponent.setSelectedRectangleColor(Color.LIGHTGREEN);
                arrayComponent.setArrayWidth(800);
                borderPane.setCenter(arrayComponent.getRenderer());
                BorderPane.setAlignment(arrayComponent.getRenderer(), Pos.CENTER);
            }
            sortArrayButton.disableProperty().set(false);
            scrambleButton.disableProperty().set(false);
        });
        hBox.getChildren().add(generateArray);
    }

    public Pane getView()
    {
        return borderPane;
    }
}
