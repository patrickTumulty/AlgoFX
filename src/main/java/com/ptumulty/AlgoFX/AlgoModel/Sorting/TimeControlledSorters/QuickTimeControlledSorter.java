package com.ptumulty.AlgoFX.AlgoModel.Sorting.TimeControlledSorters;

import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel.ArrayModel;

public class QuickTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    void doSort(ArrayModel<Integer> arrayModel) throws CancelSortException
    {
        quickSort(arrayModel, 0, arrayModel.size() - 1);
    }

    @Override
    public String getSorterName()
    {
        return "Quick Sort";
    }

    private void quickSort(ArrayModel<Integer> arr, int low, int high) throws CancelSortException
    {
        if (oc.countConditional(low < high))
        {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(ArrayModel<Integer> arr, int low, int high) throws CancelSortException
    {
        int pivot = arr.get(high);

        int i = (low - 1);

        for(int j = low; oc.countConditional(j <= high - 1); j++, doCancelableDelay())
        {
            if (oc.countConditional(arr.get(j) < pivot))
            {
                i++;
                arr.swap(i, j);
            }
        }
        arr.swap(i + 1, high);

        return i + 1;
    }
}
