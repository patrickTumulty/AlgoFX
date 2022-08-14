package com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;

public class InsertionTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    public String getSorterName()
    {
        return "Insertion Sort";
    }

    @Override
    public void doSort(ArrayModel<Integer> arrayModel) throws CancelSortException
    {
        for (int i = 1; oc.countConditional(i < arrayModel.size()); i++, doCancelableDelay())
        {
            int key = arrayModel.get(i);
            int j = i - 1;

            while (oc.countConditional(j >= 0 && arrayModel.get(j) > key))
            {
                doCancelableDelay();
                arrayModel.set(j + 1, arrayModel.get(j));
                j = j - 1;
            }
            arrayModel.set(j + 1, key);
        }
    }
}
