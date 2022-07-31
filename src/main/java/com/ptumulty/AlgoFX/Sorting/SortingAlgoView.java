package com.ptumulty.AlgoFX.Sorting;

import com.ptumulty.AlgoFX.AlgoView.AlgoView;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArraySorterController;
import com.ptumulty.AlgoFX.Sorting.SorterView.ArraySortView;
import javafx.scene.Node;

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
    public Node getControls()
    {
        return null;
    }
}
