package com.ptumulty.AlgoFX.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.utility.ThreadUtils;

public class IterateTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    public String getSorterName()
    {
        return "Iterate";
    }

    @Override
    public void sort(ArrayModel<Integer> arrayModel)
    {
        for (int i = 0; i < arrayModel.size(); i++, ThreadUtils.safeSleep(timeStepIntegerModel.get()))
        {
            arrayModel.get(i);
        }
    }
}
