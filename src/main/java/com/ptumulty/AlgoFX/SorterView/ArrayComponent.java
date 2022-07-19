package com.ptumulty.AlgoFX.SorterView;

import com.ptumulty.AlgoFX.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.utility.FxUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class ArrayComponent<T extends Number> implements ArrayModel.Listener<T>
{
    private final ArrayModel<T> model;
    private final HBox renderer;
    private final Map<Integer, SelectableRectangle> rectangleMap;
    private SelectableRectangle currentElement;

    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int maxRectangleHeight = 300;
    private int arrayWidth = 300;
    private int rectangleSpacing = 5;

    ArrayComponent(ArrayModel<T> arrayModel)
    {
        model = arrayModel;
        rectangleMap = new HashMap<>();

        for (int i = 0; i < model.size(); i++)
        {
            min = Math.min(min, model.get(i).intValue());
            max = Math.max(max, model.get(i).intValue());
        }

        renderer = new HBox();
        renderer.setSpacing(rectangleSpacing);
        renderer.setAlignment(Pos.CENTER);

        for (int i = 0; i < model.size(); i++)
        {
            renderer.getChildren().add(createRectangle(i, model.get(i)));
        }

        model.addListener(this);
    }

    public void arrayWidth(int arrayWidth)
    {
        this.arrayWidth = arrayWidth;
    }

    private int calculateRelativeRectangleHeight(float value)
    {
        return (int) (maxRectangleHeight * (value / (float) max));
    }

    public void setArrayWidth(int arrayWidth)
    {
        if (this.arrayWidth != arrayWidth)
        {
            this.arrayWidth = arrayWidth;
            for (SelectableRectangle rectangle : rectangleMap.values())
            {
                rectangle.setWidth(calculateRectangleWidth());
            }
        }
    }

    private Rectangle createRectangle(int i, T value)
    {
        int width = calculateRectangleWidth();
        SelectableRectangle rectangle = new SelectableRectangle(width, calculateRelativeRectangleHeight(value.floatValue()));
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setFillColor(Color.GREY);
        rectangle.setSelectedColor(Color.DARKGRAY);
        rectangleMap.put(i, rectangle);
        return rectangle;
    }

    public void setRectangleColor(Paint color)
    {
        for (SelectableRectangle rectangle : rectangleMap.values())
        {
            rectangle.setFillColor(color);
        }
    }

    public void setSelectedRectangleColor(Paint color)
    {
        for (SelectableRectangle rectangle : rectangleMap.values())
        {
            rectangle.setSelectedColor(color);
        }
    }

    private int calculateRectangleWidth()
    {
        return (arrayWidth / model.size()) - (((model.size() - 1) * rectangleSpacing) / model.size());
    }

    private void updateRectangle(int i, T value)
    {
        if (rectangleMap.containsKey(i))
        {
            SelectableRectangle rectangle = rectangleMap.get(i);
            FxUtils.run(() ->
            {
                selectRectangle(rectangle);
                rectangle.setHeight(calculateRelativeRectangleHeight(value.floatValue()));
                rectangle.setWidth(calculateRectangleWidth());
            });
        }
    }

    public void setElementAlignment(Pos alignment)
    {
        renderer.setAlignment(alignment);
    }

    public void clearLastSelected()
    {
        if (currentElement != null)
        {
            currentElement.selectedProperty().set(false);
        }
    }

    public Node getRenderer()
    {
        return renderer;
    }

    @Override
    public void valueSet(int i, T value)
    {
        updateRectangle(i, value);
    }

    @Override
    public void currentElement(int i, T value)
    {
        if (rectangleMap.containsKey(i))
        {
            FxUtils.run(() -> selectRectangle(rectangleMap.get(i)));
        }
    }

    private void selectRectangle(SelectableRectangle rectangle)
    {
        clearLastSelected();
        rectangle.selectedProperty().set(true);
        currentElement = rectangle;
    }

    @Override
    public void aboutToSwap(int i, T iValue, int j, T jValue)
    {
        FxUtils.run(() ->
        {
            updateRectangle(i, jValue);
            updateRectangle(j, iValue);
        });
    }

    @Override
    public void rolled()
    {
        FxUtils.run(() ->
        {
            for (int i = 0; i < model.size(); i++)
            {
                updateRectangle(i, model.get(i));
            }
        });
    }
}
