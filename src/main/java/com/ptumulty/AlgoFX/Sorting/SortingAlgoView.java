package com.ptumulty.AlgoFX.Sorting;

import com.ptumulty.AlgoFX.AlgoView.AlgoView;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArraySorterController;
import com.ptumulty.AlgoFX.Sorting.SorterView.ArraySortView;
import com.ptumulty.ceramic.components.ComponentSettingGroup;
import javafx.scene.Node;

import java.util.List;

public class SortingAlgoView implements AlgoView
{

    private ArraySortView arraySortView;

    @Override
    public String getTitle()
    {
        return "Sorting";
    }

    @Override
    public void initView()
    {
        ArraySorterController arraySorter = new ArraySorterController();
        arraySortView = new ArraySortView(arraySorter);
    }

    @Override
    public void dispose()
    {

    }

    @Override
    public Node getVisualization()
    {
        return arraySortView.getView();
    }

    @Override
    public String getAlgoActionName()
    {
        return null;
    }

    @Override
    public void doAlgoAction()
    {

    }

    @Override
    public void doAlgoReset()
    {

    }

    @Override
    public List<ComponentSettingGroup> getSettings()
    {
        return null;
    }
}
