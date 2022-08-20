package com.ptumulty.AlgoFX.CapabilitiesUI;

import com.ptumulty.AlgoFX.Capabilities.AlgoCapability;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

public interface AlgoCapabilityUIProvider<T extends AlgoCapability>
{
    boolean matchesCapable(AlgoCapability capability);

    Pane createCapabilityView(T capability);

    FontIcon getIcon();

    String getTitle();
}
