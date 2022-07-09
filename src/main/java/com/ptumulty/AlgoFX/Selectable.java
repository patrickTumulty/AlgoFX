package com.ptumulty.AlgoFX;

import javafx.beans.property.BooleanProperty;

public interface Selectable
{
    boolean isSelected();

    BooleanProperty selectedProperty();
}
