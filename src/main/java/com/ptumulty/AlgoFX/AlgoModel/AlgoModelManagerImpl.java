package com.ptumulty.AlgoFX.AlgoModel;

import java.util.LinkedList;
import java.util.List;

public class AlgoModelManagerImpl implements AlgoModelManager
{
    private final List<AlgoModelManagerListener> listeners;
    private AlgoModelController currentModel;

    public AlgoModelManagerImpl()
    {
        listeners = new LinkedList<>();
    }

    @Override
    public void setAlgoModelController(AlgoModelController algoModel)
    {
        if (currentModel != null)
        {
            currentModel.dispose();
        }
        currentModel = algoModel;
        listeners.forEach(listener -> listener.algoModelChanged(currentModel));
    }

    @Override
    public AlgoModelController getCurrentAlgoModel()
    {
        return currentModel;
    }

    @Override
    public void addListener(AlgoModelManagerListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeListener(AlgoModelManagerListener listener)
    {
        listeners.remove(listener);
    }
}
