package com.ptumulty.AlgoFX.Sorting.Sorter.AlgoOperationsCounter;

import com.ptumulty.ceramic.models.IntegerModel;

public class DefaultOperationsCounter implements OperationsCounter
{
    protected IntegerModel operationsCountModel;
    protected IntegerModel dataSetSizeModel;

    public DefaultOperationsCounter()
    {
        operationsCountModel = new IntegerModel(0);
        dataSetSizeModel = new IntegerModel(0);
    }

    public void resetCounter()
    {
        operationsCountModel.setValue(0);
        dataSetSizeModel.setValue(0);
    }

    public void incrementCounter()
    {
        operationsCountModel.setValue(operationsCountModel.get() + 1);
    }

    @Override
    public boolean countConditional(boolean conditional)
    {
        incrementCounter();
        return conditional;
    }

    @Override
    public IntegerModel getOperationsCountModel()
    {
        return operationsCountModel;
    }

    @Override
    public IntegerModel getDataSetSizeModel()
    {
        return dataSetSizeModel;
    }
}
