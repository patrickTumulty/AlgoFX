package com.ptumulty.AlgoFX.AlgoModel;

public interface Broadcaster<T>
{
    void addListener(T listener);

    void removeListener(T listener);
}
