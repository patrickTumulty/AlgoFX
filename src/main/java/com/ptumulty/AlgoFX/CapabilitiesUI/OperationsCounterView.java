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
        LabelComponent.ValueToStringConverter intCommaConverter = value ->
        {
            if (value instanceof Integer)
            {
                return String.format("%,d", value);
            }
            return value.toString();
        };

        operationsCountLabel = new LabelComponent(this.operationsCounter.getOperationsCountModel());
        operationsCountLabel.setValueStringConverter(intCommaConverter);
        operationsCountLabel.setLabel("Operations");

        operationsCountLabel.setValueStringConverter(intCommaConverter);
        dataSetSizeLabel = new LabelComponent(this.operationsCounter.getDataSetSizeModel());
        dataSetSizeLabel.setValueStringConverter(intCommaConverter);
        dataSetSizeLabel.setLabel("Data Set Size");

        componentSettingGroup = new ComponentSettingGroup("Operations Counter", List.of(operationsCountLabel, dataSetSizeLabel));
        componentSettingGroup.showTitle(false);
        getChildren().add(componentSettingGroup.getRenderer());
        StackPane.setAlignment(componentSettingGroup.getRenderer(), Pos.CENTER);
        StackPane.setMargin(componentSettingGroup.getRenderer(), new Insets(0, 10, 0, 10));
    }
}
