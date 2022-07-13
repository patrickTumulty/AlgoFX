package com.ptumulty.AlgoFX.SorterView;

import javafx.beans.property.BooleanProperty;

public interface Selectable
{
    boolean isSelected();

    BooleanProperty selectedProperty();
}
