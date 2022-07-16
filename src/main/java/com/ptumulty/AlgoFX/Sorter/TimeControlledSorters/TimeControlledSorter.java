package com.ptumulty.AlgoFX.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorter.ArrayModel;
import com.ptumulty.ceramic.models.BoundIntegerModel;
import org.openide.util.Lookup;

public interface TimeControlledSorter
{
    String getSorterName();

    BoundIntegerModel getTimeStepIntegerModel();

    void sort(ArrayModel<Integer> arrayModel);

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
