package com.ptumulty.AlgoFX.Sorting.Sorter;

import com.ptumulty.AlgoFX.ArrayGenerationMethod;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModel;
import com.ptumulty.AlgoFX.Sorting.Sorter.ArrayModel.ArrayModelImpl;
import com.ptumulty.AlgoFX.Sorting.Sorter.TimeControlledSorters.TimeControlledSorter;
import com.ptumulty.ceramic.models.BoundIntegerModel;
import com.ptumulty.ceramic.models.ChoiceModel;
import org.openide.util.Lookup;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ArraySorterController
{
    private final BoundIntegerModel arraySizeModel;
    private ChoiceModel<String> sortingAlgorithmChoiceModel;
    private ChoiceModel<ArrayGenerationMethod> arrayGenerationChoiceModel;
    private ArrayModel<Integer> arrayModel;
    private TimeControlledSorter currentSorter;

    public ArraySorterController()
    {
        arraySizeModel = new BoundIntegerModel(20, Optional.of(2), Optional.of(100));

        createArrayGenerationChoiceModel();

        createSortingAlgorithmChoiceModel();
    }

    private void createArrayGenerationChoiceModel()
    {
        arrayGenerationChoiceModel = new ChoiceModel<>(ArrayGenerationMethod.SEQUENTIAL, List.of(ArrayGenerationMethod.values()));
    }

    public ChoiceModel<ArrayGenerationMethod> getArrayGenerationChoiceModel()
    {
        return arrayGenerationChoiceModel;
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
            sortingAlgorithmChoiceModel = new ChoiceModel<>(sortingAlgorithms.get(0), sortingAlgorithms);
            sortingAlgorithmChoiceModel.addListener(currentValue ->
                    currentSorter = TimeControlledSorter.get(sortingAlgorithmChoiceModel.get()));
            currentSorter = TimeControlledSorter.get(sortingAlgorithmChoiceModel.get());
        }
        else
        {
            throw new RuntimeException("No array sorters found");
        }
    }

    public TimeControlledSorter getCurrentTimeControlledSorter()
    {
        return currentSorter;
    }

    public void generateNewArray()
    {
        arrayModel = new ArrayModelImpl<>(arraySizeModel.get());
        ArrayGenerationMethod generationMethod = arrayGenerationChoiceModel.get();
        switch (generationMethod)
        {
            case SEQUENTIAL -> ArrayModel.fillSequential(arrayModel, 1);
            case RANDOM -> ArrayModel.fillRandom(arrayModel, 1, 50);
            case STATIC_VALUE -> ArrayModel.fill(arrayModel, 5);
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

    public ChoiceModel<String> getSortingAlgorithmChoiceModel()
    {
        return sortingAlgorithmChoiceModel;
    }

    public Optional<ArrayModel<Integer>> getArrayModel()
    {
        return Optional.ofNullable(arrayModel);
    }

    public void sort()
    {
        currentSorter.sort(arrayModel);
    }
}
