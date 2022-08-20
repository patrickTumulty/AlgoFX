package com.ptumulty.AlgoFX.AlgoView.SorterView;

import com.ptumulty.ceramic.utility.FxUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SelectableRectangle extends Rectangle implements Selectable
{
    private final BooleanProperty selectedProperty;
    private Paint selectedColor = Color.BLACK;
    private Paint fillColor = Color.BLACK;

    SelectableRectangle(int width, int height)
    {
        super(width, height);

        selectedProperty = new SimpleBooleanProperty(false);
        selectedProperty.addListener((observable, oldValue, newValue) ->
                FxUtils.run(() -> setFill(newValue ? selectedColor : fillColor)));
    }

    public void setFillColor(Paint fillColor)
    {
        this.fillColor = fillColor;

        setFill(this.fillColor);
    }

    void setSelectedColor(Paint selectedColor)
    {
        this.selectedColor = selectedColor;
    }

    @Override
    public boolean isSelected()
    {
        return selectedProperty.get();
    }

    @Override
    public BooleanProperty selectedProperty()
    {
        return selectedProperty;
    }
}
