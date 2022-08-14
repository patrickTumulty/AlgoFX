package com.ptumulty.AlgoFX.Sorting.Sorter.AlgoOperationsCounter;

import com.ptumulty.ceramic.models.IntegerModel;

public interface OperationsCounter
{
    void resetCounter();

    void incrementCounter();

    boolean countConditional(boolean conditional);

    IntegerModel getOperationsCountModel();

    IntegerModel getDataSetSizeModel();

    interface RunnableConditional
    {
        boolean run();
    }
}
