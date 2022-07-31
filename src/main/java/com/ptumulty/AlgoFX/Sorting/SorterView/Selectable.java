package com.ptumulty.AlgoFX.Sorting.SorterView;

import javafx.beans.property.BooleanProperty;

public interface Selectable
{
    boolean isSelected();

    BooleanProperty selectedProperty();
}
