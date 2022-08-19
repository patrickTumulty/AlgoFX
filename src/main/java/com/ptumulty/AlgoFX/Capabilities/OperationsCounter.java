package com.ptumulty.AlgoFX.Capabilities;

import com.ptumulty.ceramic.models.IntegerModel;

public interface OperationsCounter extends AlgoCapability
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
