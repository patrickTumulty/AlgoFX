package com.ptumulty.AlgoFX.AlgoModel.Sorting.TimeControlledSorters;

import com.ptumulty.AlgoFX.Capabilities.OperationsCounter;
import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.models.BoundIntegerModel;
import org.openide.util.Lookup;

public interface TimeControlledSorter
{
    String getSorterName();

    BoundIntegerModel getTimeStepIntegerModel();

    OperationsCounter getOperationsCounter();

    void sort(ArrayModel<Integer> arrayModel, OperationsCounter operationsCounter);

    void cancelSortInProgress();

    static TimeControlledSorter get(String sorterName)
    {
        for (TimeControlledSorter sorter : Lookup.getDefault().lookupAll(TimeControlledSorter.class))
        {
            if (sorter.getSorterName().equalsIgnoreCase(sorterName))
            {
                return sorter;
            }
        }
        return null;
    }
}
