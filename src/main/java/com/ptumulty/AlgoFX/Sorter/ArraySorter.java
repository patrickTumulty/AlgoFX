package com.ptumulty.AlgoFX.Sorter;

import com.ptumulty.AlgoFX.Sorter.TimeControlledSorters.TimeControlledSorter;
import com.ptumulty.ceramic.models.BoundIntegerModel;
import com.ptumulty.ceramic.models.ChoiceModel;
import org.openide.util.Lookup;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ArraySorter
{
    private final BoundIntegerModel arraySizeModel;
    private Optional<ChoiceModel<String>> sortingAlgorithmChoiceModel;
    private ArrayModel<Integer> arrayModel;
    private TimeControlledSorter sorter;

    public ArraySorter()
    {
        arraySizeModel = new BoundIntegerModel(10, Optional.of(2), Optional.of(100));
        sortingAlgorithmChoiceModel = Optional.empty();

        createSortingAlgorithmChoiceModel();
    }

    private void createSortingAlgorithmChoiceModel()
    {
        List<String> sortingAlgorithms = new LinkedList<>();
        for (TimeControlledSorter sorter : Lookup.getDefault().lookupAll(TimeControlledSorter.class))
        {
            sortingAlgorithms.add(sorter.getSorterName());
        }

        if (sortingAlgorithms.size() > 0)
        {
            sortingAlgorithmChoiceModel = Optional.of(new ChoiceModel<>(sortingAlgorithms.get(0), sortingAlgorithms));
            sortingAlgorithmChoiceModel.get().addListener(currentValue -> sorter = TimeControlledSorter.get(sortingAlgorithmChoiceModel.get().get()));
            sorter = TimeControlledSorter.get(sortingAlgorithmChoiceModel.get().get());
        }
    }

    public TimeControlledSorter getCurrentTimeControlledSorter()
    {
        return sorter;
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

    public Optional<ChoiceModel<String>> getSortingAlgorithmChoiceModel()
    {
        return sortingAlgorithmChoiceModel;
    }

    public Optional<ArrayModel<Integer>> getArrayModel()
    {
        return Optional.ofNullable(arrayModel);
    }

    public void sort()
    {
        sorter.sort(arrayModel);
    }
}
