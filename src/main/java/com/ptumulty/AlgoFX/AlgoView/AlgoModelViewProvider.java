package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.AlgoFX.AlgoModel.AlgoModelController;

public interface AlgoModelViewProvider<T extends AlgoModelView, V extends AlgoModelController>
{
    Class<V> getCompatibleType();

    T createView(V controller);
}
