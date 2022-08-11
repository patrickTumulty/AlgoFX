package com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.utility.ThreadUtils;

public class RecursiveBubbleTimeControlledSorter extends AbstractTimeControlledSorter
{

    @Override
    public String getSorterName()
    {
        return "Bubble Sort (Recursive)";
    }

    @Override
    public void sort(ArrayModel<Integer> arrayModel)
    {
        bubbleSortRecursive(arrayModel, arrayModel.size());
    }


    public void bubbleSortRecursive(ArrayModel<Integer> array, int n)
    {
        if (n == 1)
            return;

        int count = 0;

        for (int i = 0; i < n - 1; i++, ThreadUtils.safeSleep(timeStepIntegerModel.get()))
        {
            if (array.get(i) > array.get(i + 1))
            {
                array.swap(i, i + 1);
                count++;
            }
        }

        if (count == 0)
            return;

        bubbleSortRecursive(array, n - 1);
    }
}
