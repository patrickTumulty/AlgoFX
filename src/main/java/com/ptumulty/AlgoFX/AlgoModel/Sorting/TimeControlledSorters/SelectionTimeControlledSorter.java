package com.ptumulty.AlgoFX.AlgoModel.Sorting.TimeControlledSorters;

import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel.ArrayModel;

public class SelectionTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    public String getSorterName()
    {
        return "Selection Sort";
    }

    @Override
    public void doSort(ArrayModel<Integer> arrayModel) throws CancelSortException
    {
        int lowest;
        int lowestIndex;
        for (int i = 0; oc.countConditional(i < arrayModel.size()); i++, doCancelableDelay())
        {
            lowest = Integer.MAX_VALUE;
            lowestIndex = -1;
            for (int j = i; oc.countConditional(j < arrayModel.size()); j++, doCancelableDelay())
            {
                int value = arrayModel.get(j);
                if (oc.countConditional(value < lowest))
                {
                    lowest = value;
                    lowestIndex = j;
                }
            }
            arrayModel.swap(i, lowestIndex);
        }
    }
}
