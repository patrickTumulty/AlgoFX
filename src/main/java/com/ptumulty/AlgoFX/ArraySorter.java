package com.ptumulty.AlgoFX;

import com.ptumulty.ceramic.models.IntegerModel;

import java.util.Optional;

public class ArraySorter
{
    private ArrayModel<Integer> arrayModel;
    private final IntegerModel arraySizeModel;

    ArraySorter()
    {
        arraySizeModel = new IntegerModel(10);
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

    public IntegerModel getArraySizeModel()
    {
        return arraySizeModel;
    }

    public Optional<ArrayModel<Integer>> getArrayModel()
    {
        return Optional.ofNullable(arrayModel);
    }
}
