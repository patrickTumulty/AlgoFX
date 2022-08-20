package com.ptumulty.AlgoFX.AlgoView.SorterView;

import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArraySorterController;
import com.ptumulty.AlgoFX.AlgoView.AlgoModelViewProvider;

public class SortingAlgoModelViewProvider implements AlgoModelViewProvider<SortingAlgoModelView, ArraySorterController>
{
    @Override
    public Class<ArraySorterController> getCompatibleType()
    {
        return ArraySorterController.class;
    }

    @Override
    public SortingAlgoModelView createView(ArraySorterController controller)
    {
        return new SortingAlgoModelView(controller);
    }
}
