package com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.utility.ThreadUtils;

public class SelectionTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    public String getSorterName()
    {
        return "Selection Sort";
    }

    @Override
    public void sort(ArrayModel<Integer> arrayModel)
    {
        int lowest;
        int lowestIndex;
        for (int i = 0; i < arrayModel.size(); i++, ThreadUtils.safeSleep(timeStepIntegerModel.get()))
        {
            lowest = Integer.MAX_VALUE;
            lowestIndex = -1;
            for (int j = i; j < arrayModel.size(); j++, ThreadUtils.safeSleep(timeStepIntegerModel.get()))
            {
                int value = arrayModel.get(j);
                if (value < lowest)
                {
                    lowest = value;
                    lowestIndex = j;
                }
            }
            arrayModel.swap(i, lowestIndex);
        }
    }
}
