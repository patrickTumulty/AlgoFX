package com.ptumulty.AlgoFX.AlgoView.SorterView;

import com.ptumulty.AlgoFX.AlgoModel.AlgoModelController;
import com.ptumulty.AlgoFX.AlgoView.AlgoModelView;
import com.ptumulty.AlgoFX.ArrayGenerationMethod;
import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArraySorterController;
import com.ptumulty.ceramic.components.*;
import com.ptumulty.ceramic.models.ChoiceModel;
import com.ptumulty.ceramic.utility.FxUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SortingAlgoModelView implements AlgoModelView
{
    private final ArraySorterController arraySorter;
    private final List<ComponentSettingGroup> sortingSettings;
    private final StackPane visualizationPane;
    private final BooleanProperty busyProperty;
    private ArrayComponent<Integer> arrayComponent;
    private ChoiceModel<ArrayAlignment> alignmentChoiceModel;

    public SortingAlgoModelView(ArraySorterController arraySortController)
    {
        arraySorter = arraySortController;
        busyProperty = new SimpleBooleanProperty(false);

        sortingSettings = new ArrayList<>();
        visualizationPane = new StackPane();

        configureSortingSettings();

        generateNewArray();
    }
    
    @Override
    public String getTitle()
    {
        return "Sorting";
    }

    private void generateNewArray()
    {
        arraySorter.generateNewArray();
        arraySorter.scrambleArray();
        if (arraySorter.getArrayModel().isPresent())
        {
            if (arrayComponent != null)
            {
                arraySorter.getArrayModel().get().removeListener(arrayComponent);
                visualizationPane.getChildren().remove(0);
            }
            arrayComponent = new ArrayComponent<>(arraySorter.getArrayModel().get());
            arrayComponent.setElementAlignment(alignmentChoiceModel.get().getAlignment());
            arrayComponent.setRectangleColor(Color.DARKSEAGREEN);
            arrayComponent.setSelectedRectangleColor(Color.MAGENTA);
            visualizationPane.getChildren().add(0, arrayComponent.getRenderer());
            StackPane.setAlignment(arrayComponent.getRenderer(), Pos.CENTER);
            StackPane.setMargin(arrayComponent.getRenderer(), new Insets(0, 40, 0, 40));
        }
    }

    private void configureSortingSettings()
    {
        configureSortingSetting();

        configureArrayGenerationSettings();
    }

    private void configureArrayGenerationSettings()
    {
        BoundIntegerSpinnerComponent arraySizeSpinner = new BoundIntegerSpinnerComponent(arraySorter.getArraySizeModel());
        arraySizeSpinner.setLabel("Array Size");

        ChoiceComponent<ArrayGenerationMethod> arrayGenerationChoiceComponent = new ChoiceComponent<>(arraySorter.getArrayGenerationChoiceModel());
        arrayGenerationChoiceComponent.setLabel("Generation Type");

        ButtonComponent generateButtonComponent = new ButtonComponent("Generate", event -> FxUtils.run(this::generateNewArray));
        generateButtonComponent.getRenderer().disableProperty().bind(busyProperty);

        alignmentChoiceModel = new ChoiceModel<>(ArrayAlignment.CENTER, List.of(ArrayAlignment.values()));
        alignmentChoiceModel.addListener(currentValue ->
        {
            if (arrayComponent != null)
            {
                FxUtils.run(() -> arrayComponent.setElementAlignment(alignmentChoiceModel.get().getAlignment()));
            }
        });
        ChoiceComponent<ArrayAlignment> alignmentChoices = new ChoiceComponent<>(alignmentChoiceModel);
        alignmentChoices.setLabel("Alignment");

        sortingSettings.add(new ComponentSettingGroup("Array Generation", List.of(arraySizeSpinner, arrayGenerationChoiceComponent, alignmentChoices, generateButtonComponent)));
    }

    private void configureSortingSetting()
    {
        BoundSliderComponent timeDelaySliderComponent = new BoundSliderComponent(arraySorter.getCurrentTimeControlledSorter().getTimeStepIntegerModel());
        arraySorter.getSortingAlgorithmChoiceModel().addListener(currentValue ->
                timeDelaySliderComponent.attachModel(arraySorter.getCurrentTimeControlledSorter().getTimeStepIntegerModel()));
        timeDelaySliderComponent.setLabel("Delay (ms)");

        ChoiceComponent<String> sortingAlgorithmChoiceComponent = new ChoiceComponent<>(arraySorter.getSortingAlgorithmChoiceModel());
        sortingAlgorithmChoiceComponent.setLabel("Algorithm");
        sortingAlgorithmChoiceComponent.getRenderer().setPrefWidth(200);

        sortingSettings.add(new ComponentSettingGroup("Sorting", List.of(sortingAlgorithmChoiceComponent, timeDelaySliderComponent)));
    }

    @Override
    public void dispose()
    {
        arrayComponent = null;
    }

    @Override
    public Pane getVisualizationPane()
    {
        return visualizationPane;
    }

    @Override
    public String getAlgoActionName()
    {
        return "Sort";
    }

    @Override
    public Optional<ChoiceModel<String>> getAlgoModes()
    {
        return Optional.of(arraySorter.getSortingAlgorithmChoiceModel());
    }

    @Override
    public void doAlgoAction()
    {
        busyProperty.set(true);
        arraySorter.sort();
        busyProperty.set(false);
    }

    @Override
    public void doAlgoReset()
    {
        busyProperty.set(true);
        arraySorter.scrambleArray();
        busyProperty.set(false);
    }

    @Override
    public void doAlgoCancel()
    {
        arraySorter.getCurrentTimeControlledSorter().cancelSortInProgress();
    }

    @Override
    public List<ComponentSettingGroup> getSettings()
    {
        return sortingSettings;
    }

    @Override
    public BooleanProperty busyProperty()
    {
        return busyProperty;
    }

    @Override
    public boolean matchesController(AlgoModelController controller)
    {
        return controller instanceof ArraySorterController;
    }
}
