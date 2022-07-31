package com.ptumulty.AlgoFX.AlgoView;

import javafx.scene.Node;

public interface AlgoView
{
    String getTitle();

    void initView();

    void dispose();

    Node getVisualization();

    Node getControls();
}
