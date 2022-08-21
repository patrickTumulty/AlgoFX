package com.ptumulty.AlgoFX.AlgoModel.Sorting.TimeControlledSorters;

import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel.ArrayModel;

public class HeapTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    protected void doSort(ArrayModel<Integer> arr) throws CancelSortException
    {
        int N = arr.size();

        for (int i = N / 2 - 1; oc.countConditional(i >= 0); i--, doCancelableDelay())
            heapify(arr, N, i);

        for (int i = N - 1; oc.countConditional(i > 0); i--, doCancelableDelay())
        {
            arr.swap(0, i);

            heapify(arr, i, 0);
        }
    }

    @Override
    public String getSorterName()
    {
        return "Heap Sort";
    }

    private void heapify(ArrayModel<Integer> arr, int N, int i)
    {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (oc.countConditional(l < N && arr.get(l) > arr.get(largest)))
            largest = l;

        if (oc.countConditional(r < N && arr.get(r) > arr.get(largest)))
            largest = r;

        if (oc.countConditional(largest != i))
        {
            arr.swap(i, largest);

            heapify(arr, N, largest);
        }
    }
}
