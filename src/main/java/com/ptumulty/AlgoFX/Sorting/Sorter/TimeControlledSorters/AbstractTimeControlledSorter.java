package com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters;

import com.ptumulty.ceramic.models.BoundIntegerModel;

import java.util.Optional;

public abstract class AbstractTimeControlledSorter implements TimeControlledSorter
{
    protected final BoundIntegerModel timeStepIntegerModel;

    AbstractTimeControlledSorter()
    {
        timeStepIntegerModel = new BoundIntegerModel(10, Optional.of(0), Optional.of(1000));
    }

    @Override
    public BoundIntegerModel getTimeStepIntegerModel()
    {
        return timeStepIntegerModel;
    }
}
