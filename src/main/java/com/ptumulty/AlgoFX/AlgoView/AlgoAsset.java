package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.components.ComponentSettingGroup;
import com.ptumulty.ceramic.models.ChoiceModel;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;

public interface AlgoAsset
{
    String getTitle();

    void initView();

    void dispose();

    Pane getVisualizationPane();

    String getAlgoActionName();

    Optional<ChoiceModel<String>> getAlgoModes();

    void doAlgoAction();

    void doAlgoReset();

    void doAlgoCancel();

    List<ComponentSettingGroup> getSettings();

    BooleanProperty busyProperty();
}
