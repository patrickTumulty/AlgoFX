package com.ptumulty.AlgoFX.AlgoModel;

public interface AlgoModelManager extends Broadcaster<AlgoModelManager.AlgoModelManagerListener>
{
    void setAlgoModelController(AlgoModelController algoModel);

    AlgoModelController getCurrentAlgoModel();

    interface AlgoModelManagerListener
    {
        void algoModelChanged(AlgoModelController newModel);
    }
}
