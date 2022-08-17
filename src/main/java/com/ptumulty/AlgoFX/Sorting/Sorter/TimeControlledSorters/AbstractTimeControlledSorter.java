package com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters;

import com.ptumulty.AlgoFX.Sorting.Sorter.AlgoOperationsCounter.DefaultOperationsCounter;
import com.ptumulty.AlgoFX.Sorting.Sorter.AlgoOperationsCounter.OperationsCounter;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.ceramic.models.BoundIntegerModel;
import com.ptumulty.ceramic.utility.ThreadUtils;

import java.util.Optional;

public abstract class AbstractTimeControlledSorter implements TimeControlledSorter
{
    protected final BoundIntegerModel timeStepIntegerModel;
    protected final OperationsCounter oc;

    protected boolean sortCancelRequested;

    AbstractTimeControlledSorter()
    {
        timeStepIntegerModel = new BoundIntegerModel(15, Optional.of(0), Optional.of(500));
        oc = new DefaultOperationsCounter();

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

    protected void doCancelableDelay() throws CancelSortException
    {
        ThreadUtils.safeSleep(timeStepIntegerModel.get());

        if (sortCancelRequested)
        {
            throw new CancelSortException();
        }
    }

    public void sort(ArrayModel<Integer> arrayModel)
    {
        sortCancelRequested = false;
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

    abstract void doSort(ArrayModel<Integer> arrayModel) throws CancelSortException;

    protected class CancelSortException extends Exception
    {
        CancelSortException()
        {
            super(getSorterName() + " operation was stop.");
        }
    }
}
