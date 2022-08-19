package com.ptumulty.AlgoFX.CapabilitiesUI;

import com.ptumulty.AlgoFX.Capabilities.OperationsCounter;
import com.ptumulty.ceramic.components.ComponentSettingGroup;
import com.ptumulty.ceramic.components.LabelComponent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.util.List;


public class OperationsCounterView extends StackPane
{
    private final OperationsCounter operationsCounter;
    private final ComponentSettingGroup componentSettingGroup;
    private final LabelComponent operationsCountLabel;
    private final LabelComponent dataSetSizeLabel;

    OperationsCounterView(OperationsCounter operationsCounter)
    {
        this.operationsCounter = operationsCounter;

        operationsCountLabel = new LabelComponent(this.operationsCounter.getOperationsCountModel());
        operationsCountLabel.setLabel("Operations");
        dataSetSizeLabel = new LabelComponent(this.operationsCounter.getDataSetSizeModel());
        dataSetSizeLabel.setLabel("Data Set Size");

        componentSettingGroup = new ComponentSettingGroup("Operations Counter", List.of(operationsCountLabel, dataSetSizeLabel));
        getChildren().add(componentSettingGroup.getRenderer());
        StackPane.setAlignment(componentSettingGroup.getRenderer(), Pos.CENTER);
        StackPane.setMargin(componentSettingGroup.getRenderer(), new Insets(10));
    }

    public void dispose()
    {
        operationsCountLabel.detachModel();
        dataSetSizeLabel.detachModel();
    }
}
