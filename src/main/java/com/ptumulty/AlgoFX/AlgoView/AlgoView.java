package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.components.ComponentSettingGroup;
import javafx.scene.Node;

import java.util.List;

public interface AlgoView
{
    String getTitle();

    void initView();

    void dispose();

    Node getVisualization();

    Node getControls();

    List<ComponentSettingGroup> getSettings();
}
