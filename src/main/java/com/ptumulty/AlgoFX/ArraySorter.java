package com.ptumulty.AlgoFX;

import com.ptumulty.ceramic.models.BoundIntegerModel;

import java.util.Optional;

public class ArraySorter
{
    private ArrayModel<Integer> arrayModel;
    private final BoundIntegerModel arraySizeModel;

    ArraySorter()
    {
        arraySizeModel = new BoundIntegerModel(10, Optional.of(2), Optional.of(100)); // TODO reevalute bounds
    }

    public void generateNewArray()
    {
        arrayModel = new ArrayModelImpl<>(arraySizeModel.get());
        for (int i = 0; i < arrayModel.size(); i++)
        {
            arrayModel.set(i, i + 1);
        }
    }

    public void scrambleArray()
    {
        if (arrayModel != null)
        {
            arrayModel.scramble();
        }
    }

    public BoundIntegerModel getArraySizeModel()
    {
        return arraySizeModel;
    }

    public Optional<ArrayModel<Integer>> getArrayModel()
    {
        return Optional.ofNullable(arrayModel);
    }
}
