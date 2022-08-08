package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.components.ComponentSettingGroup;
import javafx.scene.layout.Pane;

import java.util.List;

public interface AlgoView
{
    String getTitle();

    void initView();

    void dispose();

    Pane getVisualizationPane();

    String getAlgoActionName();

    void doAlgoAction();

    void doAlgoReset();

    List<ComponentSettingGroup> getSettings();
}
