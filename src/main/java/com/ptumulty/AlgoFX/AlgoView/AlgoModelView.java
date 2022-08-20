package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.AlgoFX.AlgoModel.AlgoModelController;
import com.ptumulty.ceramic.components.ComponentSettingGroup;
import com.ptumulty.ceramic.models.ChoiceModel;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;

public interface AlgoModelView
{
    String getTitle();

    void dispose();

    Pane getVisualizationPane();

    String getAlgoActionName();

    Optional<ChoiceModel<String>> getAlgoModes();

    void doAlgoAction();

    void doAlgoReset();

    void doAlgoCancel();

    List<ComponentSettingGroup> getSettings();

    BooleanProperty busyProperty();

    boolean matchesController(AlgoModelController controller);
}
