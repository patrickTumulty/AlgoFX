package com.ptumulty.AlgoFX.Sorter;

import com.ptumulty.ceramic.models.BoundIntegerModel;
import com.ptumulty.ceramic.models.ChoiceModel;

import java.util.List;
import java.util.Optional;

public class ArraySorter
{
    private final ChoiceModel<SortingAlgorithm> algorithmChoiceModel;
    private ArrayModel<Integer> arrayModel;
    private final BoundIntegerModel arraySizeModel;

    public ArraySorter()
    {
        arraySizeModel = new BoundIntegerModel(10, Optional.of(2), Optional.of(100));
        algorithmChoiceModel = new ChoiceModel<>(SortingAlgorithm.BUBBLE, List.of(SortingAlgorithm.values()));
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

    public ChoiceModel<SortingAlgorithm> getAlgorithmChoiceModel()
    {
        return algorithmChoiceModel;
    }

    public Optional<ArrayModel<Integer>> getArrayModel()
    {
        return Optional.ofNullable(arrayModel);
    }

    public void sort()
    {
        TimeControlledSorter.sort(arrayModel, getAlgorithmChoiceModel().get(), 100);
    }
}
