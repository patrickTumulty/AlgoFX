package com.ptumulty.AlgoFX.AlgoModel.Sorting.TimeControlledSorters;

import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel.ArrayModel;

public class MergeTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    protected void doSort(ArrayModel<Integer> arrayModel) throws CancelSortException
    {
        sort(arrayModel, 0, arrayModel.size() - 1);
    }

    @Override
    public String getSorterName()
    {
        return "Merge Sort";
    }

    private void merge(ArrayModel<Integer> arr, int start, int mid, int end) throws CancelSortException
    {
        int n1 = mid - start + 1;
        int n2 = end - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; oc.countConditional(i < n1); ++i)
            L[i] = arr.get(start + i);
        for (int j = 0; oc.countConditional(j < n2); ++j)
            R[j] = arr.get(mid + 1 + j);

        int i = 0, j = 0;

        int k = start;
        while (oc.countConditional(i < n1 && j < n2))
        {
            doCancelableDelay();
            if (oc.countConditional(L[i] <= R[j]))
            {
                arr.set(k, L[i]);
                i++;
            }
            else
            {
                arr.set(k, R[j]);
                j++;
            }
            k++;
        }

        while (oc.countConditional(i < n1))
        {
            doCancelableDelay();
            arr.set(k, L[i]);
            i++;
            k++;
        }

        while (oc.countConditional(j < n2))
        {
            doCancelableDelay();
            arr.set(k, R[j]);
            j++;
            k++;
        }
    }

    private void sort(ArrayModel<Integer> arr, int start, int end) throws CancelSortException
    {
        if (oc.countConditional(start < end))
        {
            int mid = start+ (end-start)/2;

            sort(arr, start, mid);
            sort(arr, mid + 1, end);

            merge(arr, start, mid, end);
        }
    }
}
