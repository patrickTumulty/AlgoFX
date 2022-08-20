package com.ptumulty.AlgoFX.AlgoView.SorterView;

import javafx.beans.property.BooleanProperty;

public interface Selectable
{
    boolean isSelected();

    BooleanProperty selectedProperty();
}
