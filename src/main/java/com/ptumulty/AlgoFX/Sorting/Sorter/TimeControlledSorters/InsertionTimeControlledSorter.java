package com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.utility.ThreadUtils;

public class InsertionTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    public String getSorterName()
    {
        return "Insertion Sort";
    }

    @Override
    public void sort(ArrayModel<Integer> arrayModel)
    {
        for (int i = 1; i < arrayModel.size(); i++, ThreadUtils.safeSleep(timeStepIntegerModel.get()))
        {
            int key = arrayModel.get(i);
            int j = i - 1;

            while (j >= 0 && arrayModel.get(j) > key)
            {
                ThreadUtils.safeSleep(timeStepIntegerModel.get());
                arrayModel.set(j + 1, arrayModel.get(j));
                j = j - 1;
            }
            arrayModel.set(j + 1, key);
        }
    }
}
