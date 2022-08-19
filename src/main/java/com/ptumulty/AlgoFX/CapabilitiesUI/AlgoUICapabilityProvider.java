package com.ptumulty.AlgoFX.CapabilitiesUI;

import com.ptumulty.AlgoFX.Capabilities.AlgoCapability;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

public interface AlgoUICapabilityProvider<T extends AlgoCapability>
{
    boolean matchesCapable(AlgoCapability capability);

    void initUI(T capable);

    Pane getView();

    FontIcon getIcon();
}
