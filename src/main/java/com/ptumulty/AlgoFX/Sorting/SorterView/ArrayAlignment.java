package com.ptumulty.AlgoFX.Sorting.SorterView;

import javafx.geometry.Pos;

public enum ArrayAlignment
{
    TOP("Top", Pos.TOP_CENTER),
    CENTER("Center", Pos.CENTER),
    BOTTOM("Bottom", Pos.BOTTOM_CENTER);

    private final String label;
    private final Pos alignment;

    ArrayAlignment(String label, Pos alignment)
    {
        this.label = label;
        this.alignment = alignment;
    }

    public String getLabel()
    {
        return label;
    }

    public Pos getAlignment()
    {
        return alignment;
    }
}
