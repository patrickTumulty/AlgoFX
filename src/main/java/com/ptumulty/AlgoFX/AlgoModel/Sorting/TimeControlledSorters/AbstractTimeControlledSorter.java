package com.ptumulty.AlgoFX.AlgoModel.Sorting.TimeControlledSorters;

import com.ptumulty.AlgoFX.Capabilities.DefaultOperationsCounter;
import com.ptumulty.AlgoFX.Capabilities.OperationsCounter;
import com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.models.BoundIntegerModel;
import com.ptumulty.ceramic.utility.ThreadUtils;

import java.util.Optional;

public abstract class AbstractTimeControlledSorter implements TimeControlledSorter
{
    protected final BoundIntegerModel timeStepIntegerModel;
    protected OperationsCounter oc;

    protected boolean sortCancelRequested;

    AbstractTimeControlledSorter()
    {
        timeStepIntegerModel = new BoundIntegerModel(15, Optional.of(0), Optional.of(500));

        sortCancelRequested = false;
    }

    @Override
    public void cancelSortInProgress()
    {
        sortCancelRequested = true;
    }

    @Override
    public BoundIntegerModel getTimeStepIntegerModel()
    {
        return timeStepIntegerModel;
    }

    @Override
    public OperationsCounter getOperationsCounter()
    {
        return oc;
    }

    protected void doCancelableDelay() throws CancelSortException
    {
        ThreadUtils.safeSleep(timeStepIntegerModel.get());

        if (sortCancelRequested)
        {
            throw new CancelSortException();
        }
    }

    public void sort(ArrayModel<Integer> arrayModel, OperationsCounter operationsCounter)
    {
        sortCancelRequested = false;
        oc = operationsCounter;
        oc.resetCounter();
        oc.getDataSetSizeModel().setValue(arrayModel.size());

        try
        {
            doSort(arrayModel);
        }
        catch (CancelSortException e)
        {
            // Do nothing
        }
    }

    abstract protected void doSort(ArrayModel<Integer> arrayModel) throws CancelSortException;

    protected class CancelSortException extends Exception
    {
        CancelSortException()
        {
            super(getSorterName() + " operation was stop.");
        }
    }
}
