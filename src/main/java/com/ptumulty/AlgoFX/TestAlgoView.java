package com.ptumulty.AlgoFX;

import com.ptumulty.AlgoFX.AlgoView.AlgoView;
import com.ptumulty.ceramic.components.*;
import com.ptumulty.ceramic.models.BooleanModel;
import com.ptumulty.ceramic.models.BoundIntegerModel;
import com.ptumulty.ceramic.models.ChoiceModel;
import com.ptumulty.ceramic.models.StringModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestAlgoView implements AlgoView
{

    private List<ComponentSettingGroup> settingGroupList;

    @Override
    public String getTitle()
    {
        return "Test";
    }

    @Override
    public void initView()
    {
        StringModel stringModel = new StringModel("Data 1234");
        StringComponent stringComponent = new StringComponent(stringModel);
        stringComponent.setLabel("Data");

        BoundIntegerModel boundIntegerModel = new BoundIntegerModel(3, Optional.of(0), Optional.of(20));
        BoundIntegerSpinnerComponent boundIntegerSpinnerComponent = new BoundIntegerSpinnerComponent(boundIntegerModel);
        boundIntegerSpinnerComponent.setLabel("Spinner");

        BooleanModel booleanModel = new BooleanModel(true);
        BooleanComponent booleanComponent = new BooleanComponent(booleanModel);
        booleanComponent.setLabel("TrueFalse");

        List<String> choices = List.of("Choice A", "Choice B", "Choice C");
        ChoiceModel<String> choiceModel = new ChoiceModel<>(choices.get(0), choices);
        ChoiceComponent<String> choiceComponent = new ChoiceComponent<>(choiceModel);
        choiceComponent.setLabel("Choices");

        settingGroupList = new ArrayList<>();
        settingGroupList.add(new ComponentSettingGroup("Group 1", List.of(stringComponent, boundIntegerSpinnerComponent)));

        settingGroupList.add(new ComponentSettingGroup("Group 2", List.of(booleanComponent, choiceComponent)));
    }

    @Override
    public void dispose()
    {

    }

    @Override
    public Node getVisualization()
    {
        return new Rectangle(100, 200);
    }

    @Override
    public Node getControls()
    {
        HBox hBox = new HBox(new Button("Action 1"), new Button("Action 2"), new Button("Action 3"));
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    @Override
    public List<ComponentSettingGroup> getSettings()
    {
        return settingGroupList;
    }
}
