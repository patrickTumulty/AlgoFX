package com.ptumulty.AlgoFX.CapabilitiesUI;

import com.ptumulty.AlgoFX.Capabilities.AlgoCapability;
import com.ptumulty.AlgoFX.Capabilities.OperationsCounter;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class OperationsCounterCapabilityProvider implements AlgoUICapabilityProvider<OperationsCounter>
{
    private OperationsCounterView operationsCounterView;

    @Override
    public boolean matchesCapable(AlgoCapability capability)
    {
        return capability instanceof OperationsCounter;
    }

    @Override
    public void initUI(OperationsCounter capable)
    {
        operationsCounterView = new OperationsCounterView(capable);
    }

    @Override
    public Pane getView()
    {
        return operationsCounterView;
    }

    @Override
    public FontIcon getIcon()
    {
        return new FontIcon(FontAwesomeSolid.CHART_LINE);
    }
}
