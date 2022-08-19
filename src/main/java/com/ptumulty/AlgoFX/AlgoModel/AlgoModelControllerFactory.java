package com.ptumulty.AlgoFX.AlgoModel;

import com.ptumulty.AlgoFX.AlgoView.AlgoAsset;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArraySorterController;
import com.ptumulty.AlgoFX.Sorting.SortingAlgoAsset;

public class AlgoModelControllerFactory
{
    public static AlgoModelController create(AlgoAsset algoAsset)
    {
        if (algoAsset instanceof SortingAlgoAsset)
        {
            return new ArraySorterController();
        }
        return null;
    }
}
