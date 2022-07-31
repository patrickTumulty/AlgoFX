package com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.utility.ThreadUtils;

public class BubbleTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    public String getSorterName()
    {
        return "Bubble Sort";
    }

    @Override
    public void sort(ArrayModel<Integer> arrayModel)
    {
        int len = arrayModel.size();
        for (int i = 0; i < len; i++, ThreadUtils.safeSleep(timeStepIntegerModel.get()))
        {
            for (int j = 1; j < (len - i); j++, ThreadUtils.safeSleep(timeStepIntegerModel.get()))
            {
                if (arrayModel.get(j - 1) > arrayModel.get(j))
                {
                    arrayModel.swap(j - 1, j);
                }
            }
        }
    }
}
