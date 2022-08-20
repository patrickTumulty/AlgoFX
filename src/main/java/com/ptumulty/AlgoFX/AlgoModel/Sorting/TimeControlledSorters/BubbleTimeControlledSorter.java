package com.ptumulty.AlgoFX.AlgoModel.Sorting.TimeControlledSorters;

import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel.ArrayModel;

public class BubbleTimeControlledSorter extends AbstractTimeControlledSorter
{
    @Override
    public String getSorterName()
    {
        return "Bubble Sort";
    }

    @Override
    public void doSort(ArrayModel<Integer> arrayModel) throws CancelSortException
    {
        int len = arrayModel.size();
        for (int i = 0; oc.countConditional(i < len); i++, doCancelableDelay())
        {
            for (int j = 1; oc.countConditional(j < (len - i)); j++, doCancelableDelay())
            {
                if (oc.countConditional(arrayModel.get(j - 1) > arrayModel.get(j)))
                {
                    oc.incrementCounter();
                    arrayModel.swap(j - 1, j);
                }
            }
        }
    }
}
