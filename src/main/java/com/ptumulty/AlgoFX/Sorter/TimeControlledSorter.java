package com.ptumulty.AlgoFX.Sorter;

import com.ptumulty.ceramic.utility.ThreadUtils;

public class TimeControlledSorter
{
    public static void sort(ArrayModel<Integer> arrayModel, SortingAlgorithm algorithm, int stepIntervalMillis)
    {
        switch (algorithm)
        {
            case BUBBLE -> bubbleSort(arrayModel, stepIntervalMillis);
        }
    }

    private static void bubbleSort(ArrayModel<Integer> arrayModel,  int stepIntervalMillis)
    {
        int len = arrayModel.size();
        for (int i = 0; i < len; i++, ThreadUtils.safeSleep(stepIntervalMillis))
        {
            for (int j = 1; j < (len - i); j++, ThreadUtils.safeSleep(stepIntervalMillis))
            {
                if (arrayModel.get(j - 1) > arrayModel.get(j))
                {
                    arrayModel.swap(j - 1, j);
                }
            }
        }
    }
}
